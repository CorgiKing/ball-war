package org.goaler.ballwar.server;

import java.util.concurrent.atomic.LongAdder;

public abstract class Server {
	/**
	 * 玩家的个数
	 */
	private LongAdder playerNum = new LongAdder();
	
	public void start() {
		prepare();
		run();
		destroy();
	}

	protected void prepare() {

	}

	abstract void run();

	protected void destroy() {

	}

	public LongAdder getPlayerNum() {
		return playerNum;
	}
	
}
