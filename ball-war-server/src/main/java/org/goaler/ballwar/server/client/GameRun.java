package org.goaler.ballwar.server.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.msg.Msg;
import org.goaler.ballwar.common.msg.MsgFans;
import org.goaler.ballwar.common.msg.MsgManager;
import org.goaler.ballwar.server.manager.ThreadPoolManager;
import org.goaler.ballwar.server.soul.HogSoul;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameRun implements MsgFans, Runnable {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private Role role;
	private RoomRun roomRun;
	private MsgManager msgManager;
	private AtomicInteger hogWaiterIndex;
	private List<HogSoul> hogWaiters;
	private List<HogSoul> hogs;

	private volatile float curSin = ThreadLocalRandom.current().nextFloat();
	private volatile float curCos = ThreadLocalRandom.current().nextFloat();
	private volatile float preSin;
	private volatile float preCos;

	public GameRun(Role role, RoomRun roomRun) {
		this.role = role;
		this.roomRun = roomRun;
	}

	@Override
	public boolean handleMsg(Msg msg) {
		return false;
	}

	@Override
	public void run() {
		
	}

	public boolean start() {
		hogWaiters = roomRun.fetchHog(RoomRun.HOG_SIZE_PER_ROLE);
		if (hogWaiters == null) {
			log.error("从RoomRun中没有取到Hog! role-{}", role.getName());
			return false;
		}
		hogWaiterIndex = new AtomicInteger();

		hogs = new ArrayList<>();
		HogSoul hog = addHog();
		hog.actUp();
		
		ThreadPoolManager.getThreadPoolInstance().execute(this);
		return true;
	}

	public HogSoul addHog() {
		HogSoul hog = fetchHogFromWaiters();
		if (hog != null) {
			hogs.add(hog);
			return hog;
		}
		return null;
	}

	private HogSoul fetchHogFromWaiters() {
		int index = hogWaiterIndex.incrementAndGet();
		if (index < hogWaiters.size()) {
			return hogWaiters.get(index);
		}
		return null;
	}

	public MsgManager getMsgManager() {
		return msgManager;
	}

	public void setMsgManager(MsgManager msgManager) {
		this.msgManager = msgManager;
	}
}
