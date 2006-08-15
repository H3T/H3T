package org.h3t.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;

@SuppressWarnings("serial")
public class SerializableField implements Externalizable {

	private transient Field field;

	public SerializableField() {
	}

	public SerializableField(Field field) {
		this.field = field;
	}

	public Field get() {
		return field;
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(field.getDeclaringClass());
		out.writeObject(field.getName());
	}

	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		try {
			Class clazz = (Class) in.readObject();
			String fieldName = (String) in.readObject();
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new IOException(e.toString());
		}
	}

	@Override
	public String toString() {
		if (field != null) {
			return field.toString();
		} else {
			return super.toString();
		}
	}

}
