package org.goaler.ballwar.msg;

import org.goaler.ballwar.io.Msg;

public interface MsgFans {
	/**
	 * 处理msg返回是否处理消息
	 * @param msg
	 * @return
	 */
	boolean handleMsg(Msg msg);
}
