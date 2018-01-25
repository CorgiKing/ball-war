package org.goaler.ballwar.io.bio;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.goaler.ballwar.io.DataTransfer;

public class BioUdpSerialDataTransfer implements DataTransfer {

	private DatagramSocket socket;

	public BioUdpSerialDataTransfer() throws SocketException {
		socket = new DatagramSocket();
	}

	public BioUdpSerialDataTransfer(int port) throws SocketException {
		socket = new DatagramSocket(port);
	}

	@Override
	public boolean output(Serializable seria) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T input(Class<T> clazz) {
		byte[] buf = new byte[65536];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		return null;
	}

	@Override
	public String getIp() {
		// TODO Auto-generated method stub
		return null;
	}

}
