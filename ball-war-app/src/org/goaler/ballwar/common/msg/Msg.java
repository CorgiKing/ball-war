package org.goaler.ballwar.common.msg;

import java.io.Serializable;
import java.util.Map;

public class Msg implements Serializable{
	private static final long serialVersionUID = 2274575955347946299L;
	
	private String cmd;
	private Map<String, Object> params;
	
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
	@Override
	public String toString() {
		return "Msg [cmd=" + cmd + ", params=" + params + "]";
	}
}
