package org.goaler.ballwar.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.goaler.ballwar.player.ClientRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer extends Server {
	private static final Logger log = LoggerFactory.getLogger(SimpleServer.class);

	protected SimpleServer() {
	}

	@Override
	void run() {

		try {
			ServerSocket serverSocket = new ServerSocket(5019);
			Socket clientSocket = null;
			while (true) {
				log.info("等待玩家...");
				clientSocket = serverSocket.accept();
				log.info("增加一个玩家：{}" , clientSocket.getInetAddress());
				new Thread(new ClientRunnable(clientSocket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
