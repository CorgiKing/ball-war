package org.goaler.ballwar.app.util;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast;

	/**
	 * 显示toast
	 * 
	 * @param ctx
	 * @param msg
	 */
	public static void showToast(final Activity ctx, final String msg, final int duration) {
		// 判断是在子线程，还是主线程
		if ("main".equals(Thread.currentThread().getName())) {
			showCurrentToast(ctx, msg, duration);
		} else {
			// 子线程
			ctx.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showCurrentToast(ctx, msg, duration);
				}
			});
		}

	}

	public static void showToast(final Activity ctx, final String msg) {
		// 判断是在子线程，还是主线程
		if ("main".equals(Thread.currentThread().getName())) {
			showCurrentToast(ctx, msg, Toast.LENGTH_SHORT);
		} else {
			// 子线程
			ctx.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showCurrentToast(ctx, msg, Toast.LENGTH_SHORT);
				}
			});
		}

	}

	private static void showCurrentToast(final Activity ctx, final String msg, final int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(ctx, msg, duration);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}
}
