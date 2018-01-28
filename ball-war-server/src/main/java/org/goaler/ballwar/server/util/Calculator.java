package org.goaler.ballwar.server.util;

public class Calculator {
	public static final float PI = 3.14f;
	public static final int DEFAULT_DENSITY = 1;

	public static int getRadius(int mass) {
		return getRadius(mass, 1);
	}

	public static int getMass(int radius) {
		return getMass(radius, 1);
	}

	public static int getRadius(int mass, int density) {
		return (int) Math.sqrt(mass / density / PI);
	}

	public static int getMass(int radius, int density) {
		return (int) PI * radius * radius * density;
	}

	public static int getMoveTime(int radius, int mass) {
		return radius / 10 + 5;
	}
}
