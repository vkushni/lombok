package lombok.experimental.core.descriptor;

import lombok.Data;

@Data public class MethodExceptionDescriptor {
	private MethodDescriptor method;
	private Class<?> reference;
}
