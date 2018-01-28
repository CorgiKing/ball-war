package org.goaler.ballwar.server.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.goaler.ballwar.server.soul.CellSoul;

public class AreaEntityManager extends EntityManager<CellSoul<?>> {

	private Map<String, ConcurrentHashMap<Integer, CellSoul<?>>> areasMap;

	public AreaEntityManager() {
		// 初始化
		areasMap = new HashMap<String, ConcurrentHashMap<Integer, CellSoul<?>>>();
		ConcurrentHashMap<Integer, CellSoul<?>> map;
		for (int i = 1; i <= Math.ceil(GameMapManager.WIDTH / SIDE_LEN); i++) {
			for (int j = 1; j <= Math.ceil(GameMapManager.HEIGHT / SIDE_LEN); j++) {
				map = new ConcurrentHashMap<>();
				areasMap.put(i + KEY_SEP_SIGN + j, map);
			}
		}
	}

	public static void main(String[] args) {
		AreaEntityManager manager = new AreaEntityManager();
		String id = manager.getAreaid(00, 00);
		System.out.println(id);
	}

	@Override
	public String getAreaid(CellSoul<?> c) {
		return getAreaid(c.getX(), c.getY());
	}

	@Override
	public String getAreaid(int x, int y) {
		int xIndex = (int) Math.ceil(x / SIDE_LEN);
		xIndex = xIndex == 0 ? 1 : xIndex;
		int yIndex = (int) Math.ceil(y / SIDE_LEN);
		yIndex = yIndex == 0 ? 1 : yIndex;
		return xIndex + KEY_SEP_SIGN + yIndex;
	}

	@Override
	public void updateEntityAreaid(CellSoul<?> c) {
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
	public void addEntity(CellSoul<?> c) {
		String areaid = getAreaid(c);
		areasMap.get(areaid).put(c.getId(), c);
		c.setAreaId(areaid);
	}

	@Override
	public void remove(CellSoul<?> c) {
		String areaId = c.getAreaId();
		if (areaId != null) {
			areasMap.get(areaId).remove(areaId);
		}
		c.setAreaId(null);
	}

	@Override
	public List<?> getAreaEntities(CellSoul<?> c) {
		String areaId = c.getAreaId();
		return getAreaEntities(areaId);
	}

	@Override
	public List<?> getAreaEntities(int x, int y) {
		String areaId = getAreaid(x, y);
		return getAreaEntities(areaId);
	}

	@Override
	public List<?> getAreaEntities(String areaid) {
		return new ArrayList<>(areasMap.get(areaid).values());
	}

}
