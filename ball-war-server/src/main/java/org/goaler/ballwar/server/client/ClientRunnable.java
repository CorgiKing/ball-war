package org.goaler.ballwar.server.client;

import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.msg.MsgManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1.多线程下fans的注册，会导致线程安全问题 2.想要退出线程只能抛异常
 */
public abstract class ClientRunnable implements Runnable {
	private Logger log = LoggerFactory.getLogger(getClass());
	private MsgManager msgManager;
	
	public ClientRunnable(DataTransfer dataTransfer) {
		msgManager = new MsgManager(dataTransfer);
	}

	@Override
	public void run() {
		try {
			prepare();

			if (auth()) {
				clientRun();
			}
			destroy();
		} catch (InterruptedException e) {
			log.info("客户端退出：ip-{}",msgManager.getIp());
		}
	}

	public void prepare() throws InterruptedException {
	}

	public boolean auth() throws InterruptedException{
		return true;
	}

	public abstract void clientRun() throws InterruptedException;

	public void destroy() throws InterruptedException{
	}

	public MsgManager getMsgManager() {
		return msgManager;
	}

	public void setMsgManager(MsgManager msgManager) {
		this.msgManager = msgManager;
	}
	
}
