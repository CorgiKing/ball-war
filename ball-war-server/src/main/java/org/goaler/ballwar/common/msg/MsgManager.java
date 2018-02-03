package org.goaler.ballwar.common.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.goaler.ballwar.common.io.DataTransfer;

public class MsgManager implements Runnable {
	private int errorNum;
	private DataTransfer dataTransfer;
	private List<MsgFans> msgFanses = new ArrayList<>();

	public MsgManager(DataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
	}

	@Override
	public void run() {
		while (true) {
			Msg msg = dataTransfer.input(Msg.class);
			if (!checkMsg(msg)) {
				if (errorNum > 10) {
					break;
				}
				continue;
			}
			try {
				noticeMsgUpdate(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
		}
	}

	/**
	 * 返回信息是否合法
	 * 
	 * @param msg
	 * @return
	 */
	private boolean checkMsg(Msg msg) {
		if (!Msg.isLegal(msg)) {
			errorNum++;
			return false;
		}
		return true;
	}

	public boolean noticeMsgUpdate(Msg msg) {
		for (MsgFans fans : msgFanses) {
			boolean hasHandled = fans.handleMsg(msg);
			if (hasHandled) {
				return hasHandled;
			}
		}
		return false;
	}

	public void registerFans(MsgFans fans) {
		this.msgFanses.add(fans);
	}

	public boolean output(Serializable seria) {
		return dataTransfer.output(seria);
	}

	public String getIp() {
		return dataTransfer.getIp();
	}

	public DataTransfer getDataTransfer() {
		return dataTransfer;
	}

	public void setDataTransfer(DataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
	}

}
