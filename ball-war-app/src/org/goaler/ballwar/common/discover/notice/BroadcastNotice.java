package org.goaler.ballwar.common.discover.notice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.goaler.ballwar.common.discover.Broadcast;
import org.goaler.ballwar.common.discover.Discover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastNotice extends Notice implements Discover, Broadcast {
	private static final Logger log = LoggerFactory.getLogger(BroadcastNotice.class);
	private boolean stop;

	@Override
	public void listenFind() {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(PORT);
			socket.setSoTimeout(SO_TIMEOUT_MILLISECONDS);
		} catch (SocketException e) {
			log.error("创建广播失败！", e);
		}
		byte[] buf = new byte[BUF_LEN];
		DatagramPacket recePacket = new DatagramPacket(buf, buf.length);
		while (!stop) {
			try {
				log.info("在{}端口接收广播信息。", PORT);
				socket.receive(recePacket);
				log.info("接收到{}搜寻服务器请求", recePacket.getAddress().getHostAddress());
				String req = new String(recePacket.getData(), 0, recePacket.getLength());
				if (FIND_SERVER_REQ.equals(req)) {
					// 发送本机ip
					InetAddress address = recePacket.getAddress();
					byte[] info = FIND_SERVER_RESP.getBytes();
					DatagramPacket packet = new DatagramPacket(info, info.length, address, PORT - 1);
					socket.send(packet);
					log.info("告知{}服务器IP地址", address.getHostAddress());
				}
			} catch (SocketTimeoutException e) {
			} catch (IOException e) {
				log.error("接收信息失败", e);
				break;
			}
		}
		if (socket != null) {
			socket.close();
		}

	}

	@Override
	public void close() {
		stop = true;
	}
}
