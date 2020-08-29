package lombok.experimental.core;

import java.util.Comparator;

import lombok.experimental.core.descriptor.AnnotationDescriptor;

public interface LombokPriorityFactory extends Comparator<AnnotationDescriptor> {
	int getPriority(AnnotationDescriptor descriptor);
}
