package org.goaler.ballwar.server.manager;

import java.util.List;

import org.goaler.ballwar.server.soul.CellSoul;

public abstract class EntityManager<T extends CellSoul<?>> {
	/**
	 * 区域边长
	 */
	float SIDE_LEN = 1000;
	/**
	 * id中坐标分隔符
	 */
	String KEY_SEP_SIGN = "&";

	/**
	 * 获得个体在管理器的id
	 */
	abstract public String getAreaid(T c);

	/**
	 * 根据坐标获得id
	 */
	abstract public String getAreaid(int x, int y);

	/**
	 * 更新管理器中entity
	 */
	abstract public void updateEntityAreaid(T c);

	/**
	 * 装入管理器
	 */
	abstract public void addEntity(T c);

	/**
	 * 移出unit
	 */
	abstract public void remove(T c);

	/**
	 * 获得个体区域所有个体
	 */
	abstract public List<?> getAreaEntities(T c);

	/**
	 * 获得坐标区域所有个体
	 */
	abstract public List<?> getAreaEntities(int x, int y);

	/**
	 * 获得areaid区域所有个体
	 */
	abstract public List<?> getAreaEntities(String areaid);
}
