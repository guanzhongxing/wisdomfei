package com.admintwo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @Title: Jsp
 * @Description: 该Action仅用于页面跳转，并不含有任何业务逻辑
 * @author 徐江飞
 * @data 2017年3月15日 上午9:03:44
 * @version V1.0
 */

@Controller("jsp")
@Scope("prototype")
public class Jsp extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		// 设置用户当前访问的界面,方便用户登录后直接跳转到前一个页面
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		String actionPath = path.substring(path.lastIndexOf('/') + 1);
		// 访问服务器所带有的参数信息
		String queryInfo = request.getQueryString();
		if (queryInfo != null && (!queryInfo.equals(""))) {
			actionPath = actionPath + "?" + queryInfo;
		}
		// 用户没有登录的时候，保存浏览记录，用于登录后返回上一级
		if (session.getAttribute("user") == null) {
			if (!"user_login".equals(actionPath) && !"user_reg".equals(actionPath)) {
				System.out.println("====jsp地址：" + actionPath);
				session.setAttribute("userURL", actionPath);
			}
		}
		return SUCCESS;
	}
}
