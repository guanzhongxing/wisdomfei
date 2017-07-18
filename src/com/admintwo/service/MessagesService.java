package com.admintwo.service;

import java.util.List;

public interface MessagesService {
	// 生成消息。1：成功，0：失败
	public int insertMessages(String message, int sort, String email);

	// 获取用户所有消息
	public List<com.admintwo.model.Messages> getMessageByEmail(String email);

	// 根据消息id和用户email更新消息已读
	public boolean updateIsReadByIdAndEmail(int id, String email);

	// 更新用户所有未读消息
	public boolean updateALlIsReadByEmail(String email);
}
