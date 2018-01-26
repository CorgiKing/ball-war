package org.goaler.ballwar.client;

import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.model.Device;
import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.common.msg.Msg;
import org.goaler.ballwar.common.msg.MsgFans;
import org.goaler.ballwar.util.ThreadPoolService;

public class SimpleClientRun extends ClientRunnable implements MsgFans{
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
	 * @param msg
	 */
	private void initInfo(Msg msg) {
	    role = (Role) msg.getParams().get("role");
	    device = (Device) msg.getParams().get("device");
    }

    /**
	 * 新建游戏房间
	 * @param msg
	 */
	private void newGameRoom(Msg msg) {
		RoomInfo room = new RoomInfo();
		room.setTitle((String) msg.getParams().get("title"));
		room.setDescription((String) msg.getParams().get("description"));
		room.setPwd((String) msg.getParams().get("pwd"));
		room.setOwner(role);
		roomRun = new RoomRun(room);
	}
	
	/**
	 * 开始游戏
	 * @param msg
	 */
	private void startGame(Msg msg) {
		
	}


}
