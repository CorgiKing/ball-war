package org.goaler.ballwar.app.util;


/**
 * 坐标相对位置转化
 * x方向：屏幕左向右
 * y方向：屏幕上向下
 * @author Goaler
 *
 */
public class ConvertCoordinateUtil {
	//手机尺寸
	private int width;
	private int height;
	//手机原点在游戏地图中的位置
	private int p_x;
	private int p_y;
	//缩放比例
	private float scale = 1.0f;
	
	
	/**
	 * 屏幕尺寸
	 * @param width
	 * @param height
	 */
	public void Init(int width,int height){
		this.width = width;
		this.height = height;
	}
	/**
	 * 输入为屏幕中心点在游戏地图中的坐标
	 * (使用本方法前必须调用init方法初始化手机屏幕尺寸)
	 * @param central_x
	 * @param central_y
	 */
	public void centralPoint(int central_x,int central_y){
		//计算手机原点在游戏地图里的位置
		p_x = central_x - width/2;
		p_y = central_y - height/2;
	}
	public void leftTopPoint(int left,int top){
		p_x = left;
		p_y = top;
	}
	/**
	 * 初始化缩放比例
	 */
	public void setScale(float scale) {
		this.scale = scale;
	}
	/**
	 * 游戏地图中的坐标(x,y)转化为手机屏幕为width,heitht中的坐标
	 * (使用本方法 前必须调用centralPoint确认中心点)
	 * @param x
	 * @param y
	 * @return
	 */
	public float[] translate(int x,int y){
		return new float[]{(x-p_x)*scale,(y-p_y)*scale};
	}
}
