package org.h3t.util;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;

@SuppressWarnings("serial")
public class SerializableMethod implements Externalizable{
	
	private transient Method method;
	
	public SerializableMethod(Method method){
		this.method = method;
	}
	
	public Method get(){
		return method;
	}

	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(method.getDeclaringClass());
		out.writeObject(method.getName());
		out.writeObject(method.getParameterTypes());
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		try {
			Class clazz = (Class)in.readObject();
			String methodName = (String)in.readObject();
			Class[] paramTypes = (Class<?>[])in.readObject();
			
			method = clazz.getMethod(methodName, paramTypes);
		} catch (NoSuchMethodException e) {
			throw new IOException(e.getMessage());
		} 
		
	}
	
	@Override
	public String toString() {
		if (method != null) {
			return method.toString();
		} else {
			return method.toString();
		}
	}
}
