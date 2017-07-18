package com.admintwo.model;

public class Ztree {
	private int tid;
	private int istree;
	private String id;
	private int pid;
	private String name;
	private String url;
	private int open;
	private int parent;
	private String click;
	private int number;
	private int isfriend;
	private int isorder;
	private String remark;
	private String nowtime;

	public int getTid() {
		return tid;
	}

	public String getId() {
		return id;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIstree() {
		return istree;
	}

	public void setIstree(int istree) {
		this.istree = istree;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIsfriend() {
		return isfriend;
	}

	public void setIsfriend(int isfriend) {
		this.isfriend = isfriend;
	}

	public String getNowtime() {
		return nowtime;
	}

	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	public int getIsorder() {
		return isorder;
	}

	public void setIsorder(int isorder) {
		this.isorder = isorder;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Ztree [tid=" + tid + ", istree=" + istree + ", id=" + id + ", pid=" + pid + ", name=" + name + ", url="
				+ url + ", open=" + open + ", parent=" + parent + ", click=" + click + ", number=" + number
				+ ", isfriend=" + isfriend + ", isorder=" + isorder + ", remark=" + remark + ", nowtime=" + nowtime
				+ "]";
	}

}
