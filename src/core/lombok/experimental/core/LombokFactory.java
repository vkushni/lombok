package lombok.experimental.core;

import java.awt.TrayIcon.MessageType;
import java.util.List;

import lombok.Data;
import lombok.experimental.core.descriptor.Descriptor;

public interface LombokFactory {
	
	public enum Action {
		ADD {
			@Override public List<Message> apply(LombokCompiler compiler, Descriptor descriptor, boolean generate) {
				return descriptor.add(compiler, generate);
			}
		},
		MODIFY {
			@Override public List<Message> apply(LombokCompiler compiler, Descriptor descriptor, boolean generate) {
				return descriptor.modify(compiler, generate);
			}
		},
		REMOVE {
			@Override public List<Message> apply(LombokCompiler compiler, Descriptor descriptor, boolean generate) {
				return descriptor.remove(compiler, generate);
			}
		};
		
		public abstract List<Message> apply(LombokCompiler compiler, Descriptor descriptor, boolean generate);
	}
	
	@Data public static class Message {
		private MessageType type;
		private String value;
		
		public boolean hasType(MessageType expected) {
			return type == expected;
		}
	}
	
	public enum MessageLevel {
		INFO, WARN, ERROR;
	}
	
	/**
	 * Used to collect generation messages and if enabled generate vanilla java
	 * for the provided class
	 * 
	 * @param type
	 */
	List<Message> generate(Class<?> type, boolean generate);
}
