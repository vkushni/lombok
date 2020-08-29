package lombok.experimental.core.descriptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.core.LombokCompiler;
import lombok.experimental.core.LombokFactory.Message;

@Data @EqualsAndHashCode(callSuper = true) public class MethodDescriptor extends MemberDescriptor {
	private MethodExceptionDescriptor[] exceptions;
	private MethodArgumentDescriptor[] arguments;
	private Function<Object[], Object> handler;
	private GenericDescriptor returnType;
	// TODO: ^^ define separate descriptor to define return type
	
	private Method reference;
	
	@Override public List<Message> add(LombokCompiler compiler, boolean generate) {
		return compiler.add(this, generate);
	}
	
	@Override public DescriptorType getDescriptorType() {
		return DescriptorType.METHOD;
	}
	
	@Override public MemberType getMemberType() {
		return MemberType.METHOD;
	}
	
	@Override public List<Message> modify(LombokCompiler compiler, boolean generate) {
		return compiler.modify(this, generate);
	}
	
	@Override public List<Message> remove(LombokCompiler compiler, boolean generate) {
		return compiler.remove(this, generate);
	}
}
