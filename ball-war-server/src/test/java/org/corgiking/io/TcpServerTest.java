package org.corgiking.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.goaler.ballwar.io.Msg;
import org.goaler.ballwar.io.bio.BioTcpSerialDataTransfer;

public class TcpServerTest {

	public static void main(String[] args) throws IOException, InterruptedException {

		ServerSocket server = new ServerSocket(5019);
		Socket clientSocket = server.accept();

		BioTcpSerialDataTransfer transfer = new BioTcpSerialDataTransfer(clientSocket);

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Msg msg = transfer.input(Msg.class);
					System.out.println(System.currentTimeMillis() + "--" + msg);
				}
			}
		});
		t1.start();

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Msg msg = new Msg();
					msg.setCmd("Server");
					transfer.output(msg);
				}
			}
		});
		t2.start();

		TimeUnit.SECONDS.sleep(3);
		t1.interrupt();
		t2.interrupt();
	}

}
