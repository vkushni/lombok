package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.Data;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data public class PackageDescriptor implements Descriptor {
	private String name;
	private String full;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.PACKAGE;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
