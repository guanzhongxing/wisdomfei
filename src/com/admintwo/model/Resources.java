package com.admintwo.model;

public class Resources {
	private int id;
	private String name;
	private int sort;
	private int jifen;
	private int label;
	private String description;
	private String title;
	private String url;
	private String urlpwd;
	private String nowtime;
	private int isfirst;
	private int isjing;
	private int isyuan;
	private int isfriend;
	private int user_id;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getJifen() {
		return jifen;
	}
	public void setJifen(int jifen) {
		this.jifen = jifen;
	}
	public int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlpwd() {
		return urlpwd;
	}
	public void setUrlpwd(String urlpwd) {
		this.urlpwd = urlpwd;
	}
	public String getNowtime() {
		return nowtime;
	}
	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}
	public int getIsfirst() {
		return isfirst;
	}
	public void setIsfirst(int isfirst) {
		this.isfirst = isfirst;
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
	public int getIsfriend() {
		return isfriend;
	}
	public void setIsfriend(int isfriend) {
		this.isfriend = isfriend;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Resources [id=" + id + ", name=" + name + ", sort=" + sort + ", jifen=" + jifen + ", label=" + label
				+ ", description=" + description + ", title=" + title + ", url=" + url + ", urlpwd=" + urlpwd
				+ ", nowtime=" + nowtime + ", isfirst=" + isfirst + ", isjing=" + isjing + ", isyuan=" + isyuan
				+ ", isfriend=" + isfriend + ", user_id=" + user_id + "]";
	}
	
}
