package org.goaler.ballwar.server.client;

import java.util.HashMap;
import java.util.Map;

import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.server.soul.CellSoul;

/**
 * 游戏房间运行类
 * 
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

	private Map<Integer, CellSoul> entitys = new HashMap<>();

	public RoomRun(RoomInfo room) {
		this.room = room;
	}

	public void start() {
		init();
	}
	
	/**
	 * 初始化地图
	 */
	private void init() {
		
	}

	public void addEntity(CellSoul cell) {
		entitys.put(cell.genId(), cell);
	}

	public RoomInfo exportRoomInfo() {
		return room;
	}
}
