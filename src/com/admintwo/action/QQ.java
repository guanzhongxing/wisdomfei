package com.admintwo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.admintwo.service.UserService;
import com.admintwo.util.ToolsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * @Title: QQ
 * @Description: 该Action用于处理qq登录逻辑
 * @author 徐江飞
 * @data 2017年6月30日 上午9:03:44
 * @version V1.0
 */

@Controller("qq")
@Scope("prototype")
public class QQ extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	/**
	 * 调用qq的login接口
	 */
	public void login() throws Exception {
		System.out.println("====QQ/login开始调用qq登录接口");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 处理QQ登录逻辑
	 * 
	 * @throws Exception
	 */
	public String getQQCode() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				System.out.print("====qq/getQQCode没有获取到响应参数");
				return ERROR;
			} else {
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("demo_access_token", accessToken);
				request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();

				System.out.println("====QQ/getQQCode：QQ用户ID：" + openID);
				request.getSession().setAttribute("demo_openid", openID);

				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				if (userInfoBean.getRet() == 0) {
					System.out.println("====QQ/getQQCode：QQ用户昵称：" + userInfoBean.getNickname());
					System.out.println("====QQ/getQQCode：QQ用户性别：" + userInfoBean.getGender());
					System.out.println("====QQ/getQQCode：100尺寸大小的头像：<image src="
							+ userInfoBean.getAvatar().getAvatarURL100() + "><br/>");
					int sex;
					if ("女".equals(userInfoBean.getGender())) {
						sex = 1;
					} else {
						sex = 0;
					}

					boolean flag = userService.existEmail(openID);
					if (flag) {
						System.out.println("====QQ/getQQCode用户已经使用qq登录过，可直接获取user进行登录");
						com.admintwo.model.User user = userService.getUserByEmail(openID);
						request.getSession().setAttribute("user", user);
					} else {
						System.out.println("====QQ/getQQCode未使用qq登录过，需要插入一条数据");
						flag = userService.insertUser(userInfoBean.getNickname(), ToolsUtils.MD5("123456"), openID, 1,
								sex, userInfoBean.getAvatar().getAvatarURL100().replace("http", "https"));
						if (flag) {
							System.out.println("====QQ/getQQCode插入qq登录记录成功，设置session");
							com.admintwo.model.User user = userService.getUserByEmail(openID);
							request.getSession().setAttribute("user", user);
						} else {
							System.out.println("====QQ/getQQCode插入qq登录记录失败，返回邮箱登录");
							return ERROR;
						}
					}

					// 用户没有登录的时候，保存浏览记录，用于登录后返回上一级
					path = (String) request.getSession().getAttribute("userURL");
					if (path != null && path != "") {
						System.out.println("====QQ/getQQCode登录之前的路径：" + path);
						return "userPath";
					}
				} else {
					System.out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
					return ERROR;
				}
			}
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
