package org.goaler.ballwar.client;

public abstract class ClientRunnable implements Runnable {

	@Override
	public void run() {
		if (auth()) {
			clientRun();
		}
	}

	public boolean auth() {
		return true;
	}

	public abstract void clientRun();

}
