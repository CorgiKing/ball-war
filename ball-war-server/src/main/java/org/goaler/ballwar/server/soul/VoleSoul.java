package org.goaler.ballwar.server.soul;

import org.goaler.ballwar.common.entity.Vole;
import org.goaler.ballwar.server.client.RoomRun;

public class VoleSoul extends CellSoul<Vole> {

	public VoleSoul( RoomRun roomRun) {
		super(new Vole(), roomRun);
	}

	@Override
	public void beEaten() {
		setDisplay(false);
	}
	
}
