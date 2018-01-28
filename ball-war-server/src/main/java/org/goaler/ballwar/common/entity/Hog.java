package org.goaler.ballwar.common.entity;

import org.goaler.ballwar.server.manager.BackgroundManager;
import org.goaler.ballwar.server.manager.GameMapManager;
import org.goaler.ballwar.server.soul.CellSoul;
import org.goaler.ballwar.server.util.Calculator;

/**
 * 爱吃的猪
 * 
 * @author Goaler
 *
 */
public class Hog extends Cell {
	private static final long serialVersionUID = 8086725711031342777L;

	public Hog() {
		// 设置id
		setId(CellSoul.genId());
		// 设置半径和坐标
		setMinR(50);
		setR(getMinR());
		setInnerX(GameMapManager.genXValue());
		setInnerY(GameMapManager.genYValue());
		// 设置背景
		setBackground(BackgroundManager.getRandomColor());
		// 设置密度，质量
		setDensity(Calculator.DEFAULT_DENSITY);
		setMass(Calculator.getMass(getR()));
		// 设置移动属性
		setMoveStep(5);
		setMoveTime(Calculator.getMoveTime(getR(), getMass()));
	}

}
