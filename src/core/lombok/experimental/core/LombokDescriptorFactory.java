package lombok.experimental.core;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import lombok.experimental.core.descriptor.ClassDescriptor;
import lombok.experimental.core.descriptor.MemberType;
import lombok.experimental.core.descriptor.Modifier;

public interface LombokDescriptorFactory {
	
	@EqualsAndHashCode @Getter @SuperBuilder public static class Configuration {
		@Singular private List<MemberType> memberTypes;
		@Singular private List<Modifier> modifiers;
		private Configuration members;
		// ^^ classes, interfaces, methods, fields (declared or all depends on
		// modifiers and member types)
		
		private Configuration parents;
		// ^^ configuration for diving into parents (implements and extends)
		
		private int depth;
		// controls depth for parents
	}
	
	/**
	 * Describe class with provided configuration
	 * 
	 * @param type
	 * @param configuration
	 * @return
	 */
	ClassDescriptor get(Class<?> type, Configuration configuration);
}
