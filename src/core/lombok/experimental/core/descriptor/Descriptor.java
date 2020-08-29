package lombok.experimental.core.descriptor;

import java.util.List;

import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

public interface Descriptor {
	
	List<Message> add(LombokCompiler compiler, boolean generate);
	
	DescriptorType getDescriptorType();
	
	List<Message> modify(LombokCompiler compiler, boolean generate);
	
	List<Message> remove(LombokCompiler compiler, boolean generate);
}
