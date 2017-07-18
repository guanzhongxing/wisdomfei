package com.admintwo.model.pojo;

import com.admintwo.model.Resources;
import com.admintwo.model.User;

public class ResourceUser {
	private Resources resources;
	private User user;

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ResourceUser [resources=" + resources + ", user=" + user + "]";
	}
}
