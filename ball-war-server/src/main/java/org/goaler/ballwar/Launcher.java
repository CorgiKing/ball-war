package org.goaler.ballwar;

import org.goaler.ballwar.server.Server;
import org.goaler.ballwar.server.ServerFactory;

public class Launcher {

	public static void main(String[] args) {
		Server server = ServerFactory.getInstance();
		server.start();
	}

}
