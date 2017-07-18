package com.admintwo.model.pojo;

public class TreeLaba {
	private String message;
	private int daynum;
	private int allnum;
	private String nowtime;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDaynum() {
		return daynum;
	}

	public void setDaynum(int daynum) {
		this.daynum = daynum;
	}

	public int getAllnum() {
		return allnum;
	}

	public void setAllnum(int allnum) {
		this.allnum = allnum;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	@Override
	public String toString() {
		return "TreeLaba [message=" + message + ", daynum=" + daynum + ", allnum=" + allnum + ", nowtime=" + nowtime
				+ "]";
	}

}
