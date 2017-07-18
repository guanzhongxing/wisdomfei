package com.admintwo.model;

public class Keep {
	private int id;
	private int user_id;
	private int resources_id;
	private String nowtime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getResources_id() {
		return resources_id;
	}
	public void setResources_id(int resources_id) {
		this.resources_id = resources_id;
	}
	public String getNowtime() {
		return nowtime;
	}
	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}
	@Override
	public String toString() {
		return "keep [id=" + id + ", user_id=" + user_id + ", resources_id=" + resources_id + ", nowtime=" + nowtime
				+ "]";
	}
}
