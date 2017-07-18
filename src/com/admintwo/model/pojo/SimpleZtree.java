package com.admintwo.model.pojo;

public class SimpleZtree {
	private String id; // ID
	private String name;// 菜单名字
	private String pid; // 菜单的父ID
	private String url; // 菜单打开的地址
	private String target; // 菜单在某个frame或者Iframe打开
	private boolean isParent;
	private boolean open;
	private String click;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPid() {
		return pid;
	}

	public String getUrl() {
		return url;
	}

	public String getTarget() {
		return target;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	@Override
	public String toString() {
		return "KnowledgeTree [id=" + id + ", name=" + name + ", pid=" + pid + ", url=" + url + ", target=" + target
				+ ", isParent=" + isParent + ", open=" + open + ", click=" + click + "]";
	}

}
