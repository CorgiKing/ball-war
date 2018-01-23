package org.goaler.ballwar.io;

import java.io.Serializable;

public interface DataTransfer {
	boolean output(Serializable seria);
	Msg input();
}
