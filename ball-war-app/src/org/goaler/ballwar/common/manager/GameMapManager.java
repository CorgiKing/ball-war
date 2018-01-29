package org.goaler.ballwar.common.manager;

import java.util.concurrent.ThreadLocalRandom;

public class GameMapManager {
	public static final int WIDTH = 30000;
	public static final int HEIGHT = 30000;
	public static final int BACKGROUND = 0;// 无背景
	public static final int MIN_X = 0;
	public static final int MAX_X = WIDTH;
	public static final int MIN_Y = 0;
	public static final int MAX_Y = HEIGHT;

	public static int genXValue() {
		return genInt(WIDTH);
	}

	public static int genYValue() {
		return genInt(HEIGHT);
	}

	public static int genInt(int bound) {
		return ThreadLocalRandom.current().nextInt(bound);
	}
}
