package lombok.experimental.core.descriptor;

import java.util.Arrays;
import java.util.List;

public enum Modifier {
	PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, SYNCHRONIZED, VOLATILE, TRANSIENT, NATIVE, INTERFACE, ABSTRACT, STRICT,
	
	;
	
	public static List<Modifier> all() {
		return Arrays.asList(values());
	};
}
