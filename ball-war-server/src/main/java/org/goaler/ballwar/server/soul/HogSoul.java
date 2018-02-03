package org.goaler.ballwar.server.soul;

import java.util.List;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.entity.Hog;
import org.goaler.ballwar.common.entity.Monad;
import org.goaler.ballwar.common.entity.Vole;
import org.goaler.ballwar.common.util.Calculator;
import org.goaler.ballwar.server.client.GameRun;
import org.goaler.ballwar.server.client.RoomRun;
import org.goaler.ballwar.server.manager.BackgroundManager;
import org.goaler.ballwar.server.manager.ThreadPoolManager;
import org.goaler.ballwar.server.util.ScreenshotUtil;

public class HogSoul extends CellSoul<Hog> implements Runnable {
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
			} else if (cell instanceof Vole) {
				eatVole((Vole) cell);
			}

		}
	}

	/**
	 * 吃掉田鼠
	 * @param vole
	 * @return
	 */
	private boolean eatVole(Vole vole) {
		double distance = getDistance(vole.getX(), vole.getY());
		if ((this.getR() - distance) >= 0) {// 覆盖直径的1/2,
			this.updateMass(this.getMass() + vole.getMass());// 体重增加
			VoleSoul soul = (VoleSoul) getRoomRun().getAllEntitys().remove(vole.getId());
			soul.beEaten();
			getRoomRun().getAreaManager().remove(soul.getInfo());
			return true;
		}
		return false;
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
			this.updateMass(this.getMass() + monad.getMass() * 5);// 体重增加
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

	/**
	 * 分裂
	 */
	public HogSoul split() {
		if (!gameRun.isFull() && this.getMass() > Calculator.getMass(getInfo().getMinR()) * 2) {
			// 获取安静的hog
			HogSoul hog = gameRun.findQuietHog();
			if (hog == null) {
				return null;
			}
			// 给予一般
			updateMass(getMass() / 2);
			hog.updateMass(getMass());
			// 设置新role位置
			int len = 2 * this.getR();
			float sin = gameRun.getCurSin();
			float cos = gameRun.getCurCos();
			if (sin == 0 && cos == 0) {
				sin = gameRun.getPreSin();
				cos = gameRun.getPreCos();
			}
			int x = this.getX() + Math.round(len * cos);
			int y = this.getY() + Math.round(len * sin);
			hog.setInnerX(x);
			hog.setInnerY(y);

			return hog;
		}
		return null;
	}

	/**
	 * 吐
	 */
	public void vomit() {
		int min_mass = Calculator.getMass(getInfo().getMinR());
		if (this.getMass() > min_mass * 2) {// 每次吐的小球体重为500
			// 自身体重减少
			this.updateMass(this.getMass() - min_mass);
			// 新建吐出的vole
			VoleSoul vole = getRoomRun().createVole();
			vole.getInfo().setBackground(getInfo().getBackground());
			vole.updateMass(min_mass);
			// 设置新vole位置
			int len = 3 * this.getR();
			float sin = gameRun.getCurSin();
			float cos = gameRun.getCurCos();
			if (sin == 0 && cos == 0) {
				sin = gameRun.getPreSin();
				cos = gameRun.getPreCos();
			}
			int x = this.getX() + Math.round(len * cos);
			int y = this.getY() + Math.round(len * sin);
			vole.setInnerX(x);
			vole.setInnerY(y);
			vole.setDisplay(true);
			getRoomRun().getAreaManager().addEntity(vole.getInfo());
		}
	}

	/**
	 * 启动运行，并显示
	 */
	public void actUp() {
		ThreadPoolManager.getThreadPoolInstance().execute(this);
		setDisplay(true);
	}

	public GameRun getGameRun() {
		return gameRun;
	}

	public void setGameRun(GameRun gameRun) {
		this.gameRun = gameRun;
	}

}
