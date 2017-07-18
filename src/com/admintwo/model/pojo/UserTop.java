package com.admintwo.model.pojo;

public class UserTop {
	private int sort;
	private int id;
	private String name;
	private String img;
	private String num;

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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "UserTop [sort=" + sort + ", id=" + id + ", name=" + name + ", img=" + img + ", num=" + num + "]";
	}

}
