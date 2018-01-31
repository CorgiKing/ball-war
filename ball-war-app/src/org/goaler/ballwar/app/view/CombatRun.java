package org.goaler.ballwar.app.view;

import java.util.List;

import org.goaler.ballwar.app.R;
import org.goaler.ballwar.app.model.ScreenData;
import org.goaler.ballwar.app.util.ConvertCoordinateUtil;
import org.goaler.ballwar.app.util.GLog;
import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.msg.Msg;
import org.goaler.ballwar.common.msg.MsgFans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class CombatRun implements Runnable, MsgFans {
	private Context context;
	private SurfaceHolder holder;
	private boolean drawing;
	private ScreenData screenData;
	private Paint paint;

	public CombatRun(Context context, SurfaceHolder holder) {
		this.holder = holder;
		this.context = context;
		drawing = true;
		screenData = new ScreenData();
		paint = new Paint();
	}

	@Override
	public void run() {
		while (drawing) {
			draw();
		}
	}

	@Override
	public boolean handleMsg(Msg msg) {
		switch (msg.getCmd()) {
		case "show":
			updateScreenData(msg);
			draw();
			break;

		default:
			break;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void updateScreenData(Msg msg) {

		screenData.setCentral_x(msg.getParam("central_x", Integer.class));
		screenData.setCentral_y(msg.getParam("central_y", Integer.class));
		screenData.setScreen_left(msg.getParam("screen_left", Integer.class));
		screenData.setScreen_right(msg.getParam("screen_right", Integer.class));
		screenData.setScreen_up(msg.getParam("screen_up", Integer.class));
		screenData.setScreen_down(msg.getParam("screen_down", Integer.class));

		screenData.setCells(msg.getParam("cs", List.class));

	}

	@SuppressWarnings("deprecation")
	private void draw() {
		Canvas canvas = holder.lockCanvas();
		if (canvas == null) {
			GLog.error("goaler", "没有拿到Canvas!!!");
			return;
		}

		canvas.drawColor(context.getResources().getColor(R.color.map_backcolor));

		ConvertCoordinateUtil translatePoint = new ConvertCoordinateUtil();
		translatePoint.Init(canvas.getWidth(), canvas.getHeight());// 初始化尺寸
		translatePoint.centralPoint(screenData.getCentral_x(), screenData.getCentral_y());// 中心点

		// 缩放比例
		float scale = getScale(canvas, translatePoint);
		translatePoint.setScale(scale);

		// 画边界
		drawBorder(canvas, translatePoint);

		// 画出图形
		// System.out.println("drawing");
		List<Cell> cells = screenData.getCells();
		for (Cell cell : cells) {
			if (!cell.isDisplay()) {
				continue;
			}
			if (cell.getId() == 5015) {
				GLog.info("goaler", "x={},y={}", cell.getX(), cell.getY());
			}
			float[] point = translatePoint.translate(cell.getX(), cell.getY());
			if ((point[0] + cell.getR()) >= 0 && (point[0] - cell.getR()) <= canvas.getWidth()
					&& (point[1] + cell.getR()) >= 0 && (point[1] - cell.getR()) <= canvas.getHeight()) {
				 setColor(paint, cell.getBackground());
				canvas.drawCircle(point[0], point[1], cell.getR() * scale, paint);
			}
		}

		holder.unlockCanvasAndPost(canvas);
	}

	private float getScale(Canvas canvas, ConvertCoordinateUtil translatePoint) {
		int w = screenData.getScreen_right() - screenData.getScreen_left();
		int h = screenData.getScreen_down() - screenData.getScreen_up();
		float scale = 1.0f;
		float scale_w = (float) canvas.getWidth() / 3 / w;
		float scale_h = (float) canvas.getHeight() / 3 / h;
		scale = scale_w < scale_h ? scale_w : scale_h;
		if (scale >= 1.0f) {
			scale = 1.0f;
		} else {
			int x = screenData.getCentral_x() - (int) (canvas.getWidth() / 2.0 / scale);
			int y = screenData.getCentral_y() - (int) (canvas.getHeight() / 2.0 / scale);
			translatePoint.leftTopPoint(x, y);
		}
		return scale;
	}

	private void drawBorder(Canvas canvas, ConvertCoordinateUtil translatePoint) {
		paint.setColor(Color.BLACK);
		float[] p1 = translatePoint.translate(0, 0);
		float[] p2 = translatePoint.translate(0, 30000);
		float[] p3 = translatePoint.translate(30000, 30000);
		float[] p4 = translatePoint.translate(30000, 0);
		canvas.drawLine(p1[0], p1[1], p2[0], p2[1], paint);
		canvas.drawLine(p3[0], p3[1], p2[0], p2[1], paint);
		canvas.drawLine(p1[0], p1[1], p4[0], p4[1], paint);
		canvas.drawLine(p3[0], p3[1], p4[0], p4[1], paint);
	}
	
	@SuppressWarnings("deprecation")
	private void setColor(Paint paint, int code) {
		int c = context.getResources().getColor(R.color.green);
		switch (code) {
		case 0:
			c = context.getResources().getColor(R.color.color0);
			break;

		case 1:
			c = context.getResources().getColor(R.color.color1);
			break;

		case 2:
			c = context.getResources().getColor(R.color.color2);
			break;

		case 3:
			c = context.getResources().getColor(R.color.color3);
			break;

		case 4:
			c = context.getResources().getColor(R.color.color4);
			break;

		case 5:
			c = context.getResources().getColor(R.color.color5);
			break;

		case 6:
			c = context.getResources().getColor(R.color.color6);
			break;

		case 7:
			c = context.getResources().getColor(R.color.color7);
			break;

		case 8:
			c = context.getResources().getColor(R.color.color8);
			break;

		case 9:
			c = context.getResources().getColor(R.color.color9);
			break;

		}
		paint.setColor(c);
	}

}
