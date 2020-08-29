package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.Data;

@Data public abstract class MemberDescriptor implements Descriptor {
	private List<AnnotationDescriptor> annotations;
	// TODO: ^^ move to children (generics are not needed for annotations)
	private GenericDescriptor[] generics;
	private List<Modifier> modifiers;
	
	// when this class is defined inside other class
	private MemberDescriptor memberOf;
	
	public abstract MemberType getMemberType();
}
