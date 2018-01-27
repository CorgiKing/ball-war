package org.goaler.ballwar.common.msg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Msg implements Serializable {
	private static final long serialVersionUID = 2274575955347946299L;

	private String cmd;
	private Map<String, Object> params;

	public static boolean isLegal(Msg msg) {
		if (msg == null) {
			return false;
		}
		if (msg.getCmd() == null) {
			return false;
		}
		return true;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParam(String key, Class<T> clazz) {
		if (params != null) {
			Object obj = params.get(key);
			if (clazz.isInstance(obj)) {
				return (T) obj;
			}
		}
		return null;
	}

	public void setParam(String key, Object value) {
		if (params == null) {
			params = new HashMap<>();
		}
		params.put(key, value);
	}

	@Override
	public String toString() {
		return "Msg [cmd=" + cmd + ", params=" + params + "]";
	}
}
