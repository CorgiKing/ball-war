package org.corgiking.io;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.goaler.ballwar.io.Msg;
import org.goaler.ballwar.io.bio.BioTcpSerialDataTransfer;

public class TcpClientTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("localhost", 5019);
		BioTcpSerialDataTransfer transfer = new BioTcpSerialDataTransfer(socket);
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
				Msg msg = transfer.input();
				System.out.println(msg);
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true){
				Msg msg = new Msg();
				msg.setCmd("Client");
				transfer.output(msg);
				}
			}
		}).start();
		
	}

}
