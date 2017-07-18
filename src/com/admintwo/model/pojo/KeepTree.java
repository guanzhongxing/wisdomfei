package com.admintwo.model.pojo;

public class KeepTree {
	private int id;
	private String name;
	private String url;
	private String nowtime;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	@Override
	public String toString() {
		return "KeepTree [id=" + id + ", name=" + name + ", url=" + url + ", nowtime=" + nowtime + "]";
	}

}
