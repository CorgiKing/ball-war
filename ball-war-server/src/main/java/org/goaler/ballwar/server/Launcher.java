package org.goaler.ballwar.server;

import org.goaler.ballwar.server.core.Server;
import org.goaler.ballwar.server.core.ServerFactory;

public class Launcher {

	public static void main(String[] args) {
		Server server = ServerFactory.getInstance();
		server.start();
	}

}
