package org.usfirst.frc.team4590.utils.ntcore.robot;

import java.util.HashMap;

import org.usfirst.frc.team4590.utils.ntcore.common.NTFieldType;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class NetworkTablesSyncer {
	public static final String DEFAULT_NAME = "<NO_NAME>";
	
	private static final HashMap<Class<?>, TrackedFieldRefs<?>> m_fieldRefs = new HashMap<>();
	
	public static <T, F extends T> void addHook(F instance, Class<T> cls){
		TrackedFieldRefs<T> tracked = TrackedFieldRefs.create(cls);
		tracked.setInstance(instance);
		String ntPath = tracked.getPath();
		String[] split = ntPath.split("/");
		ITable dataTable = NetworkTable.getTable("data_sync");
		
		for (String cur : split){
			dataTable = dataTable.getSubTable(cur);
		}
		
		dataTable.addTableListener(tracked);
		tracked.addSubListeners();
		
		m_fieldRefs.put(cls, tracked);
	}

	public static Object readValue(Object value, NTFieldType type) {
		switch (type){
		case COMPOSITE:
			return constructObject(value);
		default:
			return value;
		}
	}

	/**
	 * Do not change this method unless you know what you are doing
	 * TODO: Add custom object constructor support
	 */
	private static Object constructObject(Object value) {
		return value; 
	}
}
