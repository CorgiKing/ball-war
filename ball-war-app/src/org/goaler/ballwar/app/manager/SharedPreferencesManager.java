package org.goaler.ballwar.app.manager;

import org.goaler.ballwar.app.util.SerializeUtil;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPreferencesManager {
	private SharedPreferences sp;

	public SharedPreferencesManager(SharedPreferences sp) {
		this.sp = sp;
	}

	public <T> T getData(String key, Class<T> clazz) {
		String json = sp.getString(key, "df");
		Log.i("goaler", "sharedPreferences:" + json);
		if (json == null || "df".equals(json)) {
			return null;
		} else {
			return SerializeUtil.toObj(json, clazz);
		}
	}

	public <T> T getData(String key, Class<T> clazz, DefectOperator<T> oper) {
		String json = sp.getString(key, "df");
		Log.i("goaler", "sharedPreferences:" + json);
		if (json == null || "df".equals(json)) {
			return oper.oper(sp);
		} else {
			return SerializeUtil.toObj(json, clazz);
		}
	}

	public <T> void save(String key, T data) {
		String json = SerializeUtil.toJson(data);
		Editor editor = sp.edit();
		editor.putString(key, json);
		editor.commit();
	}

	public interface DefectOperator<T> {
		T oper(SharedPreferences sp);
	}
}
