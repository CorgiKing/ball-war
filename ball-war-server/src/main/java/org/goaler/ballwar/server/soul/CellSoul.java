package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;

public abstract class CellSoul {
	private static final AtomicInteger baseId = new AtomicInteger();
	private Cell cell;

	public CellSoul(Cell cell) {
		this.cell = cell;
	}

	public int genId() {
		return baseId.getAndIncrement();
	}

	public int getId(){
		return cell.getId();
	}
	
	public Cell getCell() {
		return cell;
	}
}
