package org.corgiking.io;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class AdderTest {

	public static void main(String[] args) {
		AtomicLong al = new AtomicLong();
		System.out.println(al.getAndIncrement());
		
//		for(int i=0;i<10000;++i){
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					while(true){
//						long v = al.incrementAndGet();
//						System.out.println(v);
//					}
//				}
//			}).start();
//		}
	}

}
