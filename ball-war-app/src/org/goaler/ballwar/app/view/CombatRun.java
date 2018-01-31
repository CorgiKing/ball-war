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

		GLog.info("goaler", "cx:{},cy{}", msg.getParam("central_x", Integer.class),
				msg.getParam("central_y", Integer.class));
		GLog.info("goaler", "cx:{},cy{}", screenData.getCentral_x(), screenData.getCentral_y());

	}

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
		translatePoint.setScale(scale);

		// 画边界
		paint.setColor(Color.BLACK);
		float[] p1 = translatePoint.translate(0, 0);
		float[] p2 = translatePoint.translate(0, 30000);
		float[] p3 = translatePoint.translate(30000, 30000);
		float[] p4 = translatePoint.translate(30000, 0);
		canvas.drawLine(p1[0], p1[1], p2[0], p2[1], paint);
		canvas.drawLine(p3[0], p3[1], p2[0], p2[1], paint);
		canvas.drawLine(p1[0], p1[1], p4[0], p4[1], paint);
		canvas.drawLine(p3[0], p3[1], p4[0], p4[1], paint);

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
				// setColor(paint, unitData.background);
				canvas.drawCircle(point[0], point[1], cell.getR() * scale, paint);
			}
		}

		holder.unlockCanvasAndPost(canvas);
	}

}
