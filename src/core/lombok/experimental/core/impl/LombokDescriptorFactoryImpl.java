package lombok.experimental.core.impl;

import lombok.experimental.core.LombokDescriptorFactory;
import lombok.experimental.core.descriptor.ClassDescriptor;

public abstract class LombokDescriptorFactoryImpl implements LombokDescriptorFactory {
	
	@Override public ClassDescriptor get(Class<?> type, Configuration configuration) {
		if (type == null || configuration == null) {
			return null;
		}
		
		return null;
	}
}
