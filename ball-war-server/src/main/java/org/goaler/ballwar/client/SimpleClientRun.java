package org.goaler.ballwar.client;

import org.goaler.ballwar.io.DataTransfer;
import org.goaler.ballwar.model.Device;
import org.goaler.ballwar.model.GameRoom;
import org.goaler.ballwar.model.Role;
import org.goaler.ballwar.msg.Msg;
import org.goaler.ballwar.msg.MsgFans;
import org.goaler.ballwar.util.ThreadPoolService;

public class SimpleClientRun extends ClientRunnable implements MsgFans{
	private GameRoom room;
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
		room = new GameRoom();
		room.setTitle((String) msg.getParams().get("title"));
		room.setDescription((String) msg.getParams().get("description"));
		room.setPwd((String) msg.getParams().get("pwd"));
		room.setOwner(role);
	}
	
	/**
	 * 开始游戏
	 * @param msg
	 */
	private void startGame(Msg msg) {
	}

    public GameRoom getRoom() {
      return room;
    }
  
    public void setRoom(GameRoom room) {
      this.room = room;
    }
  
    public Role getRole() {
      return role;
    }
  
    public void setRole(Role role) {
      this.role = role;
    }
  
    public Device getDevice() {
      return device;
    }
  
    public void setDevice(Device device) {
      this.device = device;
    }

}
