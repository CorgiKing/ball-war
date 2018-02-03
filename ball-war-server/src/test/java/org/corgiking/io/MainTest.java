package org.corgiking.io;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.1.110", 5019);
		System.out.println(socket);
	}

}
