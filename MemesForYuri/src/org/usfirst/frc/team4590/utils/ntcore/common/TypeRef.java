package org.usfirst.frc.team4590.utils.ntcore.common;

import java.lang.reflect.Array;


public interface TypeRef {
	boolean isInstance(Object obj);
	boolean isClassInstance(Class<?> cls);
	
	public static class ClassTypeRef implements TypeRef{
		
		private Class<?> m_class;
		
		public ClassTypeRef(Class<?> cls){
			m_class = cls;
		}
		
		public boolean isInstance(Object obj){
			return m_class.isInstance(obj);
		}
		
		public boolean isClassInstance(Class<?> cls){
			return m_class.isAssignableFrom(cls);
		}
	}
	
	public static class ArrayTypeRef extends ClassTypeRef{

		private TypeRef m_type;
		
		public ArrayTypeRef(TypeRef type) {
			super(Array.class);
			m_type = type;
		}
		
		public ArrayTypeRef(Class<?> cls) {
			super(Array.class);
			m_type = new ClassTypeRef(cls);
		}
		
		@Override
		public boolean isInstance(Object obj){
			return super.isInstance(obj) && m_type.isClassInstance(obj.getClass().getComponentType());
		}

		@Override
		public boolean isClassInstance(Class<?> cls){
			return super.isClassInstance(cls) && m_type.isClassInstance(cls.getComponentType());
		}
		
	}
}
