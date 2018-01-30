package org.goaler.ballwar.app.model;

import java.util.ArrayList;
import java.util.List;

import org.goaler.ballwar.common.entity.Cell;

public class ScreenData {
	private int central_x;
	private int central_y;
	private int screen_left;
	private int screen_right;
	private int screen_up;
	private int screen_down;
	private List<Cell> cells;
	
	
	public ScreenData(){
		cells = new ArrayList<Cell>();
	}
	
	public int getCentral_x() {
		return central_x;
	}
	public void setCentral_x(int central_x) {
		this.central_x = central_x;
	}
	public int getCentral_y() {
		return central_y;
	}
	public void setCentral_y(int central_y) {
		this.central_y = central_y;
	}
	public int getScreen_left() {
		return screen_left;
	}
	public void setScreen_left(int screen_left) {
		this.screen_left = screen_left;
	}
	public int getScreen_right() {
		return screen_right;
	}
	public void setScreen_right(int screen_right) {
		this.screen_right = screen_right;
	}
	public int getScreen_up() {
		return screen_up;
	}
	public void setScreen_up(int screen_up) {
		this.screen_up = screen_up;
	}
	public int getScreen_down() {
		return screen_down;
	}
	public void setScreen_down(int screen_down) {
		this.screen_down = screen_down;
	}
	public List<Cell> getCells() {
		return cells;
	}
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

}
