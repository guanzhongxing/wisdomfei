package com.admintwo.model.pojo;

public class UserCommentsResources {
	private int r_id;
	private String r_name;
	private String c_content;
	private String c_nowtime;
	public int getR_id() {
		return r_id;
	}
	public void setR_id(int r_id) {
		this.r_id = r_id;
	}
	public String getR_name() {
		return r_name;
	}
	public void setR_name(String r_name) {
		this.r_name = r_name;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public String getC_nowtime() {
		return c_nowtime;
	}
	public void setC_nowtime(String c_nowtime) {
		this.c_nowtime = c_nowtime;
	}
	@Override
	public String toString() {
		return "UserCommentsResources [r_id=" + r_id + ", r_name=" + r_name + ", c_content=" + c_content
				+ ", c_nowtime=" + c_nowtime + "]";
	}
}
