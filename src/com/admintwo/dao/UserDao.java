package com.admintwo.dao;

import java.util.List;

import com.admintwo.model.User;
import com.admintwo.model.UserPay;
import com.admintwo.model.pojo.MyLabel;
import com.admintwo.model.pojo.ResourcesTop;
import com.admintwo.model.pojo.UserLabels;
import com.admintwo.model.pojo.UserTop;

public interface UserDao {
	// 判断邮箱是否存在。1：存在，0：不存在
	public int existEmail(String email);

	// 注册用户，将name、password、email插入到user表中。1：插入成功，0：插入失败
	public int insertUser(String name, String password, String email);

	// 根据邮箱获取用户所有信息
	// TODO 注意：若用户信息进行修改，则此方法和User实体类需进行修改
	public User getUserByEmail(String email);

	// 根据id获取用户所有信息
	// TODO 注意：若用户信息进行修改，则此方法和User实体类需进行修改
	public User getUserById(int id);

	// 根据id获取用户和对应标签所有信息
	// TODO 注意：若用户信息进行修改，则此方法和UserLabels实体类需进行修改
	public UserLabels getUserLabelsById(int id);

	// user表中isactive修改为1激活邮箱账户。0：未激活，1：激活。
	public int updateIsactive(String email);

	// user表中style字段，当0时修改为1,当1时修改为0
	public int updateStyle(String email);

	// 根据邮箱修改对应的基本信息
	public int updateUserSetByEmail(User user);

	// 根据邮箱修改对应的头像
	public int updateUserImgByEmail(User user);

	// 根据邮箱修改密码
	public int updateUserPasswordByEmail(User user);

	// 修改用户积分
	public int updateUserMoneyAndJifen(int moneyToJifen, String email);

	// 获取用户分享榜
	public List<UserTop> getTop();

	// 获取资源榜
	public List<ResourcesTop> getResourcesTop();

	// 根据邮箱修改时间
	public int updateNowtime(String email);

	// 根据用户id获取用户称号
	public MyLabel getLabelByUser_id(int id);

	// 升级用户称号
	public boolean updateLabel(int id, int money);

	// 获取用户最新消息
	public int getNewMessage(String email);

	// 上传支付宝和微信二维码
	public boolean insertUserPay(UserPay userPay);

	// 获取用户二维码信息
	public UserPay getUserPay(int id);

	// qq登录保存一条记录
	public boolean insertUser(String name, String password, String email, int isactive, int sex, String img);

	// 账户增加积分
	public int addJifen(int id);
}
