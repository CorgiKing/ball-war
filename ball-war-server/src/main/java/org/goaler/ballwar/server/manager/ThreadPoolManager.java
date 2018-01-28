package org.goaler.ballwar.server.manager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
	private static ThreadPoolExecutor threadPool;
	
	public static ThreadPoolExecutor getThreadPoolInstance(){
		if (threadPool == null) {
			synchronized (ThreadPoolManager.class) {
				if (threadPool == null) {
					newThreadPool();
				}
			}
		}
		return threadPool;
	}

	private static void newThreadPool() {
		int corePoolSize = 10;
		int maximumPoolSize = 50;
		int keepAliveTime = 60;
		TimeUnit unit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> workQueue;
		workQueue = new ArrayBlockingQueue<>(100);
		
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
}
