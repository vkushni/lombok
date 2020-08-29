package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data @EqualsAndHashCode(callSuper = true) public class ClassDescriptor extends MemberDescriptor {
	private List<InterfaceDescriptor> implemented;
	private List<MemberDescriptor> members;
	private PackageDescriptor packaged;
	private ClassDescriptor extended;
	private String name;
	
	private Class<?> reference;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.CLASS;
	}
	
	@Override public MemberType getMemberType() {
		return MemberType.CLASS;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
	
}
