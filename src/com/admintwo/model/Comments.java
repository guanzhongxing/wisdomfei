package com.admintwo.model;

public class Comments {
	private int id;
	private String content;
	private int isfriend;
	private String nowtime;
	private int resources_id;
	private int user1_id;
	private int user2_id;
	private int lou;

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public int getIsfriend() {
		return isfriend;
	}

	public String getNowtime() {
		return nowtime;
	}

	public int getResources_id() {
		return resources_id;
	}

	public int getUser1_id() {
		return user1_id;
	}

	public int getUser2_id() {
		return user2_id;
	}

	public int getLou() {
		return lou;
	}

	public void setLou(int lou) {
		this.lou = lou;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIsfriend(int isfriend) {
		this.isfriend = isfriend;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	public void setResources_id(int resources_id) {
		this.resources_id = resources_id;
	}

	public void setUser1_id(int user1_id) {
		this.user1_id = user1_id;
	}

	public void setUser2_id(int user2_id) {
		this.user2_id = user2_id;
	}

	@Override
	public String toString() {
		return "Comments [id=" + id + ", content=" + content + ", isfriend=" + isfriend + ", nowtime=" + nowtime
				+ ", resources_id=" + resources_id + ", user1_id=" + user1_id + ", user2_id=" + user2_id + ", lou="
				+ lou + "]";
	}
}
