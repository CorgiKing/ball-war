package org.nineteens.ballwar.app.manager;

import org.nineteens.ballwar.app.util.SerializeUtil;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesManager {
	private SharedPreferences sp;

	public SharedPreferencesManager(SharedPreferences sp) {
		this.sp = sp;
	}

	public <T> T getData(String key, Class<T> clazz) {
		String roleJson = sp.getString(key, "df");
		if ("df".equals(roleJson)) {
			return null;
		} else {
			return SerializeUtil.toObj(roleJson, clazz);
		}
	}
	
	public <T> T getData(String key, Class<T> clazz, Operator<T> oper) {
		String roleJson = sp.getString(key, "df");
		if ("df".equals(roleJson)) {
			return oper.oper(sp);
		} else {
			return SerializeUtil.toObj(roleJson, clazz);
		}
	}
	
	public <T> void save(String key, T data){
		String json = SerializeUtil.toJson(data);
		Editor editor = sp.edit();
		editor.putString(key, json);
		editor.commit();
	}
	
	public interface Operator<T>{
		T oper(SharedPreferences sp);
	}
}

