package com.admintwo.model;

public class Messages {
	private int id;
	private String message;
	private int sort;
	private int isRead;
	private String email;
	private String nowtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	@Override
	public String toString() {
		return "Messages [id=" + id + ", message=" + message + ", sort=" + sort + ", isRead=" + isRead + ", email="
				+ email + ", nowtime=" + nowtime + "]";
	}

}
