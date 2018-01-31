package org.goaler.ballwar.app.view;

import org.goaler.ballwar.app.BallWarApplication;
import org.goaler.ballwar.common.msg.Msg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CombatView extends SurfaceView implements SurfaceHolder.Callback {
	private BallWarApplication app;

	private float down_x;
	private float down_y;

	public CombatView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setFocusable(true);
		setFocusableInTouchMode(true);
		setClickable(true);
		setKeepScreenOn(true);

		app = (BallWarApplication) context.getApplicationContext();
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		CombatRun combatRun = new CombatRun(context, holder);
		app.getMsgManager().registerFans(combatRun);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			down_x = event.getX();
			down_y = event.getY();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			float x = event.getX();
			float y = event.getY();
			double z = Math.sqrt((x - down_x) * (x - down_x) + (y - down_y) * (y - down_y));
			float sin = (float) ((y - down_y) / z);
			float cos = (float) ((x - down_x) / z);
			move(sin, cos);
		}
		return super.onTouchEvent(event);
	}

	private void move(float sin, float cos) {
		Msg msg = new Msg();
		msg.setCmd("move");
		msg.setParam("sin", sin);
		msg.setParam("cos", cos);
		app.getMsgManager().output(msg);
	}

}
