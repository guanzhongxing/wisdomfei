package com.admintwo.model.pojo;

public class ResourcesTop {
	private int sort;
	private int id;
	private String name;
	private int num;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

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

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "ResourcesTop [sort=" + sort + ", id=" + id + ", name=" + name + ", num=" + num + "]";
	}

}
