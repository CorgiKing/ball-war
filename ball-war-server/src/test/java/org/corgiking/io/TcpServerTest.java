package org.corgiking.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.goaler.ballwar.io.Msg;
import org.goaler.ballwar.io.bio.BioTcpSerialDataTransfer;

public class TcpServerTest {

	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(5019);
		Socket clientSocket = server.accept();

		BioTcpSerialDataTransfer transfer = new BioTcpSerialDataTransfer(clientSocket);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
				Msg msg = transfer.input(Msg.class);
				System.out.println(msg);
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
				Msg msg = new Msg();
				msg.setCmd("Server");
				transfer.output(msg);
				}
			}
		}).start();
	}

}
