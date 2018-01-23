package org.goaler.ballwar.server;

public class ServerFactory {
	private static Server server;
	
	public static Server getInstance(){
		if (server != null) {
			synchronized (ServerFactory.class) {
				if (server != null) {
					server = new SimpleServer();
				}
			}
		}
		
		return server;
	}
}
