package org.goaler.ballwar.server.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.goaler.ballwar.common.entity.Cell;
import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.server.manager.AreaEntityManager;
import org.goaler.ballwar.server.manager.EntityManager;
import org.goaler.ballwar.server.soul.CellSoul;
import org.goaler.ballwar.server.soul.HogSoul;
import org.goaler.ballwar.server.soul.MonadSoul;
import org.goaler.ballwar.server.soul.VoleSoul;

/**
 * 游戏房间运行类
 * 
 * @author goaler
 *
 */
public class RoomRun {
	public static final int ROOM_STATE_QUIET = 0;
	public static final int ROOM_STATE_PAUSE = 1;
	public static final int ROOM_STATE_RUNNING = 2;
	public static final int ROOM_STATE_CLOSE = 3;

	public static final int MAX_ROLE = 30;
	public static final int HOG_SIZE_PER_ROLE = 16;

	private RoomInfo room;
	private int state = ROOM_STATE_QUIET;
	/**
	 * 区域管理，将所有entity分别放在不同区域中
	 */
	private EntityManager<Cell> areaManager;

	/**
	 * 等待被玩家获取的最小hog下标
	 */
	private AtomicInteger hogWaiterIndex;
	/**
	 * 所有hog
	 */
	private List<HogSoul> hogWaiters = new ArrayList<>();

	/**
	 * 所有的entity
	 */
	private Map<Integer, CellSoul<?>> allEntitys = new ConcurrentHashMap<>(6000);
	/**
	 * 所有的monad
	 */
	private Map<Integer, MonadSoul> monads = new HashMap<>();
	/**
	 * 所有hog
	 */
	private Map<Integer, HogSoul> hogs = new HashMap<>();

	public RoomRun(RoomInfo room) {
		this.room = room;
	}

	public void start() {
		state = ROOM_STATE_RUNNING;
		areaManager = new AreaEntityManager<Cell>();
		init();
	}

	/**
	 * 初始化地图
	 */
	private void init() {
		initMonads(5000);
		initHogs(MAX_ROLE * HOG_SIZE_PER_ROLE);
	}

	/**
	 * 初始化num个Hog并不显示
	 * 
	 * @param num
	 */
	private void initHogs(int num) {
		hogWaiterIndex = new AtomicInteger();
		for (int i = 0; i < num; ++i) {
			HogSoul hogSoul = createHogSoul();
			allEntitys.put(hogSoul.getId(), hogSoul);
			hogWaiters.add(hogSoul);
			hogs.put(hogSoul.getId(), hogSoul);
			areaManager.addEntity(hogSoul.getInfo());
		}
	}

	/**
	 * 初始化num个Monad并显示
	 * 
	 * @param num
	 */
	private void initMonads(int num) {
		for (int i = 0; i < num; ++i) {
			MonadSoul monadSoul = createMonadSoul();
			monadSoul.setDisplay(true);
			allEntitys.put(monadSoul.getId(), monadSoul);
			monads.put(monadSoul.getId(), monadSoul);
			areaManager.addEntity(monadSoul.getInfo());
		}
	}

	public VoleSoul createVole() {
		VoleSoul vole = new VoleSoul(this);
		allEntitys.put(vole.getId(), vole);
		return vole;
	}

	public MonadSoul createMonadSoul() {
		MonadSoul monadSoul = new MonadSoul(this);
		return monadSoul;
	}

	public HogSoul createHogSoul() {
		HogSoul hogSoul = new HogSoul(this);
		allEntitys.put(hogSoul.getId(), hogSoul);
		return hogSoul;
	}

	public List<HogSoul> fetchHog(int n) {
		int index = hogWaiterIndex.getAndAdd(n);
		List<HogSoul> list = null;
		if (index < hogWaiters.size()) {
			list = new ArrayList<>();
			for (int i = 0; i < n; ++i) {
				list.add(hogWaiters.get(index + i));
			}
		}
		return list;
	}

	public HogSoul fetchHog() {
		int index = hogWaiterIndex.incrementAndGet();
		if (index < hogWaiters.size()) {
			return hogWaiters.get(index);
		}
		return null;
	}

	public RoomInfo getRoomInfo() {
		return room;
	}

	public boolean isStarted() {
		if (state != ROOM_STATE_QUIET) {
			return true;
		}
		return false;
	}

	public Role getOwner() {
		return room.getOwner();
	}

	public Role addRole(Role role) {
		return room.addRole(role);
	}

	public EntityManager<Cell> getAreaManager() {
		return areaManager;
	}

	public Map<Integer, CellSoul<?>> getAllEntitys() {
		return allEntitys;
	}

}
