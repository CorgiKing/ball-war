package org.goaler.ballwar.server.soul;

import java.util.List;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.entity.Hog;
import org.goaler.ballwar.common.entity.Monad;
import org.goaler.ballwar.common.util.Calculator;
import org.goaler.ballwar.server.client.GameRun;
import org.goaler.ballwar.server.client.RoomRun;
import org.goaler.ballwar.server.manager.BackgroundManager;
import org.goaler.ballwar.server.util.ScreenshotUtil;

public class HogSoul extends CellSoul<Hog> {
	private GameRun gameRun;

	public HogSoul(RoomRun roomRun) {
		super(new Hog(), roomRun);

		// 设置半径和坐标
		getInfo().setMinR(50);
		updateR(getInfo().getMinR());
		// 设置密度
		getInfo().setDensity(Calculator.DEFAULT_DENSITY);
		// 设置背景
		getInfo().setBackground(BackgroundManager.getRandomColor());

		// 随机位置
		randomLocation();
	}

	@Override
	public void run() {
		while (isRunning()) {
			move(gameRun.getCurSin(), gameRun.getCurCos());

			eatOther();

			try {
				Thread.sleep(getInfo().getMoveTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void eatOther() {
		List<Cell> cells = ScreenshotUtil.getAroundByBorder(getInfo(), getGameRun().getRoomRun().getAreaManager());
		for (Cell cell : cells) {
			if (!cell.isDisplay() || getInfo().getId() == cell.getId()) {
				continue;
			}
			if (cell instanceof Monad) {
				// 单细胞生物
				eatMonad((Monad) cell);
			} else if (cell instanceof Hog) {
				eatHog((Hog) cell);
			}

		}
	}

	/**
	 * 吃掉单细胞生物
	 * 
	 * @param monad
	 * @return
	 */
	public boolean eatMonad(Monad monad) {
		double distance = getDistance(monad.getX(), monad.getY());
		if ((this.getR() - distance) >= 0) {// 覆盖直径的1/2,
			this.updateMass(this.getMass() + monad.getMass());// 体重增加
			MonadSoul soul = (MonadSoul) getRoomRun().getAllEntitys().get(monad.getId());
			soul.beEaten();
			return true;
		}
		return false;
	}

	/**
	 * 吃掉贪吃猪
	 * 
	 * @param hog
	 * @return
	 */
	public boolean eatHog(Hog hog) {
		double distance = getDistance(hog.getX(), hog.getY());
		if ((this.getR() - distance) >= hog.getR() / 2) {// 覆盖直径的3/4,
			this.updateMass(this.getMass() + hog.getMass());// 体重增加
			HogSoul soul = (HogSoul) getRoomRun().getAllEntitys().get(hog.getId());
			soul.beEaten();
			return true;
		}
		return false;
	}

	@Override
	public void beEaten() {
		reInit();
		gameRun.keepAlive();
	}

	@Override
	public void reInit() {
		// 初始大小
		updateR(getInfo().getMinR());
		// 关闭运行
		setRunning(false);
		// 关闭显示
		setDisplay(false);

	}

	public GameRun getGameRun() {
		return gameRun;
	}

	public void setGameRun(GameRun gameRun) {
		this.gameRun = gameRun;
	}

}
