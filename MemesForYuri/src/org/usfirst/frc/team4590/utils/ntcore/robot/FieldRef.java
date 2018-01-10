package org.usfirst.frc.team4590.utils.ntcore.robot;

import java.lang.reflect.Field;

import org.usfirst.frc.team4590.utils.ntcore.common.NTFieldType;

public class FieldRef<T> {
	
	private final Field m_field;
	private final String m_ntName;
	private final TrackedFieldRefs<T> m_tracker;
	private final Class<T> m_owner;
	private NTFieldType m_type;
	
	private boolean m_access = false;
	
	protected FieldRef(Field field, String name, TrackedFieldRefs<T> tracker, Class<T> owner, NTFieldType type) {
		m_field = field;
		m_ntName = name;
		m_tracker = tracker;
		m_owner = owner;
		m_type = type;
	}

	protected boolean isAccessible(){
		return m_access = m_field.isAccessible();
	}
	
	protected void assureAccess(){
		if (m_access || isAccessible()) return;
		m_field.setAccessible(true);	
	}
	
	protected <F extends T> Object getFieldValue(F instance){
		if (instance == null) return null;
		assureAccess();
		try {
			return m_field.get(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}
	
	protected <F extends T> boolean setFieldValue(F instance, Object value){
		if (instance == null) return false;
		assureAccess();
		try {
			m_field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return false;
		}
		
		return true;
	}
	
	protected Class<T> getOwner(){
		return m_owner;
	}
	
	public NTFieldType getType() {
		return m_type;
	}
} 
