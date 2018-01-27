package org.corgiking.io;

import org.goaler.ballwar.common.discover.find.BroadcastFind;
import org.goaler.ballwar.common.discover.find.Find;
import org.goaler.ballwar.common.discover.notice.BroadcastNotice;

public class BroadcastDiscoverTest {

	public static void main(String[] args) throws InterruptedException {
		final BroadcastNotice notice = new BroadcastNotice();
		new Thread(new Runnable() {
			@Override
			public void run() {
				notice.listenFind();

				System.out.println("notice stop!");
			}
		}).start();

		Thread.sleep(2000);

		final BroadcastFind find = new BroadcastFind();
		new Thread(new Runnable() {
			@Override
			public void run() {

				find.setRespondListener(new Find.RespondListener() {

					@Override
					public void oper(String serverIp) {
						System.out.println(serverIp + "##############");
					}
				});
			}
		}).start();

		Thread.sleep(2000);

		notice.close();
//		find.close();
	}

}
