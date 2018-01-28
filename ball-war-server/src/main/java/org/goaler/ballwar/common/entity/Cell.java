package org.goaler.ballwar.common.entity;

import java.io.Serializable;

/**
 * 细胞
 * 
 * @author goaler
 *
 */
public abstract class Cell implements Serializable {
	private static final long serialVersionUID = -7935645635464596772L;
	private int id;
	private int x;
	private int y;
	private int r;
	private int background;
	/**
	 * 质量
	 */
	private long mass;
	/**
	 * 步长
	 */
	private int v_step;
	/**
	 * 频率
	 */
	private int v_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public long getMass() {
		return mass;
	}

	public void setMass(long mass) {
		this.mass = mass;
	}

	public int getV_step() {
		return v_step;
	}

	public void setV_step(int v_step) {
		this.v_step = v_step;
	}

	public int getV_time() {
		return v_time;
	}

	public void setV_time(int v_time) {
		this.v_time = v_time;
	}

}
