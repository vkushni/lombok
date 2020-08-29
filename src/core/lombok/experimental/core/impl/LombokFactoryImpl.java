package lombok.experimental.core.impl;

import java.awt.TrayIcon.MessageType;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokDescriptorFactory;
import lombok.experimental.core.LombokDescriptorFactory.Configuration;
import lombok.experimental.core.LombokDescriptorFactory.Configuration.ConfigurationBuilder;
import lombok.experimental.core.LombokFactory;
import lombok.experimental.core.LombokPriorityFactory;
import lombok.experimental.core.descriptor.AnnotationDescriptor;
import lombok.experimental.core.descriptor.ClassDescriptor;
import lombok.experimental.core.descriptor.Descriptor;
import lombok.experimental.core.descriptor.MemberType;

@RequiredArgsConstructor public class LombokFactoryImpl implements LombokFactory {
	
	/**
	 * Default configuration to retrieve annotations only, is needed to pick up
	 * lombok annotations (for now at the top level only).
	 */
	public static final Configuration ANNOTATIONS_ONLY = Configuration.builder().memberType(MemberType.ANNOTATION).build();
	
	private final LombokDescriptorFactory descriptorFactory;
	private final LombokPriorityFactory priorityProvider;
	private final LombokCompiler compiler;
	
	/**
	 * Generate code for all descriptors
	 * 
	 * @param descriptors
	 * @param action
	 * @param generate
	 * @return
	 */
	private List<Message> applyChanges(List<Descriptor> descriptors, Action action, boolean generate) {
		// TODO: at this point i'm pretty sure we should not use lists
		// most likely it should be tree of required modifications
		// Because adding method arguments, exceptions make no sense without
		// adding methods, but its OK even for POC.
		// Also there is a way to sort it out:
		// method arguments keep references to method descriptors, which:
		// have references to all its children so they may initiate generation
		// for the methods (with all arguments, exceptions) if it is needed.
		for (Descriptor descriptor : descriptors) {
			action.apply(compiler, descriptor, generate);
		}
		
		// TODO: it should return real messages
		return Collections.emptyList();
	}
	
	/**
	 * Collect changes to apply for particular lombok annotation
	 * 
	 * @param descriptor
	 * @param itemsToRemove
	 * @param itemsToModify
	 * @param itemsToAdd
	 */
	private void collectChanges(AnnotationDescriptor descriptor, List<Descriptor> itemsToRemove, List<Descriptor> itemsToModify, List<Descriptor> itemsToAdd) {
		if (descriptor.hasAnnotation(Setter.class)) {
			// TODO: collect changes for setter here or delegate to other class
			// TODO: this code supposed to add items to the end of the list to
			// preserve order and avoid errors
			// (like creating method to access field which has not been
			// generated yet)
		}
	}
	
	/**
	 * Collect changes to apply for all lombok annotations
	 * 
	 * @param lombokAnnotations
	 * @param itemsToRemove
	 * @param itemsToModify
	 * @param itemsToAdd
	 */
	private void collectChanges(List<AnnotationDescriptor> lombokAnnotations, List<Descriptor> itemsToRemove, List<Descriptor> itemsToModify, List<Descriptor> itemsToAdd) {
		for (AnnotationDescriptor annotation : lombokAnnotations) {
			flagUsage(annotation);
			collectChanges(annotation, itemsToRemove, itemsToModify, itemsToAdd);
		}
	}
	
	/**
	 * Creates configuration to extract descriptions from the type. Descriptors
	 * for lombok annotations are requried to change its behavior. Like for
	 * super builder is is necessary to dive into parents, etc.
	 * 
	 * @param type
	 * @param annotations
	 * @return
	 */
	private Configuration createConfiguration(Class<?> type, List<AnnotationDescriptor> annotations) {
		return Configuration.builder().memberType(MemberType.CLASS).memberType(MemberType.FIELD).memberType(MemberType.METHOD).members(createMemberConfiguration(type, annotations)).build();
	}
	
	private Configuration createMemberConfiguration(Class<?> type, List<AnnotationDescriptor> annotations) {
		ConfigurationBuilder<?, ?> builder = Configuration.builder();
		if (hasAnnotation(annotations, Setter.class)) {
			builder.memberType(MemberType.ANNOTATION).memberType(MemberType.METHOD).memberType(MemberType.FIELD);
		}
		return builder.build();
	}
	
