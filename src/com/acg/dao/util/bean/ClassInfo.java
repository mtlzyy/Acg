package com.acg.dao.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassInfo {
	private Field[] fields;
	private Method[] methods;
	
	public Field[] getFields() {
		return fields;
	}
	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	public Method[] getMethods() {
		return methods;
	}
	public void setMethods(Method[] methods) {
		this.methods = methods;
	}

}
