package org.goaler.ballwar.app.util;

import com.google.gson.Gson;

public class SerializeUtil {
	private static final Gson GSON = new Gson();

	public static <T> String toJson(T obj) {
		return GSON.toJson(obj);
	}

	public static <T> T toObj(String json, Class<T> clazz) {
		return GSON.fromJson(json, clazz);
	}
}
