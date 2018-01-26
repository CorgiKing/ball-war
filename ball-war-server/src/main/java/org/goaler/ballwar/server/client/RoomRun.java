package org.goaler.ballwar.server.client;

import org.goaler.ballwar.common.model.RoomInfo;

/**
 * 游戏房间运行类
 * @author goaler
 *
 */
public class RoomRun {
	public static final int ROOM_STATE_QUIET = 0;
	public static final int ROOM_STATE_PAUSE = 1;
	public static final int ROOM_STATE_RUNNING = 2;
	public static final int ROOM_STATE_CLOSE = 3;
	
	private RoomInfo room;
	
	private int state = ROOM_STATE_QUIET;
	
	public RoomRun(RoomInfo room) {
		this.room = room;
	}
	
	public void begin(){
		
	}
	
}
