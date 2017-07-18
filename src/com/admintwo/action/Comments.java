package com.admintwo.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.admintwo.model.pojo.CommentsUserLabels;
import com.admintwo.model.pojo.UserCommentsResources;
import com.admintwo.service.CommentsService;
import com.admintwo.util.GsonUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Title: Comments
 * @Description: 该类用于处理资源操作
 * @author 徐江飞
 * @date 2017年3月18日 下午4:29:14
 * @version V1.0
 */
@Controller("comments")
@Scope("prototype")
public class Comments extends ActionSupport implements ModelDriven<com.admintwo.model.Comments> {
	private static final long serialVersionUID = 1L;

	private com.admintwo.model.Comments comments = new com.admintwo.model.Comments();

	@Autowired
	private CommentsService commentsService;

	@Override
	public com.admintwo.model.Comments getModel() {
		return comments;
	}

	/**
	 * 该方法用于ajax根据资源id，获取资源所有信息
	 * 
	 * @throws Exception
	 */
	public void getCommentsByResources_id() throws Exception {
		System.out.println("====comments/getCommentsByResources_id获取资源id:" + comments.getResources_id());
		List<CommentsUserLabels> commentsUserLabelsList = commentsService.getCommentsByResources_id(comments.getResources_id());
		System.out.println("====comments/comments.getResources_id()根据资源id获取所有评论信息：" + commentsUserLabelsList);
		returnJson(commentsUserLabelsList);
	}
	
	/**
	 * 该方法用于ajax获取某个用户前10条评论
	 * 
	 * @throws Exception
	 */
	public void getTenCommentsByUser_id() throws Exception {
		System.out.println("====comments/getTenCommentsByUser_id获取用户id:" + comments.getUser1_id());
		List<UserCommentsResources> ucrss = commentsService.getCommentsResourcesByUser_id(comments.getUser1_id());
		System.out.println("====comments/comments.getTenCommentsByUser_id()根据资源id获取最近10条评论信息：" + ucrss);
		returnJson(ucrss);
	}

	/**
	 * 该方法用于返回json
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public void returnJson(Object obj) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
		PrintWriter out = response.getWriter();
		out.print(GsonUtils.toJson(obj));
		out.flush();
		out.close();
		return;
	}

}
