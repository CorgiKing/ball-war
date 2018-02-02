package org.goaler.ballwar.common.io.bio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.goaler.ballwar.common.io.DataTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioUdpSerialDataTransfer implements DataTransfer {
	private static final Logger log = LoggerFactory.getLogger(BioUdpSerialDataTransfer.class);
	public static final int DEFAULT_PORT = 5019;
	
	private DatagramSocket socket;
	private String destIp;
	private int destPort;

	public BioUdpSerialDataTransfer(String destIp, int destPort){
		try {
			socket = new DatagramSocket();
			this.destIp = destIp;
			this.destPort = destPort;
		} catch (SocketException e) {
			log.error("udpDataTransfer创建失败！");
		}
	}

	public BioUdpSerialDataTransfer(int port) {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			log.error("udpDataTransfer创建失败！");
		}
	}

	@Override
	public boolean output(Serializable seria) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(seria);
			oo.flush();
			byte[] buf = bo.toByteArray();
			
			InetAddress address = InetAddress.getByName(destIp);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, destPort);
			socket.send(packet);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public <T> T input(Class<T> clazz) {
		byte[] buf = new byte[65536];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		try {
			socket.receive(packet);

			byte[] objbuf = packet.getData();
			ObjectInputStream oi = new ObjectInputStream(new ByteArrayInputStream(objbuf));
			Object obj = oi.readObject();
			if (clazz.isInstance(obj)) {
				return clazz.cast(obj);
			}
		} catch (IOException | ClassNotFoundException e) {
			log.info("接收数据失败！{}",e.getMessage());
		}
		return null;

	}

	@Override
	public String getIp() {
		return socket.getInetAddress().getHostAddress();
	}

}
