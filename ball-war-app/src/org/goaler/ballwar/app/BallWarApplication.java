package org.goaler.ballwar.app;

import java.io.IOException;
import java.net.Socket;

import org.goaler.ballwar.app.activity.LauncherActivity;
import org.goaler.ballwar.app.manager.SharedPreferencesManager;
import org.goaler.ballwar.app.model.AppConstant;
import org.goaler.ballwar.app.util.ConnectServerUtil;
import org.goaler.ballwar.app.util.GLog;
import org.goaler.ballwar.app.util.SerializeUtil;
import org.goaler.ballwar.app.util.ToastUtil;
import org.goaler.ballwar.common.io.DataTransfer;
import org.goaler.ballwar.common.io.bio.BioTcpSerialDataTransfer;
import org.goaler.ballwar.common.model.Role;
import org.goaler.ballwar.common.model.RoomInfo;
import org.goaler.ballwar.common.msg.MsgManager;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class BallWarApplication extends Application {
	private Role role;
	private RoomInfo room;

	private SharedPreferencesManager spm;
	private MsgManager msgManager;

	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences sp = getSharedPreferences(AppConstant.SP_DEFAULT, MODE_PRIVATE);
		spm = new SharedPreferencesManager(sp);
		initRoleInfo();
		initRoomInfo();
	}

	/**
	 * 获取缓存中的Room信息
	 */
	private void initRoomInfo() {
		room = spm.getData(AppConstant.SP_KEY_ROOM, RoomInfo.class);
		Log.i("goaler", "init room:" + room);
	}

	/**
	 * 获取缓存中的Role信息
	 */
	private void initRoleInfo() {
		role = spm.getData(AppConstant.SP_KEY_ROLE, Role.class, new SharedPreferencesManager.DefectOperator<Role>() {

			@Override
			public Role oper(SharedPreferences sp) {
				String roleName = "u" + System.currentTimeMillis();
				Role r = new Role();
				r.setName(roleName);
				Editor edit = sp.edit();
				edit.putString(AppConstant.SP_KEY_ROLE, SerializeUtil.toJson(r));
				edit.commit();
				return r;
			}
		});
		Log.i("goaler", "init role:" + role);
	}
	
	public void cacheRoom() {
		spm.save(AppConstant.SP_KEY_ROOM, room);
		Log.i("goaler", "cache room：" + room);
	}

	public void cacheRole() {
		spm.save(AppConstant.SP_KEY_ROLE, role);
		Log.i("goaler", "cache role：" + role);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public RoomInfo getRoom() {
		return room;
	}

	public void setRoom(RoomInfo room) {
		this.room = room;
	}

	public SharedPreferencesManager getSpm() {
		return spm;
	}

	public void setSpm(SharedPreferencesManager spm) {
		this.spm = spm;
	}

	public MsgManager getMsgManager() {
		return msgManager;
	}

	public void setMsgManager(MsgManager msgManager) {
		this.msgManager = msgManager;
	}
}
