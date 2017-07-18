package com.admintwo.service;

import java.util.List;

import com.admintwo.model.Comments;
import com.admintwo.model.pojo.CommentsUserLabels;
import com.admintwo.model.pojo.UserCommentsResources;

public interface CommentsService {

	// 根据资源id获取该资源下的所有评论
	public List<CommentsUserLabels> getCommentsByResources_id(int resources_id);

	// 发表评论
	public boolean addComments(Comments comment);

	// 获取用户最近10条评论
	public List<UserCommentsResources> getCommentsResourcesByUser_id(int user_id);

	// 判断用户是否是第一次评论
	public boolean isfirstComment(int user_id, int resources_id);
}
