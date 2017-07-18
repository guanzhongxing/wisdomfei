package com.admintwo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.CommentsDao;
import com.admintwo.model.Comments;
import com.admintwo.model.pojo.CommentsUserLabels;
import com.admintwo.model.pojo.UserCommentsResources;
import com.admintwo.service.CommentsService;

@Component("CommentsServiceImpl")
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private CommentsDao commentsDao;

	@Override
	public List<CommentsUserLabels> getCommentsByResources_id(int resources_id) {
		List<CommentsUserLabels> commentsUserLabelsList = commentsDao.getCommentsByResources_id(resources_id);
		return commentsUserLabelsList;
	}

	@Override
	public boolean addComments(Comments comment) {
		int rows = commentsDao.addComments(comment);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<UserCommentsResources> getCommentsResourcesByUser_id(int user_id) {
		return commentsDao.getCommentsResourcesByUser_id(user_id);
	}

	@Override
	public boolean isfirstComment(int user_id, int resources_id) {
		return commentsDao.isfirstComment(user_id, resources_id);
	}

}
