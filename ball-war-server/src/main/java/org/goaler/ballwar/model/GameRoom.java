package org.goaler.ballwar.model;

import java.io.Serializable;
import java.util.Map;

import org.goaler.ballwar.client.GameRun;

public class GameRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3745522594706185776L;
	
	private String title;
	private String description;
	private String pwd;
	private Map<Integer, Role> roles;
	
	private transient GameRun gameRun;

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

	public Map<Integer, Role> getRoles() {
		return roles;
	}

	public void setRoles(Map<Integer, Role> roles) {
		this.roles = roles;
	}

	public GameRun getGameRun() {
		return gameRun;
	}

	public void setGameRun(GameRun gameRun) {
		this.gameRun = gameRun;
	}
	
}