	private List<AnnotationDescriptor> findLombokAnnotations(Class<?> type) {
		ClassDescriptor classDescriptor = descriptorFactory.get(type, ANNOTATIONS_ONLY);
		List<AnnotationDescriptor> lombokAnnotations = classDescriptor.getAnnotations();
		List<AnnotationDescriptor> annotations = classDescriptor.getAnnotations();
		for (AnnotationDescriptor descriptor : annotations) {
			if (descriptor.isLombok()) {
				lombokAnnotations.add(descriptor);
			}
		}
		return lombokAnnotations;
	}
	
	private void flagUsage(AnnotationDescriptor annotation) {
		// TODO flag lombok annotation is used
		// TODO: consider delegating to separate class
	}
	
	@Override public List<Message> generate(Class<?> type, boolean generate) {
		// extracting lombok annotation descriptors (they are needed to extract
		// the rest)
		List<AnnotationDescriptor> lombokAnnotations = sortByPriority(findLombokAnnotations(type));
		// TODO: ^^ instead of sorting lombok annotations here, we may consider
		// sorting the whole tree
		// using different priority provider for different IDE's will allow
		// to modify generation order and thus sort fields/methods according to
		// IDE preferences.
		
		// extract descriptor tree
		Configuration configuration = createConfiguration(type, lombokAnnotations);
		ClassDescriptor classDescriptor = descriptorFactory.get(type, configuration);
		
		// perform initial validation
		List<Message> messages = validateDescriptor(classDescriptor, lombokAnnotations);
		if (hasMessageType(messages, MessageType.ERROR)) {
			return messages;
		}
		
		// this part supposed to collect all changes required to apply
		// this code just to show the idea
		// better if it would return list of all required modifications
		// event better if it would return some modification tree object
		// which will contain references to all items which should be
		// removed/updated/deleted
		List<Descriptor> itemsToRemove = new LinkedList<Descriptor>();
		List<Descriptor> itemsToModify = new LinkedList<Descriptor>();
		List<Descriptor> itemsToAdd = new LinkedList<Descriptor>();
		collectChanges(lombokAnnotations, itemsToRemove, itemsToModify, itemsToAdd);
		
		// apply all changes
		List<Message> removeMessages = applyChanges(itemsToRemove, Action.REMOVE, generate);
		messages.addAll(removeMessages);
		if (hasMessageType(removeMessages, MessageType.ERROR)) {
			return messages;
		}
		
		List<Message> modifyMessages = applyChanges(itemsToModify, Action.MODIFY, generate);
		messages.addAll(modifyMessages);
		if (hasMessageType(modifyMessages, MessageType.ERROR)) {
			return messages;
		}
		
		List<Message> addMessages = applyChanges(itemsToAdd, Action.ADD, generate);
		messages.addAll(addMessages);
		if (hasMessageType(addMessages, MessageType.ERROR)) {
			return messages;
		}
		
		return messages;
	}
	
	private boolean hasAnnotation(List<AnnotationDescriptor> annotations, Class<?> annotationType) {
		for (AnnotationDescriptor descriptor : annotations) {
			if (descriptor.hasAnnotation(annotationType)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if there is a message with type
	 * 
	 * @param messages
	 * @param error
	 * @return
	 */
	private boolean hasMessageType(List<Message> messages, MessageType messageType) {
		for (Message message : messages) {
			if (message.hasType(messageType)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This way we may ensure all annotations will be handled in correct
	 * priority. Also it might help to simplify code generation or validation.
	 * 
	 * @param lombokAnnotations
	 * @return sorted list
	 */
	private List<AnnotationDescriptor> sortByPriority(List<AnnotationDescriptor> lombokAnnotations) {
		Collections.sort(lombokAnnotations, priorityProvider);
		return lombokAnnotations;
	}
	
	/**
	 * This is needed to collect any info/warns/errors prior to any code
	 * generation
	 * 
	 * @param classDescriptor
	 * @param lombokAnnotations
	 * @return
	 */
	private List<Message> validateDescriptor(ClassDescriptor descriptor, List<AnnotationDescriptor> lombokAnnotations) {
		// TODO: implement intial validation
		return Collections.emptyList();
	}
	
}
