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
	private int minR;

	private int id;
	private int x;
	private int y;
	private int r;
	private int background;
	/**
	 * 密度
	 */
	private int density;
	/**
	 * 质量
	 */
	private int mass;
	/**
	 * 步长
	 */
	private int moveStep;
	/**
	 * 频率
	 */
	private int moveTime;
	private float sin;
	private float cos;

	/**
	 * 区域id
	 */
	private String areaId;
	private boolean display;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
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

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public int getMoveStep() {
		return moveStep;
	}

	public void setMoveStep(int moveStep) {
		this.moveStep = moveStep;
	}

	public int getMoveTime() {
		return moveTime;
	}

	public void setMoveTime(int moveTime) {
		this.moveTime = moveTime;
	}

	public int getDensity() {
		return density;
	}

	public void setDensity(int density) {
		this.density = density;
	}

	public int getMinR() {
		return minR;
	}

	public void setMinR(int minR) {
		this.minR = minR;
	}

	public float getSin() {
		return sin;
	}

	public void setSin(float sin) {
		this.sin = sin;
	}

	public float getCos() {
		return cos;
	}

	public void setCos(float cos) {
		this.cos = cos;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	@Override
	public String toString() {
		return "Cell [minR=" + minR + ", id=" + id + ", x=" + x + ", y=" + y + ", r=" + r + ", background=" + background
				+ ", density=" + density + ", mass=" + mass + ", moveStep=" + moveStep + ", moveTime=" + moveTime
				+ ", sin=" + sin + ", cos=" + cos + ", areaId=" + areaId + ", display=" + display + "]";
	}

}
