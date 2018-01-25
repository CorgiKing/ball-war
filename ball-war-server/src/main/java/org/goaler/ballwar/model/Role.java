package org.goaler.ballwar.model;

import java.io.Serializable;

public class Role implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1403174129911081788L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
