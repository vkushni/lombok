package lombok.experimental.core;

import java.util.List;

import lombok.experimental.core.LombokFactory.Message;
import lombok.experimental.core.descriptor.AnnotationDescriptor;
import lombok.experimental.core.descriptor.ClassDescriptor;
import lombok.experimental.core.descriptor.ConstructorDescriptor;
import lombok.experimental.core.descriptor.FieldDescriptor;
import lombok.experimental.core.descriptor.GenericDescriptor;
import lombok.experimental.core.descriptor.GenericTypeDescriptor;
import lombok.experimental.core.descriptor.InterfaceDescriptor;
import lombok.experimental.core.descriptor.MethodArgumentDescriptor;
import lombok.experimental.core.descriptor.MethodDescriptor;
import lombok.experimental.core.descriptor.MethodExceptionDescriptor;
import lombok.experimental.core.descriptor.PackageDescriptor;

/**
 * This interface is required to translate internal descriptor objects into real
 * code or whatever is needed. Supposed to return only messages which will be
 * displayed or dumped or whatever. Main purpose of this interface is to
 * decouple high-level business logic from low-level compilers.
 */
// TODO: some long reading is here
/*
 * It looks odd to have such amount of methods in a single class, but this would
 * allow testing it properly. Compiler may be filled with functionality on
 * demand. And once it is implemented and fully covered with tests it may be
 * re-used everywhere. Thus no need to have similar code for handling different
 * annotations. Right now handler classes for Eclipse compiler and for Java
 * compiler have same business logic inside with adding this as an interface, it
 * will be located in single place. In theory compiler implementation may be
 * replaced with anything like JavasciptCompiler, PythonCompiler, whatever...
 * This is slightly limited by hierarchy of descriptors, but it can be fixed in
 * the way to be like a real tree structure. After that it may be used for
 * generating everything (like sql's etc.)
 */
// TODO: right now compiler does return only messages, but most likely it should
// return smth else.
// i'm pretty sure compilers may dump all messages within. and throw errors when
// everything is pretty bad
// this would allow to pass some results back to be re-used or stored or
// whatever.
public interface LombokCompiler {
	
	// TODO: method names are subject of discussion
	
	List<Message> add(AnnotationDescriptor descriptor, boolean generate);
	
	List<Message> add(ClassDescriptor descriptor, boolean generate);
	
	List<Message> add(ConstructorDescriptor descriptor, boolean generate);
	
	List<Message> add(FieldDescriptor descriptor, boolean generate);
	
	List<Message> add(GenericDescriptor descriptor, boolean generate);
	
	List<Message> add(GenericTypeDescriptor descriptor, boolean generate);
	
	List<Message> add(InterfaceDescriptor descriptor, boolean generate);
	
	List<Message> add(MethodArgumentDescriptor descriptor, boolean generate);
	
	List<Message> add(MethodDescriptor descriptor, boolean generate);
	
	List<Message> add(MethodExceptionDescriptor descriptor, boolean generate);
	
	List<Message> add(PackageDescriptor descriptor, boolean generate);
	
	List<Message> modify(AnnotationDescriptor descriptor, boolean generate);
	
	List<Message> modify(ClassDescriptor descriptor, boolean generate);
	
	List<Message> modify(ConstructorDescriptor descriptor, boolean generate);
	
	List<Message> modify(FieldDescriptor descriptor, boolean generate);
	
	List<Message> modify(GenericDescriptor descriptor, boolean generate);
	
	List<Message> modify(GenericTypeDescriptor descriptor, boolean generate);
	
	List<Message> modify(InterfaceDescriptor descriptor, boolean generate);
	
	List<Message> modify(MethodArgumentDescriptor descriptor, boolean generate);
	
	List<Message> modify(MethodDescriptor descriptor, boolean generate);
	
	List<Message> modify(MethodExceptionDescriptor descriptor, boolean generate);
	
	List<Message> modify(PackageDescriptor descriptor, boolean generate);
	
	List<Message> remove(AnnotationDescriptor descriptor, boolean generate);
	
	List<Message> remove(ClassDescriptor descriptor, boolean generate);
	
	List<Message> remove(ConstructorDescriptor descriptor, boolean generate);
	
	List<Message> remove(FieldDescriptor descriptor, boolean generate);
	
	List<Message> remove(GenericDescriptor descriptor, boolean generate);
	
	List<Message> remove(GenericTypeDescriptor descriptor, boolean generate);
	
	List<Message> remove(InterfaceDescriptor descriptor, boolean generate);
	
	List<Message> remove(MethodArgumentDescriptor descriptor, boolean generate);
	
	List<Message> remove(MethodDescriptor descriptor, boolean generate);
	
	List<Message> remove(MethodExceptionDescriptor descriptor, boolean generate);
	
	List<Message> remove(PackageDescriptor descriptor, boolean generate);
}
