package org.goaler.ballwar.server.soul;

import org.goaler.ballwar.common.entity.Monad;
import org.goaler.ballwar.common.manager.BackgroundManager;
import org.goaler.ballwar.common.manager.GameMapManager;
import org.goaler.ballwar.common.util.Calculator;

public class MonadSoul extends CellSoul<Monad> {

	public MonadSoul() {
		super(new Monad());
		// 设置id
		info.setId(CellSoul.genId());
		// 设置半径和坐标
		info.setMinR(15);
		info.setR(info.getMinR());
		setInnerX(GameMapManager.genXValue());
		setInnerY(GameMapManager.genYValue());
		// 设置背景
		info.setBackground(BackgroundManager.getRandomColor());
		// 设置密度，质量
		info.setDensity(Calculator.DEFAULT_DENSITY);
		info.setMass(Calculator.getMass(getR()));
	}

	@Override
	public void run() {

	}

}
