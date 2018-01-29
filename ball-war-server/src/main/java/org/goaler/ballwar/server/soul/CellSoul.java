package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.server.manager.ThreadPoolManager;

public abstract class CellSoul<T extends Cell> implements Runnable {
	private static final AtomicInteger baseId = new AtomicInteger();
	private T info;
	private boolean display;
	private AtomicBoolean running = new AtomicBoolean();

	public CellSoul(T info) {
		this.info = info;
	}

	public static int genId() {
		return baseId.getAndIncrement();
	}

	public void actUp() {
		ThreadPoolManager.getThreadPoolInstance().execute(this);
		setDisplay(true);
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

	public boolean isRunning() {
		return running.get();
	}

	public boolean compareAndSetRunning(boolean expect, boolean update) {
		return this.running.compareAndSet(expect, update);
	}

	public int getX() {
		return info.getX();
	}

	public int getY() {
		return info.getY();
	}

	public int getR() {
		return info.getR();
	}

}
