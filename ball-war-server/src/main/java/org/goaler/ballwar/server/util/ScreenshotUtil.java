package org.goaler.ballwar.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.server.manager.EntityManager;
import org.goaler.ballwar.server.manager.GameMapManager;

public class ScreenshotUtil<T extends Cell> {

	public int central_x;
	public int central_y;
	public int screen_left;
	public int screen_right;
	public int screen_up;
	public int screen_down;

	public List<T> screenshot(List<T> cells, EntityManager<T> areasManage, int screenWidth, int screenHeight) {
		List<T> retCells = new ArrayList<T>();

		Map<String, Integer> border = findBorder(cells);
		int x_min = border.get("x_min");
		int x_max = border.get("x_max");
		int y_min = border.get("y_min");
		int y_max = border.get("y_max");

		// 此次截屏相关信息
		central_x = (x_max + x_min) / 2;
		central_y = (y_max + y_min) / 2;
		screen_left = x_min;
		screen_right = x_max;
		screen_up = y_min;
		screen_down = y_max;

		// 根据手机尺寸向外扩展获得显示区域
		int w = x_max - x_min;
		int h = y_max - y_min;

		// 计算比例
		float scale = 1.0f;
		float scale_w = (float) screenWidth / 3 / w;
		float scale_h = (float) screenHeight / 3 / h;
		scale = scale_w < scale_h ? scale_w : scale_h;
		if (scale >= 1.0f) {
			scale = 1.0f;
		}

		float ratio = (float) screenWidth / screenHeight;
		if (w / h > ratio) {
			int w_new = (int) (screenWidth / scale);
			// w += 2000;// 向两边各扩展1000

			int w_c = (w_new - w) / 2;
			int h_c = (int) (w_new / ratio - h);

			x_min -= w_c;
			x_max += w_c;
			y_min -= h_c;
			y_max += h_c;

		} else {
			int h_new = (int) (screenHeight / scale);
			// h += 2000;

			int h_c = (h_new - w) / 2;
			int w_c = (int) (h_new * ratio - w);

			x_min -= w_c;
			x_max += w_c;
			y_min -= h_c;
			y_max += h_c;
		}

		// 判断左上角，右下角向外拓展后的边界
		// x_min -= 1000;
		if (x_min < 0) {
			x_min = 0;
		}
		// x_max += 1000;
		if (x_max > GameMapManager.WIDTH) {
			x_max = GameMapManager.WIDTH;
		}

		// y_min -= 1000;
		if (y_min < 0) {
			y_min = 0;
		}
		// y_max += 1000;
		if (y_max > GameMapManager.HEIGHT) {
			y_max = GameMapManager.HEIGHT;
		}

		// 左上角，右下角向外拓展后的分区id
		int minXIndex = areasManage.getIndex(x_min);
		int maxXIndex = areasManage.getIndex(x_max);
		int minYIndex = areasManage.getIndex(y_min);
		int maxYIndex = areasManage.getIndex(y_max);

		// 第一层分区id 左x 右x
		for (int i = minXIndex; i <= maxXIndex; ++i) {
			// 第一层分区id 上y 下y
			for (int j = minYIndex; j <= maxYIndex; ++j) {

				retCells.addAll(areasManage.getAreaEntities(i + areasManage.KEY_SEP_SIGN + j));

			}
		}

		return retCells;
	}

	/**
	 * 判断cells的边界
	 * 
	 * @param roles
	 * @return
	 */
	public Map<String, Integer> findBorder(List<T> cells) {
		HashMap<String, Integer> borderMap = new HashMap<String, Integer>();

		int x_min = GameMapManager.WIDTH, x_max = 0;
		int y_min = GameMapManager.HEIGHT, y_max = 0;
		int t;
		for (T c : cells) {
			t = c.getX() - c.getR();// 最左边判断
			if (t < x_min) {
				x_min = t;
			}
			t = c.getX() + c.getR();// 最右边判断
			if (t > x_max) {
				x_max = t;
			}
			t = c.getY() - c.getR();// 最上边判断
			if (t < y_min) {
				y_min = t;
			}
			t = c.getY() + c.getR();// 最下边判断
			if (t > y_max) {
				y_max = t;
			}
		}
		borderMap.put("x_min", x_min);
		borderMap.put("x_max", x_max);
		borderMap.put("y_min", y_min);
		borderMap.put("y_max", y_max);

		return borderMap;
	}
}
