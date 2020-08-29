package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.Data;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data public class MethodArgumentDescriptor implements Descriptor {
	private AnnotationDescriptor[] annotations;
	private GenericDescriptor generics;
	private MethodDescriptor method;
	private Modifier[] modifiers;
	private String name;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.METHOD_ARGUMENT;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
