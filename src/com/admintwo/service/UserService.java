package com.admintwo.service;

import java.util.List;

import com.admintwo.model.User;
import com.admintwo.model.UserPay;
import com.admintwo.model.pojo.MyLabel;
import com.admintwo.model.pojo.ResourcesTop;
import com.admintwo.model.pojo.UserLabels;
import com.admintwo.model.pojo.UserTop;

public interface UserService {
	// 判断邮箱是否存在。
	public boolean existEmail(String email);

	// 注册用户，将name、password、email插入到user表中。true：插入成功，false：插入失败
	public boolean insertUser(String name, String password, String email);

	// 用户登录时判断是否激活、是否封号。0：未激活，1：已激活（可正常登录），2：封号，3：密码不正确
	public int isactive(String email, String password);

	// 邮件激活
	// 1 判断时间是否有效。若无效，跳转到user_activate页面
	// 2 判断账户是否封号。若封号，跳转到user_activate页面
	// 2 若时间有效，更改激活状态为1，并跳转到user_login页面
	public boolean updateIsactive(String email);

	// 邮箱账户登录成功，根据邮箱获取用户信息，并放入session中
	public User getUserByEmail(String email);

	// 根据id获取用户信息
	public User getUserById(int id);

	// 根据id获取用户信息和标签信息
	public UserLabels getUserLabelsById(int id);

	// 修改界面风格，修改成功：true，修改失败：false
	public boolean updateStyle(String email);

	// 修改用户基本信息，修改成功：true，修改失败：false
	public boolean updateUserSetByEmail(User user);

	// 修改用户头像，修改成功：true，修改失败：false
	public boolean updateUserImgByEmail(User user);

	// 修改用户密码，修改成功：true，修改失败：false
	public boolean updateUserPasswordByEmail(User user);

	// 赞助兑换积分，兑换成功：true，兑换失败：false
	public boolean updateUserMoneyAndJifen(int moneyToJifen, String email);

	// 获取用户分享榜
	public List<UserTop> getTop();

	// 获取资源榜
	public List<ResourcesTop> getResourcesTop();

	// 根据邮箱修改时间
	public int updateNowtime(String email);

	// 根据用户id，获取用户称号
	public MyLabel getLabelByUser_id(int id);

	// 更新用户称号
	public boolean updateLabel(int id, int money);

	// 获取用户最新消息
	public int getNewMessage(String email);

	// 上传支付宝和微信二维码
	public boolean insertUserPay(UserPay userPay);

	// 根据用户id获取用户二维码信息
	public UserPay getUserPay(int id);

	// 根据qq登录，插入一条保存记录
	public boolean insertUser(String name, String password, String email, int isactive, int sex, String img);

	// 账户增加积分
	public int addJifen(int id);
}
