package org.goaler.ballwar.server.core;

import java.util.concurrent.atomic.LongAdder;

public abstract class Server {
	/**
	 * 玩家的个数
	 */
	private LongAdder playerNum = new LongAdder();
	
	public void start() {
		prepare();
		running();
		destroy();
	}

	protected void prepare() {

	}

	abstract void running();

	protected void destroy() {

	}

	public LongAdder getPlayerNum() {
		return playerNum;
	}
	
}
