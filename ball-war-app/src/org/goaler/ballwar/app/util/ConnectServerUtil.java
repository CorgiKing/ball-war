package org.goaler.ballwar.app.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectServerUtil {
	private static final int PORT = 5019;

	public static void connect(final String ip, final ConCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Socket socket = connect(ip);
				if (socket != null) {
					callback.call(socket);
				}
			}
		}).start();
	}

	public static Socket connect(String ip) {

		InetAddress address = null;
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			GLog.error("goaler", "ip地址错误：ip-{},error-{}", ip, e);
			return null;
		}

		Socket socket = null;
		try {
			socket = new Socket(address, PORT);
		} catch (IOException e) {
			GLog.error("ip地址错误：ip-{},error-{}", ip, e);
		}

		return socket;
	}

	public interface ConCallback {
		void call(Socket socket);
	}

}
