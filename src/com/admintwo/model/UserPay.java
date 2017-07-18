package com.admintwo.model;

public class UserPay {
	private int user_id;
	private String alipay;
	private String weipay;
	private String nowtime;

	public int getUser_id() {
		return user_id;
	}

	public String getAlipay() {
		return alipay;
	}

	public String getWeipay() {
		return weipay;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public void setWeipay(String weipay) {
		this.weipay = weipay;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	@Override
	public String toString() {
		return "UserPay [user_id=" + user_id + ", alipay=" + alipay + ", weipay=" + weipay + ", nowtime=" + nowtime
				+ "]";
	}

}
