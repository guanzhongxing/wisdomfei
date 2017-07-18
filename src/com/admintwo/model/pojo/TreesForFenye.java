package com.admintwo.model.pojo;

public class TreesForFenye {
	private int id;
	private String title;
	private String url;
	private String sort;
	private int number;
	private String remark;
	private String nowtime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	@Override
	public String toString() {
		return "TreesForFenye [id=" + id + ", title=" + title + ", url=" + url + ", sort=" + sort + ", number=" + number
				+ ", remark=" + remark + ", nowtime=" + nowtime + "]";
	}

}
