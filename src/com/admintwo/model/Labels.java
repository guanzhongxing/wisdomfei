package com.admintwo.model;

public class Labels {
	private int id;
	private String name;
	private int money;
	private int isfriend;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMoney() {
		return money;
	}

	public int getIsfriend() {
		return isfriend;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void setIsfriend(int isfriend) {
		this.isfriend = isfriend;
	}

	@Override
	public String toString() {
		return "Labels [id=" + id + ", name=" + name + ", money=" + money + ", isfriend=" + isfriend + "]";
	}

}
