package com.admintwo.model.pojo;

public class WaitTree {
	private int id;
	private String title;
	private String url;
	private String sort;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getSort() {
		return sort;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "WaitTree [id=" + id + ", title=" + title + ", url=" + url + ", sort=" + sort + "]";
	}

}
