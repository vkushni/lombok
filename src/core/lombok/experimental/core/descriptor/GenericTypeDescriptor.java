package lombok.experimental.core.descriptor;

import java.lang.reflect.Type;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data @EqualsAndHashCode public class GenericTypeDescriptor implements Descriptor {
	private GenericDescriptor generic;
	private Type genericType;
	private Class<?> type;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.GENERIC_TYPE;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
