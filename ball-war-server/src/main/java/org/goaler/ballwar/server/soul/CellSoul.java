package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.util.Calculator;
import org.goaler.ballwar.server.client.RoomRun;
import org.goaler.ballwar.server.manager.GameMapManager;
import org.goaler.ballwar.server.manager.ThreadPoolManager;

public abstract class CellSoul<T extends Cell> implements Runnable {
	private T info;
	private AtomicBoolean running = new AtomicBoolean();
	private static final AtomicInteger baseId = new AtomicInteger();
	private RoomRun roomRun;

	public CellSoul(T info, RoomRun roomRun) {
		this.info = info;
		this.roomRun = roomRun;

		// 设置id
		info.setId(CellSoul.genId());
		// 设置移动步长
		info.setMoveStep(5);
	}

	public boolean move(float sin, float cos) {
		if ((sin != 0 || cos != 0) && info.getMoveStep() != 0) {

			int x, y;
			x = this.getX() + Math.round(info.getMoveStep() * cos);
			y = this.getY() + Math.round(info.getMoveStep() * sin);
			// 设置坐标
			setInnerX(x);
			setInnerY(y);
			roomRun.getAreaManager().updateEntityAreaid(this.getInfo());
			return true;
		}
		return false;
	}

	public static int genId() {
		return baseId.getAndIncrement();
	}

	/**
	 * 启动运行，并显示
	 */
	public void actUp() {
		ThreadPoolManager.getThreadPoolInstance().execute(this);
		setDisplay(true);
	}

	public void randomLocation() {
		setInnerX(GameMapManager.genXValue());
		setInnerY(GameMapManager.genYValue());
		roomRun.getAreaManager().updateEntityAreaid(this.getInfo());
	}

	public double getDistance(int x, int y) {
		return Math.sqrt((this.getX() - x) * (this.getX() - x) + (this.getY() - y) * (this.getY() - y));
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
	
	public void setRunning(boolean b){
		this.running.set(b);
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

	public int getMass() {
		return info.getMass();
	}

	public void updateR(int r) {
		info.setR(r);
		// 该半径对应质量，速度
		info.setMass(Calculator.getMass(r));
		info.setMoveTime(Calculator.getMoveTime(r, getMass()));
	}

	public void setMoveTime(int t) {
		info.setMoveTime(t);
	}

	public void updateMass(int mass) {
		info.setMass(mass);
		// 该质量对应半径，速度
		info.setR(Calculator.getRadius(mass));
		info.setMoveTime(Calculator.getMoveTime(getR(), mass));
	}

	public void beEaten() {
	}
	
	public void reInit(){
		
	}

	public RoomRun getRoomRun() {
		return roomRun;
	}

}
