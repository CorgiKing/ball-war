package org.goaler.ballwar.common.discover.find;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.goaler.ballwar.common.discover.Discover;
import org.goaler.ballwar.common.discover.Multicast;
import org.goaler.ballwar.server.manager.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MulticastFind extends Find implements Discover, Multicast {
	private static final Logger log = LoggerFactory.getLogger(MulticastFind.class);
	private volatile boolean stop = false;
	private Set<String> serverIps = new HashSet<>();

	@Override
	public Set<String> find(int milliseconds) {
		startTask();

		// milliseconds后返回结果
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.info("查询服务器被中断！");
		} finally {
			this.close();
		}

		return serverIps;
	}

	@Override
	public void setRespondListener(RespondListener respListener) {
		this.respListener = respListener;
		startTask();
	}

	@Override
	public void close() {
		stop = true;
	}

	private void startTask() {
		// 发送线程
		FindTask findTask = new FindTask();
		ThreadPoolManager.getThreadPoolInstance().execute(findTask);
		// 接收线程
		ReceTask receTask = new ReceTask();
		ThreadPoolManager.getThreadPoolInstance().execute(receTask);
	}

	private void handle(String ip) {
		serverIps.add(ip);
	}

	class FindTask implements Runnable {

		public void run() {
			MulticastSocket sendSocket;
			InetAddress group = null;
			try {
				sendSocket = new MulticastSocket();
				group = InetAddress.getByName(GROUP_IP);
				sendSocket.joinGroup(group);
				sendSocket.setTimeToLive(255);
			} catch (IOException e) {
				log.error("创建多播失败！", e);
				return;
			}

			byte[] req = FIND_SERVER_REQ.getBytes();
			DatagramPacket packet = new DatagramPacket(req, req.length, group, PORT);
			while (!stop) {
				try {
					sendSocket.send(packet);
					log.debug("多播地址{}:{}", GROUP_IP, PORT);
					Thread.sleep(500);
				} catch (Exception e) {
					log.error("多播失败！", e);
				}
			}

			if (sendSocket != null) {
				sendSocket.close();
			}
		}
	}

	class ReceTask implements Runnable {

		public void run() {
			MulticastSocket receSocket = null;
			String serverIP = null;
			try {
				receSocket = new MulticastSocket(PORT);
				receSocket.setSoTimeout(SO_TIMEOUT_MILLISECONDS);

				byte[] buf = new byte[BUF_LEN];
				DatagramPacket recePacket = new DatagramPacket(buf, buf.length);
				while (!stop) {
					receSocket.receive(recePacket);
					String resp = new String(recePacket.getData(), 0, recePacket.getLength());
					if (FIND_SERVER_RESP.equals(resp)) {
						serverIP = recePacket.getAddress().getHostAddress();
						handle(serverIP);
						if (respListener != null) {
							respListener.oper(serverIP);
						}
						log.info("服务器IP地址为: " + serverIP);
					}
				}
			} catch (SocketTimeoutException e) {
				log.info("广播查找服务器超时结束");
			} catch (IOException e) {
				log.error("多播接收失败：", e);
			} finally {
				close();
				if (receSocket != null) {
					receSocket.close();
				}
			}
		}
	}

}
