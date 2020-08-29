package lombok.experimental.core.descriptor;

import java.lang.annotation.Annotation;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data @EqualsAndHashCode(callSuper = true) public class AnnotationDescriptor extends MemberDescriptor {
	private List<AnnotationDescriptor> annotations;
	private List<Modifier> modifiers;
	private Descriptor target; // type, method, method argument, field
	private String name;
	
	private Annotation reference;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.ANNOTATION;
	}
	
	@Override public MemberType getMemberType() {
		return MemberType.ANNOTATION;
	}
	
	/**
	 * Checks if reference annotation matches provided one
	 * 
	 * @param type
	 * @return
	 */
	public boolean hasAnnotation(Class<?> type) {
		if (reference == null) {
			return false;
		}
		return reference.annotationType() == type;
	}
	
	/**
	 * Should return true if annotation is lombok one
	 * 
	 * @return
	 */
	public boolean isLombok() {
		// TODO: implement filtering by lombok annotations
		// most suitable way - annotate each lombok annotation with some generic
		// one
		
		return hasAnnotation(Setter.class);
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
