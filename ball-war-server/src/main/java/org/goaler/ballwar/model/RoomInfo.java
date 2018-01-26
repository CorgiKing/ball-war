package org.goaler.ballwar.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;

/**
 * 游戏房间基础信息
 * @author Goaler
 *
 */
public class RoomInfo implements Serializable {
	private static final long serialVersionUID = 3745522594706185776L;

	private String title;
	private String description;
	private String pwd;
	private Role owner;
	private Map<String, Role> roles;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Map<String, Role> getRoles() {
		return roles;
	}

	public Role getRole(String name) {
		return roles.get(name);
	}

	/**
	 * 添加游戏角色，并返回其在本房间昵称
	 * @param role
	 * @return
	 */
	public Role addRole(Role role) {
		String name = role.getName();
		Role r = roles.get(name);
		Random random = new Random();
		while (r != null) {
			name += random.nextInt(100);
			r = roles.get(name);
			if (r != null) {
				role.setName(name);
			}
		}
		roles.put(role.getName(), role);
		return role;
	}

	public Role getOwner() {
		return owner;
	}

	public void setOwner(Role owner) {
		Role role = addRole(owner);
		this.owner = role;
	}

}
