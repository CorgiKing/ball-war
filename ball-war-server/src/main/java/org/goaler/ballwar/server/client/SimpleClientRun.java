package org.goaler.ballwar.server.client;

import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.model.Device;
import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.common.msg.Msg;
import org.goaler.ballwar.common.msg.MsgFans;
import org.goaler.ballwar.server.manager.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleClientRun extends ClientRunnable implements MsgFans {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private RoomRun roomRun;
	private GameRun gameRun;
	private Role role;
	private Device device;

	public SimpleClientRun(DataTransfer dataTransfer) {
		super(dataTransfer);
	}

	@Override
	public void prepare() {
		// 建立通讯
		ThreadPoolManager.getThreadPoolInstance().execute(getMsgManager());
		getMsgManager().registerFans(this);
	}

	@Override
	public void clientRun() {

	}

	@Override
	public boolean handleMsg(Msg msg) {
		switch (msg.getCmd()) {
		case "initInfo":
			initInfo(msg);
			break;
		case "newGameRoom":
			newGameRoom(msg);
			break;
		case "startGame":
			startGame(msg);
			break;

		default:
			return false;
		}

		return true;
	}

	/**
	 * 初始化玩家信息
	 * 
	 * @param msg
	 */
	private void initInfo(Msg msg) {
		role = msg.getParam("role", Role.class);
		device = msg.getParam("device", Device.class);
		log.info("initInfo：role-{},device-{}", role, device);
	}

	/**
	 * 新建游戏房间
	 * 
	 * @param msg
	 */
	private void newGameRoom(Msg msg) {
		RoomInfo room = msg.getParam("room", RoomInfo.class);
		room.setOwner(role);
		log.info("newGameRoom：room-{}", room);
		roomRun = new RoomRun(room);
	}

	/**
	 * 开始游戏
	 * 
	 * @param msg
	 */
	private void startGame(Msg msg) {
		if (!roomRun.isStarted() && roomRun.getOwner().equals(role)) {
			// 游戏未开始，并且是房主
			roomRun.start();
		}
		if (roomRun.isStarted()) {
			gameRun = new GameRun();
			gameRun.setRole(role);
			gameRun.setRoomRun(roomRun);
			gameRun.setDevice(device);
			gameRun.setMsgManager(getMsgManager());
			getMsgManager().registerFans(gameRun);
			gameRun.start();
			log.info("startGame：开始游戏");
		}
	}

	public RoomRun getRoomRun() {
		return roomRun;
	}

	public Role getRole() {
		return role;
	}

	public Device getDevice() {
		return device;
	}

}
