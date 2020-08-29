package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data @EqualsAndHashCode(callSuper = true) public class InterfaceDescriptor extends MemberDescriptor implements Descriptor {
	private InterfaceDescriptor[] extended; // extends
	private MemberDescriptor[] members;
	private PackageDescriptor packaged;
	private String name;
	
	private Class<?> reference;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.INTERFACE;
	}
	
	@Override public MemberType getMemberType() {
		return MemberType.INTERFACE;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
