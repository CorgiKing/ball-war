package org.goaler.ballwar.server.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.model.Device;
import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.msg.Msg;
import org.goaler.ballwar.common.msg.MsgFans;
import org.goaler.ballwar.common.msg.MsgManager;
import org.goaler.ballwar.server.manager.ThreadPoolManager;
import org.goaler.ballwar.server.soul.HogSoul;
import org.goaler.ballwar.server.util.ScreenshotUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameRun implements MsgFans, Runnable {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private Role role;
	private RoomRun roomRun;
	private Device device;
	private MsgManager msgManager;
	private boolean running;

	private List<HogSoul> hogs;

	private volatile float curSin = ThreadLocalRandom.current().nextFloat();
	private volatile float curCos = ThreadLocalRandom.current().nextFloat();
	private volatile float preSin;
	private volatile float preCos;

	public GameRun() {
	}

	@Override
	public boolean handleMsg(Msg msg) {
		return false;
	}

	@Override
	public void run() {
		ScreenshotUtil<Cell> screenshotUtil = new ScreenshotUtil<>();
		Msg msg = new Msg();
		msg.setCmd("show");
		while (running) {
			List<Cell> cs = new ArrayList<>();
			for (HogSoul hogSoul : hogs) {
				if (hogSoul.isRunning() && hogSoul.isDisplay()) {
					cs.add(hogSoul.getInfo());
				}
			}
			List<Cell> sendCs = screenshotUtil.screenshot(cs, roomRun.getAreaManager(), device.getWidth(),
					device.getHeight());
			

			msg.setParam("cs", sendCs);
			msg.setParam("central_x", screenshotUtil.central_x);
			msg.setParam("central_y", screenshotUtil.central_y);
			msg.setParam("screen_left", screenshotUtil.screen_left);
			msg.setParam("screen_right", screenshotUtil.screen_right);
			msg.setParam("screen_up", screenshotUtil.screen_up);
			msg.setParam("screen_down", screenshotUtil.screen_down);
			msgManager.output(msg);
			System.out.println(sendCs.size());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean start() {
		// 从房间中获取hogs
		hogs = roomRun.fetchHog(RoomRun.HOG_SIZE_PER_ROLE);
		if (hogs == null) {
			log.error("从RoomRun中没有取到Hog! role-{}", role.getName());
			return false;
		}

		// 初始化并且唤醒一个hog
		HogSoul hog = awakenHog();
		if (hog == null) {
			log.error("唤醒第一个hog失败！hogs-{}", hogs);
			return false;
		}
		hog.actUp();

		// 启动GameRun
		running = true;
		ThreadPoolManager.getThreadPoolInstance().execute(this);
		return true;
	}

	public HogSoul awakenHog() {

		HogSoul hog = null;
		for (int i = 0; i < hogs.size(); ++i) {
			boolean flag = hogs.get(i).compareAndSetRunning(false, true);
			if (flag) {
				hog = hogs.get(i);
			}
		}
		return hog;
	}

	public MsgManager getMsgManager() {
		return msgManager;
	}

	public void setMsgManager(MsgManager msgManager) {
		this.msgManager = msgManager;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoomRun getRoomRun() {
		return roomRun;
	}

	public void setRoomRun(RoomRun roomRun) {
		this.roomRun = roomRun;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
