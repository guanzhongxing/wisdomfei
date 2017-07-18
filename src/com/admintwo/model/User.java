package com.admintwo.model;

import java.sql.Timestamp;

public class User {
	private int id;
	private String name;
	private String password;
	private String email;
	private String qq;
	private int jifen;
	private int money;
	private int isactive;
	private int style;
	private Timestamp nowtime;
	private int sex;
	private String label;
	private String img;
	private String city;
	private String motto;

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public int getJifen() {
		return jifen;
	}

	public void setJifen(int jifen) {
		this.jifen = jifen;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getIsactive() {
		return isactive;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public Timestamp getNowtime() {
		return nowtime;
	}

	public void setNowtime(Timestamp nowtime) {
		this.nowtime = nowtime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", qq=" + qq
				+ ", jifen=" + jifen + ", money=" + money + ", isactive=" + isactive + ", style=" + style + ", nowtime="
				+ nowtime + ", sex=" + sex + ", label=" + label + ", img=" + img + ", city=" + city + ", motto=" + motto
				+ "]";
	}

}
