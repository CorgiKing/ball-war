package org.goaler.ballwar.common.io;

import java.io.Serializable;

public interface DataTransfer {
	public static final int RW_TIMEOUT = 600000;
	
	boolean output(Serializable seria);
	<T> T input(Class<T> clazz);
	String getIp();
}
