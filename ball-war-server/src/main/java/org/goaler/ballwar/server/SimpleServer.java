package org.goaler.ballwar.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.goaler.ballwar.client.ClientRunnable;
import org.goaler.ballwar.client.SimpleClientRun;
import org.goaler.ballwar.common.io.bio.BioTcpSerialDataTransfer;
import org.goaler.ballwar.util.ThreadPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleServer extends Server {
	private static final Logger log = LoggerFactory.getLogger(SimpleServer.class);

	protected SimpleServer() {
	}

	@Override
	void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(5019);
			Socket clientSocket = null;
			while (true) {
				log.info("等待玩家...");
				clientSocket = serverSocket.accept();
				log.info("增加一个玩家：{}", clientSocket.getInetAddress());

				ThreadPoolService.getThreadPoolInstance().execute(createClientRun(clientSocket));
			}
		} catch (IOException e) {
			log.error("服务器异常！", e);
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					log.error("服务器关闭异常！", e);
				}
			}
		}
	}

	public ClientRunnable createClientRun(Socket clientSocket) throws IOException {
		ClientRunnable clientRunnable = new SimpleClientRun(new BioTcpSerialDataTransfer(clientSocket));
		return clientRunnable;
	}
}
