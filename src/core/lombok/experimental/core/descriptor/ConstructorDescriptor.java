package lombok.experimental.core.descriptor;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.BiConsumer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data @EqualsAndHashCode(callSuper = true) public class ConstructorDescriptor extends MemberDescriptor {
	private MethodExceptionDescriptor[] exceptions;
	private MethodArgumentDescriptor[] arguments;
	private BiConsumer<Object, Object[]> handler;
	
	private Constructor<?> reference;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.CONSTRUCTOR;
	}
	
	@Override public MemberType getMemberType() {
		return MemberType.CONSTRUCTOR;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
