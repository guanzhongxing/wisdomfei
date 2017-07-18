package com.admintwo.dao;

import com.admintwo.model.Keep;

public interface KeepDao {

	// 收藏资源。1：成功，0：失败
	public int insertKeep(Keep keep);
	
	// 取消收藏。1：成功，0：失败
	public int deleteKeep(Keep keep);
	
}
