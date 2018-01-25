package org.corgiking.io;

public class ThreadTest {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.err.println("start");
				Thread.currentThread().interrupt();
				System.out.println("end");
			}
		}).start();
	}

}
