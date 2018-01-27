package org.goaler.ballwar.server.client;

import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.model.Device;
import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.common.msg.Msg;
import org.goaler.ballwar.common.msg.MsgFans;
import org.goaler.ballwar.util.ThreadPoolService;
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
		ThreadPoolService.getThreadPoolInstance().execute(getMsgManager());
		getMsgManager().registerFans(this);
	}

	@Override
	public void clientRun() {

	}

	@Override
	public boolean handleMsg(Msg msg) {
		return msgDispatch(msg);
	}

	/**
	 * 分发信息
	 * 
	 * @param msg
	 * @return
	 */
	private boolean msgDispatch(Msg msg) {
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
		log.info("role-{},device-{}", role, device);
	}

	/**
	 * 新建游戏房间
	 * 
	 * @param msg
	 */
	private void newGameRoom(Msg msg) {
		RoomInfo room = msg.getParam("room", RoomInfo.class);
		room.setOwner(role);
		log.info("room-{}", room);
		roomRun = new RoomRun(room);
	}

	/**
	 * 开始游戏
	 * 
	 * @param msg
	 */
	private void startGame(Msg msg) {

	}

}
