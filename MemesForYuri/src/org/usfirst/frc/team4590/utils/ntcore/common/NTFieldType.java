package org.usfirst.frc.team4590.utils.ntcore.common;

import org.usfirst.frc.team4590.utils.ntcore.common.TypeRef.ArrayTypeRef;
import org.usfirst.frc.team4590.utils.ntcore.common.TypeRef.ClassTypeRef;

import edu.wpi.first.wpilibj.tables.ITable;

public enum NTFieldType {
	NUMBER(Number.class, Integer.TYPE, Double.TYPE, Float.TYPE),
	
	STRING(String.class),
	
	BOOLEAN(Boolean.class, Boolean.TYPE),
	
	NUMBER_ARRAY(new ArrayTypeRef(Number.class), new ArrayTypeRef(Integer.TYPE), 
			new ArrayTypeRef(Double.TYPE), new ArrayTypeRef(Float.TYPE)),
	
	STRING_ARRAY(new ArrayTypeRef(String.class)),
	
	BOOLEAN_ARRAY(new ArrayTypeRef(Boolean.class), new ArrayTypeRef(Boolean.TYPE)),
	
	COMPOSITE(ITable.class),
	
	ANY(Object.class),
	
	AUTO(Void.TYPE);
	
	private TypeRef[] m_refs;
	
	private NTFieldType(Class<?>... classes){
		m_refs = new TypeRef[classes.length];
		int i = 0;
		for (Class<?> cls : classes){
			m_refs[i] = new ClassTypeRef(cls);
			i++;
		}
	}
	
	private NTFieldType(TypeRef... refs){
		m_refs = refs;
	}
	
	public final boolean isInstance(Object obj){
		for (TypeRef ref : m_refs)
			if (ref.isInstance(obj)) return true;
		
		return false;
	}

	public boolean isClassInstance(Class<?> cls) {
		for (TypeRef ref : m_refs)
			if (ref.isClassInstance(cls)) return true;
		
		return false;
	}

	
}
