package org.goaler.ballwar.common.entity;

import org.goaler.ballwar.common.manager.BackgroundManager;
import org.goaler.ballwar.common.manager.GameMapManager;
import org.goaler.ballwar.common.util.Calculator;

/**
 * 单细胞生物,游戏中最小个体
 * 
 * @author goaler
 *
 */
public class Monad extends Cell {
	private static final long serialVersionUID = -4126615244835418788L;

	public Monad() {
		// 设置id
		setId(Cell.genId());
		// 设置半径和坐标
		setMinR(15);
		setR(getMinR());
		setInnerX(GameMapManager.genXValue());
		setInnerY(GameMapManager.genYValue());
		// 设置背景
		setBackground(BackgroundManager.getRandomColor());
		// 设置密度，质量
		setDensity(Calculator.DEFAULT_DENSITY);
		setMass(Calculator.getMass(getR()));
	}
}
