package org.goaler.ballwar.common.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BackgroundManager {
	public static final int COLOR_NUM = 10;
	private List<Integer> backColors;
	
	public BackgroundManager() {
		backColors = new LinkedList<Integer>();
		for(int i = 0; i < COLOR_NUM; i++){
			backColors.add(i);
		}
	}
	public int getBackColor(){
		return backColors.remove(ThreadLocalRandom.current().nextInt(backColors.size()));
	}
	
	public static int getRandomColor(){
		return new Random().nextInt(COLOR_NUM);
	}
}
