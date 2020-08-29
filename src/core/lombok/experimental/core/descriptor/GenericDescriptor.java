package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.Data;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

// <T, X extends Object & Comparable<T>>
// or
// ? super Object
@Data public class GenericDescriptor implements Descriptor {
	private String name;
	private boolean wildcard; // when defined as ?
	
	// when wildcard: null or size = 0
	// when NOT wildcard: size > = 0
	private List<GenericDescriptor> extendsOf;
	
	// when wildcard: if wildcard is li
	// when NOT wildcard: null
	private GenericDescriptor superOf; //
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.GENERIC;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
