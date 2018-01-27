package org.corgiking.io;

import org.goaler.ballwar.common.discover.find.Find;
import org.goaler.ballwar.common.discover.find.MulticastFind;
import org.goaler.ballwar.common.discover.notice.MulticastNotice;

public class MulticastDiscoverTest {

	public static void main(String[] args) throws InterruptedException {
		final MulticastNotice notice = new MulticastNotice();
		new Thread(new Runnable() {
			@Override
			public void run() {
				notice.listenFind();

				System.out.println("notice stop!");
			}
		}).start();

		Thread.sleep(2000);

		final MulticastFind find = new MulticastFind();
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
