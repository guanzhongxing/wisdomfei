package com.admintwo.model.pojo;

public class MyLabel {
	private int id;
	private int mymoney;
	private String mylabel;
	private int money;

	public int getId() {
		return id;
	}

	public int getMymoney() {
		return mymoney;
	}

	public String getMylabel() {
		return mylabel;
	}

	public int getMoney() {
		return money;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMymoney(int mymoney) {
		this.mymoney = mymoney;
	}

	public void setMylabel(String mylabel) {
		this.mylabel = mylabel;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "MyLabel [id=" + id + ", mymoney=" + mymoney + ", mylabel=" + mylabel + ", money=" + money + "]";
	}

}
