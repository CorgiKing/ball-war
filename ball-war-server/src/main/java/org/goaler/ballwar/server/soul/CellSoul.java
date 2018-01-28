package org.goaler.ballwar.server.soul;

import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;

public abstract class CellSoul<T extends Cell>{
	private static final AtomicInteger baseId = new AtomicInteger();
	private T info;

	public CellSoul(T info) {
		this.info = info;
	}

	public static int genId() {
		return baseId.getAndIncrement();
	}

	public int getId(){
		return info.getId();
	}
	
	public T getCell() {
		return info;
	}
}
