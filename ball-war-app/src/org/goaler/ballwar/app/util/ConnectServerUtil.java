package org.goaler.ballwar.app.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.goaler.ballwar.common.io.bio.BioTcpSerialDataTransfer;
import org.goaler.ballwar.common.msg.MsgManager;

public class ConnectServerUtil {

	public static void connect(final String ip, final int port, final ConCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Socket socket = connect(ip, port);

				if (socket == null) {
					return;
				}
				try {
					MsgManager msgManager = new MsgManager(new BioTcpSerialDataTransfer(socket));
					callback.call(msgManager);
				} catch (IOException e) {
					GLog.error("goaler", "创建TcpDataTransfer失败！ip-{}", ip);
				}
			}
		}).start();
	}

	public static Socket connect(String ip, int port) {

		InetAddress address = null;
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			GLog.error("goaler", "ip地址错误：ip-{},error-{}", ip, e);
			return null;
		}

		Socket socket = null;
		try {
			socket = new Socket(address, port);
		} catch (IOException e) {
			GLog.error("ip地址错误：ip-{},error-{}", ip, e);
		}

		return socket;
	}

	public interface ConCallback {
		void call(MsgManager msgManager);
	}

}
