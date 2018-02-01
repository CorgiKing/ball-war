package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.server.manager.GameMapManager;
import org.goaler.ballwar.server.manager.ThreadPoolManager;

public abstract class CellSoul<T extends Cell> implements Runnable {
	protected T info;
	private AtomicBoolean running = new AtomicBoolean();
	private static final AtomicInteger baseId = new AtomicInteger();

	public CellSoul(T info) {
		this.info = info;
	}

	public boolean move(float sin, float cos) {
		if ((sin != 0 || cos != 0) && info.getMoveStep() != 0) {

			int x, y;
			x = this.getX() + Math.round(info.getMoveStep() * cos);
			y = this.getY() + Math.round(info.getMoveStep() * sin);
			// 设置坐标
			setInnerX(x);
			setInnerY(y);
			return true;
		}
		return false;
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
		return info.isDisplay();
	}

	public void setDisplay(boolean display) {
		info.setDisplay(display);
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

	public void setInnerX(int xx) {
		if (xx - getR() < GameMapManager.MIN_X) {
			info.setX(GameMapManager.MIN_X + getR());
		} else if (xx + getR() > GameMapManager.MAX_X) {
			info.setX(GameMapManager.MAX_X - getR());
		} else {
			info.setX(xx);
		}
	}

	public void setInnerY(int yy) {
		if (yy - getR() < GameMapManager.MIN_Y) {
			info.setY(GameMapManager.MIN_Y + getR());
		} else if (yy + getR() > GameMapManager.MAX_Y) {
			info.setY(GameMapManager.MAX_Y - getR());
		} else {
			info.setY(yy);
		}
	}
}
