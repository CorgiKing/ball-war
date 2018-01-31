package org.goaler.ballwar.server.soul;

import org.goaler.ballwar.common.entity.Hog;
import org.goaler.ballwar.server.client.GameRun;

public class HogSoul extends CellSoul<Hog> {
    private GameRun gameRun;

	public HogSoul(Hog info) {
		super(info);
	}

	@Override
	public void run() {
		while(isRunning()){
		  move(gameRun.getCurSin(), gameRun.getCurCos());
		  
		  try {
            Thread.sleep(getInfo().getMoveTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		}
	}

  public GameRun getGameRun() {
    return gameRun;
  }

  public void setGameRun(GameRun gameRun) {
    this.gameRun = gameRun;
  }
	
}
