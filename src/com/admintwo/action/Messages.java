package com.admintwo.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.admintwo.service.MessagesService;
import com.admintwo.util.GsonUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Title: Messages
 * @Description: 该类用于处理消息
 * @author 徐江飞
 * @date 2017年3月18日 下午4:29:14
 * @version V1.0
 */
@Controller("messages")
@Scope("prototype")
public class Messages extends ActionSupport implements ModelDriven<com.admintwo.model.Messages> {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MessagesService messagesService;

	private com.admintwo.model.Messages messages = new com.admintwo.model.Messages();

	@Override
	public com.admintwo.model.Messages getModel() {
		return messages;
	}

	/**
	 * 该方法用于ajax记录上传资源
	 * 
	 * @throws Exception
	 */
	public void getMessageByEmail() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		List<com.admintwo.model.Messages> msgs = messagesService.getMessageByEmail(user.getEmail());
		System.out.println("====message/getMessageByEmail获取用户所有消息：" + msgs);
		returnJson(msgs);
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
