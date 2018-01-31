package org.goaler.ballwar.server.soul;

import org.goaler.ballwar.common.entity.Hog;
import org.goaler.ballwar.common.manager.BackgroundManager;
import org.goaler.ballwar.common.manager.GameMapManager;
import org.goaler.ballwar.common.util.Calculator;
import org.goaler.ballwar.server.client.GameRun;

public class HogSoul extends CellSoul<Hog> {
	private GameRun gameRun;

	public HogSoul() {
		super(new Hog());
		// 设置id
		info.setId(CellSoul.genId());
		// 设置半径和坐标
		info.setMinR(50);
		info.setR(info.getMinR());
		setInnerX(GameMapManager.genXValue());
		setInnerY(GameMapManager.genYValue());
		// 设置背景
		info.setBackground(BackgroundManager.getRandomColor());
		// 设置密度，质量
		info.setDensity(Calculator.DEFAULT_DENSITY);
		info.setMass(Calculator.getMass(getR()));
		// 设置移动属性
		info.setMoveStep(5);
		info.setMoveTime(Calculator.getMoveTime(getR(), info.getMass()));
	}

	@Override
	public void run() {
		while (isRunning()) {
			move(gameRun.getCurSin(), gameRun.getCurCos());

			try {
				Thread.sleep(getInfo().getMoveTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean move(float sin, float cos) {
		boolean move = super.move(sin, cos);
		if (move) {
			getGameRun().getRoomRun().getAreaManager().updateEntityAreaid(this.getInfo());
		}
		return move;
	}

	public GameRun getGameRun() {
		return gameRun;
	}

	public void setGameRun(GameRun gameRun) {
		this.gameRun = gameRun;
	}

}
