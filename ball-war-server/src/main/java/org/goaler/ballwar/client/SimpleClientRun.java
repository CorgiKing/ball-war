package org.goaler.ballwar.client;

import org.goaler.ballwar.io.Msg;
import org.goaler.ballwar.io.bio.BioTcpSerialDataTransfer;
import org.goaler.ballwar.model.GameRoom;

public class SimpleClientRun extends ClientRunnable {
	private BioTcpSerialDataTransfer dataTransfer;
	private GameRoom room;

	@Override
	public void clientRun() {
		while(true){
			Msg msg = dataTransfer.input(Msg.class);
			switch (msg.getCmd()) {
			case "newGameRoom":
				newGameRoom(msg);
				break;
			case "startGame":
				
				break;

			default:
				break;
			}
		}
	}
	
	private void newGameRoom(Msg msg) {
		room = new GameRoom();
		room.setTitle((String) msg.getParams().get("title"));
		room.setDescription((String) msg.getParams().get("description"));
		room.setPwd((String) msg.getParams().get("pwd"));
	}

	public BioTcpSerialDataTransfer getDataTransfer() {
		return dataTransfer;
	}

	public void setDataTransfer(BioTcpSerialDataTransfer dataTransfer) {
		this.dataTransfer = dataTransfer;
	}
}
