package org.corgiking.io;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.goaler.ballwar.common.io.bio.BioTcpSerialDataTransfer;
import org.goaler.ballwar.common.msg.Msg;

public class TcpClientTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("localhost", 5019);
		BioTcpSerialDataTransfer transfer = new BioTcpSerialDataTransfer(socket);
		
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
				Msg msg = new Msg();
				msg.setCmd("Client");
				while(true){
					msg.setCmd(System.currentTimeMillis()+"");
				transfer.output(msg);
				}
			}
		}).start();
		
	}

}
