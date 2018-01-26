package org.goaler.ballwar.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.goaler.ballwar.io.DataTransfer;

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
				break;
			}
			noticMsgUpdate(msg);
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
		}
	}
	
	private boolean checkMsg(Msg msg){
		if (msg == null) {
			errorNum++;
			if (errorNum > 10) {
				return false;
			}
		}else if (errorNum != 0) {
			errorNum = 0;
		}
		return true;
	}

	public boolean noticMsgUpdate(Msg msg) {
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
