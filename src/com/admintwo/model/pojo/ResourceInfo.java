package com.admintwo.model.pojo;

public class ResourceInfo {
	private int id;
	private String name;
	private String nowtime;
	private int isjing;
	private int isyuan;
	private int res_comments_num;
	private int res_download_num;
	private int res_keep_num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNowtime() {
		return nowtime;
	}
	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}
	public int getIsjing() {
		return isjing;
	}
	public void setIsjing(int isjing) {
		this.isjing = isjing;
	}
	public int getIsyuan() {
		return isyuan;
	}
	public void setIsyuan(int isyuan) {
		this.isyuan = isyuan;
	}
	public int getRes_comments_num() {
		return res_comments_num;
	}
	public void setRes_comments_num(int res_comments_num) {
		this.res_comments_num = res_comments_num;
	}
	public int getRes_download_num() {
		return res_download_num;
	}
	public void setRes_download_num(int res_download_num) {
		this.res_download_num = res_download_num;
	}
	public int getRes_keep_num() {
		return res_keep_num;
	}
	public void setRes_keep_num(int res_keep_num) {
		this.res_keep_num = res_keep_num;
	}
	@Override
	public String toString() {
		return "ResourceInfo [id=" + id + ", name=" + name + ", nowtime=" + nowtime + ", isjing=" + isjing + ", isyuan="
				+ isyuan + ", res_comments_num=" + res_comments_num + ", res_download_num=" + res_download_num
				+ ", res_keep_num=" + res_keep_num + "]";
	}
}
