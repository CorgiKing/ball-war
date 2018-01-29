package org.goaler.ballwar.common.discover.notice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;

import org.goaler.ballwar.common.discover.Discover;
import org.goaler.ballwar.common.discover.Multicast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastNotice extends Notice implements Discover, Multicast {
	private static final Logger log = LoggerFactory.getLogger(MulticastNotice.class);
	private boolean stop;

	@Override
	public void listenFind() {
		MulticastSocket receSocket = null;
		InetAddress group = null;
		try {
			receSocket = new MulticastSocket(PORT);
			group = InetAddress.getByName(GROUP_IP);
			receSocket.joinGroup(group);// 只有加入组播才可以收数据，但是无需加入组播就可发数据
			receSocket.setSoTimeout(SO_TIMEOUT_MILLISECONDS);

		} catch (IOException e) {
			log.error("创建多播失败！", e);
			return;
		}

		byte[] buf = new byte[BUF_LEN];
		DatagramPacket recePacket = new DatagramPacket(buf, buf.length);
		while (!stop) {
			try {
				log.info("在{}端口接收组播信息。", PORT);
				receSocket.receive(recePacket);
				log.info("接收到{}搜寻服务器请求", recePacket.getAddress().getHostAddress());
				String req = new String(recePacket.getData(), 0, recePacket.getLength());
				if (FIND_SERVER_REQ.equals(req)) {
					// 发送本机ip
					InetAddress address = recePacket.getAddress();
					byte[] info = FIND_SERVER_RESP.getBytes();
					DatagramPacket packet = new DatagramPacket(info, info.length, address, PORT);
					receSocket.send(packet);
					log.info("告知{}服务器IP地址", address.getHostAddress());
				}
			} catch (SocketTimeoutException e) {
			} catch (Exception e) {
				log.error("接收信息失败", e);
				break;
			}
		}
		if (receSocket != null) {
			receSocket.close();
		}
	}

	@Override
	public void close() {
		stop = true;
	}

}
