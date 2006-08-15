package org.h3t.aspect;
import java.io.ObjectStreamException;

public interface EntitySerializer {
	Object writeReplace() throws ObjectStreamException;

}
