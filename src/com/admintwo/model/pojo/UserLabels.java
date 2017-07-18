package com.admintwo.model.pojo;

import com.admintwo.model.Labels;
import com.admintwo.model.User;

public class UserLabels {
	private User user;
	private Labels labels;

	public User getUser() {
		return user;
	}

	public Labels getLabels() {
		return labels;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLabels(Labels labels) {
		this.labels = labels;
	}

	@Override
	public String toString() {
		return "UserLabels [user=" + user + ", labels=" + labels + "]";
	}

}
