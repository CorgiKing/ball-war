package org.goaler.ballwar.server.soul;

import org.goaler.ballwar.common.entity.Monad;
import org.goaler.ballwar.common.util.Calculator;
import org.goaler.ballwar.server.client.RoomRun;
import org.goaler.ballwar.server.manager.BackgroundManager;
import org.goaler.ballwar.server.manager.GameMapManager;

public class MonadSoul extends CellSoul<Monad> {

	public MonadSoul(RoomRun roomRun) {
		super(new Monad(), roomRun);
		// 设置半径和坐标
		getInfo().setMinR(15);
		updateR(getInfo().getMinR());
		// 设置背景
		getInfo().setBackground(BackgroundManager.getRandomColor());
		// 设置密度，质量
		getInfo().setDensity(Calculator.DEFAULT_DENSITY);

		// 随机位置
		randomLocation();
	}

	@Override
	public void run() {

	}

	@Override
	public void beEaten() {
		setInnerX(GameMapManager.genXValue());
		setInnerY(GameMapManager.genYValue());
		getRoomRun().getAreaManager().updateEntityAreaid(getInfo());
	}

}
