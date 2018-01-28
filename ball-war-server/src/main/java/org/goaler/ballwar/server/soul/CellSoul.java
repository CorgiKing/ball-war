package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.server.manager.ThreadPoolManager;

public abstract class CellSoul<T extends Cell> implements Runnable {
	private static final AtomicInteger baseId = new AtomicInteger();
	private T info;
	private boolean display;
	private String areaId;

	public CellSoul(T info) {
		this.info = info;
	}

	public static int genId() {
		return baseId.getAndIncrement();
	}

	public void actUp() {
		ThreadPoolManager.getThreadPoolInstance().execute(this);
	}

	public int getId() {
		return info.getId();
	}

	public T getInfo() {
		return info;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public int getX() {
		return info.getX();
	}

	public int getY() {
		return info.getY();
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
}
