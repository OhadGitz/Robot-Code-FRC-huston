package org.usfirst.frc.team4590.utils.ntcore.robot;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.usfirst.frc.team4590.utils.ntcore.common.NTFieldType;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class TrackedFieldRefs<T> implements ITableListener{
	
	private final String m_path;
	private final HashMap<String, FieldRef<T>> m_fields;
	private final Class<T> m_class;
	
	private List<String> m_slaves = new LinkedList<String>();
	
	private T m_reference;
	
	public static <T> TrackedFieldRefs<T> create(Class<T> cls){
		NTConstLoad[] annots = cls.getAnnotationsByType(NTConstLoad.class);
		if (annots.length == 0) {
			throw new InvalidParameterException("Class '" + cls.getCanonicalName() + "' does not support NT syncing");
		}
		
		String pathArg = annots[0].path(), _path = pathArg;
		
		if (pathArg.equals(NetworkTablesSyncer.DEFAULT_NAME)){
			_path = cls.getName();
		}
		
		Class<T> _class = cls;
		return new TrackedFieldRefs<T>(_class, _path);
	}
	
	private TrackedFieldRefs(Class<T> cls, String path){
		m_path = path;
		m_fields = new HashMap<>();
		m_class = cls;
		for (Field field : cls.getDeclaredFields()){
			NTField[] annots = field.getAnnotationsByType(NTField.class);
			if (annots.length == 0) continue;
			NTField annot = annots[0];
			String nameArg = annot.name(), _name = nameArg;
			if (nameArg.equals(NetworkTablesSyncer.DEFAULT_NAME)){
				_name = field.getName();
			}
			
			NTFieldType typeArg = annot.type(), _type = typeArg;
			
			if (typeArg == NTFieldType.AUTO){
				for (NTFieldType type : NTFieldType.values()){
					if (type.isClassInstance(field.getType())){
						_type = type;
						break;
					}
				}
			}
			
			FieldRef<T> ref = new FieldRef<T>(field, _name, this, cls, _type);
			
			m_fields.put(_name, ref);
		}
	}
	
	protected boolean syncField(String field, Object value){
		return m_fields.get(field.substring(m_path.length() + 1)).setFieldValue(m_reference, value);
	}
	
	protected boolean fieldExists(String field){
		return m_fields.containsKey(field.substring(m_path.length() + 1));
	}
	
	public <F extends T> void setInstance(F instance){
		m_reference = instance;
	}
	
	private class ListenerSlave implements ITableListener{

		private String m_path;
		
		private ITable m_table;
		
		public ListenerSlave(String path){
			ListenerSlave.this.m_path = path;
			String ntPath = TrackedFieldRefs.this.m_path;
			String[] split = ntPath.split("/");
			ITable dataTable = NetworkTable.getTable("data_sync");
			
			for (String cur : split){
				dataTable = dataTable.getSubTable(cur);
			}
			
			m_table = dataTable;
		}
		
		@Override
		public void valueChanged(ITable source, String key, Object value, boolean isNew) {
			TrackedFieldRefs.this.valueChanged(m_table, 
					ListenerSlave.this.m_path + NetworkTable.PATH_SEPARATOR + key, value, isNew);
		}
		
	}
	
	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		if (fieldExists(m_path + NetworkTable.PATH_SEPARATOR + key) && isNew){
			syncField(m_path + NetworkTable.PATH_SEPARATOR + key, NetworkTablesSyncer.readValue(value, m_fields.get(key).getType()));
		}
	}

	protected String getPath() {
		return m_path;
	}

	protected void addSubListeners() {
		for (Entry<String, FieldRef<T>> entry : m_fields.entrySet()){
			String path = entry.getKey();
			if (path.contains("" + NetworkTable.PATH_SEPARATOR)){
				String tableName = path.substring(0, path.lastIndexOf(NetworkTable.PATH_SEPARATOR));
				if (!m_slaves.contains(tableName)){
					ITable table = NetworkTable.getTable("data_sync").getSubTable(m_path).getSubTable(tableName);
					ListenerSlave slave = new ListenerSlave(tableName);
					m_slaves.add(tableName);
					table.addTableListener(slave);
				}	
			}
		}
	}
 	
	
	
	
}
