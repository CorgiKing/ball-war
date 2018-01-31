package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicBoolean;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.server.manager.ThreadPoolManager;

public abstract class CellSoul<T extends Cell> implements Runnable {
	private T info;
	private AtomicBoolean running = new AtomicBoolean();

	public CellSoul(T info) {
		this.info = info;
	}
	
	public boolean move( float sin,float cos) {
      if ((sin != 0 || cos != 0) && info.getMoveStep() != 0) {

          int x, y;
          x = this.getX() + Math.round(info.getMoveStep() * cos);
          y = this.getY() + Math.round(info.getMoveStep() * sin);
          //设置坐标
          info.setInnerX(x);
          info.setInnerY(y);
          return true;
      }
      return false;
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

}
