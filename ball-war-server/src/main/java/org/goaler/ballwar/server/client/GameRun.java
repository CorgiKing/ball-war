package org.goaler.ballwar.server.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.io.bio.BioUdpSerialDataTransfer;
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
	private DataTransfer udpDataTransfer;
	private boolean running;

	private AtomicInteger aliveHogNum = new AtomicInteger();
	private List<HogSoul> hogs;

	private volatile float curSin = ThreadLocalRandom.current().nextFloat();
	private volatile float curCos = ThreadLocalRandom.current().nextFloat();
	private volatile float preSin;
	private volatile float preCos;

	@Override
	public boolean handleMsg(Msg msg) {
		switch (msg.getCmd()) {
		case "move":
			handleMoveMsg(msg);
			break;
		case "split":
			handleSplitMsg(msg);
			break;
		case "vomit":
			handleVomitMsg(msg);
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * 吐
	 * @param msg
	 */
	private void handleVomitMsg(Msg msg) {
		for(HogSoul hog:hogs){
			if (hog.isRunning() && hog.isDisplay()) {
				hog.vomit();
			}
		}
	}

	/**
	 * 分裂
	 * 
	 * @param msg
	 */
	private void handleSplitMsg(Msg msg) {
		List<HogSoul> hs = new ArrayList<>();
		for (HogSoul hog : hogs) {
			if (hog.isRunning() && hog.isDisplay()) {
				HogSoul splitHog = hog.split();
				if (splitHog != null) {					
					hs.add(splitHog);
				}
			}
		}
		//统一唤醒
		for (HogSoul hog : hs) {
			hog.actUp();
		}
	}

	/**
	 * 移动
	 * 
	 * @param msg
	 */
	private void handleMoveMsg(Msg msg) {
		preSin = curSin;
		preCos = curCos;
		curSin = msg.getParam("sin", Float.class);
		curCos = msg.getParam("cos", Float.class);
	}

	@Override
	public void run() {
		ScreenshotUtil<Cell> screenshotUtil = new ScreenshotUtil<>();
		while (running) {
			// 获取活跃状态的hog
			List<Cell> cs = new ArrayList<>();
			for (HogSoul hogSoul : hogs) {
				if (hogSoul.isRunning() && hogSoul.isDisplay()) {
					cs.add(hogSoul.getInfo());
				}
			}
			List<Cell> sendCs = screenshotUtil.screenshot(cs, roomRun.getAreaManager(), device.getWidth(),
					device.getHeight());

			Msg msg = new Msg();
			msg.setCmd("show");
			msg.setParam("cs", sendCs);
			msg.setParam("central_x", screenshotUtil.central_x);
			msg.setParam("central_y", screenshotUtil.central_y);
			msg.setParam("screen_left", screenshotUtil.screen_left);
			msg.setParam("screen_right", screenshotUtil.screen_right);
			msg.setParam("screen_up", screenshotUtil.screen_up);
			msg.setParam("screen_down", screenshotUtil.screen_down);
			// msgManager.output(msg);
			udpDataTransfer.output(msg);
//			log.info("发送个数：{}",sendCs.size());
			try {
				Thread.sleep(25);
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
		// 绑定到GameRun
		for (HogSoul hog : hogs) {
			hog.setGameRun(this);
		}

		// 初始化并且唤醒一个hog
		awakenHog();

		// 启动GameRun
		running = true;
		ThreadPoolManager.getThreadPoolInstance().execute(this);
		return true;
	}

	/**
	 * 唤醒一个安静的hog
	 */
	public void awakenHog() {
		HogSoul hog = findQuietHog();
		if (hog != null) {
			hog.actUp();
		}
	}

	/**
	 * 查找未running的hog
	 * 
	 * @return
	 */
	public HogSoul findQuietHog() {

		HogSoul hog = null;
		for (int i = 0; i < hogs.size(); ++i) {
			boolean flag = hogs.get(i).compareAndSetRunning(false, true);
			if (flag) {
				hog = hogs.get(i);
				aliveHogNum.incrementAndGet();
				break;
			}
		}
		return hog;
	}

	/**
	 * 保证至少有一个hog处于活跃状态
	 */
	public void keepAlive() {
		boolean alive = false;
		for (int i = 0; i < hogs.size(); ++i) {
			if (hogs.get(i).isRunning()) {
				alive = true;
				break;
			}
		}

		if (!alive) {
			HogSoul hog = findQuietHog();
			hog.randomLocation();
			hog.actUp();
		}

	}

	public boolean isFull() {
		return aliveHogNum.intValue() >= hogs.size();
	}

	public MsgManager getMsgManager() {
		return msgManager;
	}

	public void setMsgManager(MsgManager msgManager) {
		this.msgManager = msgManager;
		udpDataTransfer = new BioUdpSerialDataTransfer(msgManager.getIp(), BioUdpSerialDataTransfer.DEFAULT_PORT);
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

	public float getCurSin() {
		return curSin;
	}

	public void setCurSin(float curSin) {
		this.curSin = curSin;
	}

	public float getCurCos() {
		return curCos;
	}

	public void setCurCos(float curCos) {
		this.curCos = curCos;
	}

	public float getPreSin() {
		return preSin;
	}

	public void setPreSin(float preSin) {
		this.preSin = preSin;
	}

	public float getPreCos() {
		return preCos;
	}

	public void setPreCos(float preCos) {
		this.preCos = preCos;
	}

}
