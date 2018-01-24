package org.goaler.ballwar.client;

import org.goaler.ballwar.model.GameRoom;

public class GameRun {
	private GameRoom room;
	
	public GameRun(GameRoom room){
		this.room = room;
	}

	public GameRoom getRoom() {
		return room;
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}
}
