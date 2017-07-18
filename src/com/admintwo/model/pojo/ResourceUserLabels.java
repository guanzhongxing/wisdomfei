package com.admintwo.model.pojo;

import com.admintwo.model.Resources;

public class ResourceUserLabels {
	private Resources resources;
	private UserLabels userLabels;
	private String words;
	private String r_nowtime;
	private int isshoucang;
	private int res_keep_num;
	private int res_comments_num;
	private int res_download_num;
	public Resources getResources() {
		return resources;
	}
	public void setResources(Resources resources) {
		this.resources = resources;
	}
	public UserLabels getUserLabels() {
		return userLabels;
	}
	public void setUserLabels(UserLabels userLabels) {
		this.userLabels = userLabels;
	}
	public int getIsshoucang() {
		return isshoucang;
	}
	public void setIsshoucang(int isshoucang) {
		this.isshoucang = isshoucang;
	}
	public int getRes_keep_num() {
		return res_keep_num;
	}
	public void setRes_keep_num(int res_keep_num) {
		this.res_keep_num = res_keep_num;
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
	public String getR_nowtime() {
		return r_nowtime;
	}
	public void setR_nowtime(String r_nowtime) {
		this.r_nowtime = r_nowtime;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	@Override
	public String toString() {
		return "ResourceUserLabels [resources=" + resources + ", userLabels=" + userLabels + ", words=" + words
				+ ", r_nowtime=" + r_nowtime + ", isshoucang=" + isshoucang + ", res_keep_num=" + res_keep_num
				+ ", res_comments_num=" + res_comments_num + ", res_download_num=" + res_download_num + "]";
	}
}
