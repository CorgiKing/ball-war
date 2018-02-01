package org.goaler.ballwar.server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.goaler.ballwar.common.entity.Cell;

public class AreaEntityManager<T extends Cell> extends EntityManager<T> {

	private Map<String, ConcurrentHashMap<Integer, T>> areasMap;

	public AreaEntityManager() {
		// 初始化
		areasMap = new HashMap<String, ConcurrentHashMap<Integer, T>>();
		ConcurrentHashMap<Integer, T> map;
		for (int i = 1; i <= Math.ceil(GameMapManager.WIDTH / SIDE_LEN); i++) {
			for (int j = 1; j <= Math.ceil(GameMapManager.HEIGHT / SIDE_LEN); j++) {
				map = new ConcurrentHashMap<>();
				areasMap.put(i + KEY_SEP_SIGN + j, map);
			}
		}
	}

	@Override
	public String getAreaid(T c) {
		return getAreaid(c.getX(), c.getY());
	}

	@Override
	public String getAreaid(int x, int y) {
		int xIndex = getIndex(x);
		int yIndex = getIndex(y);
		return xIndex + KEY_SEP_SIGN + yIndex;
	}

	@Override
	public int getIndex(int n) {
		int index = (int) Math.ceil(n / SIDE_LEN);
		index = index == 0 ? 1 : index;
		return index;
	}

	@Override
	public void updateEntityAreaid(T c) {
		String areaid = getAreaid(c);// 根据坐标获得areaid，现在真实位置id
		String oldAreaId = c.getAreaId();
		if (oldAreaId != null && !areaid.equals(oldAreaId)) {// 上次存入的位置areaid
			// 删除之前的
			areasMap.get(oldAreaId).remove(c.getId());
		}

		// 存入现在的
		areasMap.get(areaid).put(c.getId(), c);
		c.setAreaId(areaid);
	}

	@Override
	public void addEntity(T c) {
		String areaid = getAreaid(c);
		areasMap.get(areaid).put(c.getId(), c);
		c.setAreaId(areaid);
	}

	@Override
	public void remove(T c) {
		String areaId = c.getAreaId();
		if (areaId != null) {
			areasMap.get(areaId).remove(areaId);
		}
		c.setAreaId(null);
	}

	@Override
	public List<T> getAreaEntities(T c) {
		String areaId = c.getAreaId();
		return getAreaEntities(areaId);
	}

	@Override
	public List<T> getAreaEntities(int x, int y) {
		String areaId = getAreaid(x, y);
		return getAreaEntities(areaId);
	}

	@Override
	public List<T> getAreaEntities(String areaid) {
		return new ArrayList<>(areasMap.get(areaid).values());
	}

}
