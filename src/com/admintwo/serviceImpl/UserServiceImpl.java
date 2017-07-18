package com.admintwo.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.MessagesDao;
import com.admintwo.dao.UserDao;
import com.admintwo.model.User;
import com.admintwo.model.UserPay;
import com.admintwo.model.pojo.MyLabel;
import com.admintwo.model.pojo.ResourcesTop;
import com.admintwo.model.pojo.UserLabels;
import com.admintwo.model.pojo.UserTop;
import com.admintwo.service.UserService;

@Component("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private MessagesDao messagesDao;

	@Override
	public boolean existEmail(String email) {
		if (userDao.existEmail(email) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean insertUser(String name, String password, String email) {
		if (userDao.insertUser(name, password, email) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public int isactive(String email, String loginpwd) {
		User user = userDao.getUserByEmail(email);
		int isactive = user.getIsactive();
		String password = user.getPassword();
		if (isactive == 0)
			return 0;
		if (isactive == 2)
			return 2;
		if (isactive == 1) {
			// 页面传过来的密码进行md5加密后与数据库密码比较。若不相同返回3：代表密码错误
			if (!loginpwd.equals(password)) {
				return 3;
			}
		}
		return 1;
	}

	@Override
	public boolean updateIsactive(String email) {
		User user = userDao.getUserByEmail(email);
		// 1 根据邮箱获取注册时间和激活状态
		Timestamp nowtime = user.getNowtime();
		int isactive = user.getIsactive();
		// 2 判断时间是否失效，是：返回false
		Timestamp nowsystem = new Timestamp(System.currentTimeMillis());
		double day = (nowsystem.getTime() - nowtime.getTime()) / 1000 / 60 / 60 / 24;
		if (day > 1) {
			return false;
		}
		// 3 判断账户是否已封号，是：返回false
		if (isactive == 2) {
			return false;
		}
		// 4 更改激活状态，返回ture
		int rows = userDao.updateIsactive(email);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userDao.getUserByEmail(email);
		return user;
	}

	@Override
	public User getUserById(int id) {
		User user = userDao.getUserById(id);
		return user;
	}

	@Override
	public UserLabels getUserLabelsById(int id) {
		UserLabels userLabels = userDao.getUserLabelsById(id);
		return userLabels;
	}

	@Override
	public boolean updateStyle(String email) {
		int rows = userDao.updateStyle(email);
		// 修改成功
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserSetByEmail(User user) {
		int rows = userDao.updateUserSetByEmail(user);
		// 修改成功
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserImgByEmail(User user) {
		int rows = userDao.updateUserImgByEmail(user);
		// 修改成功
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserPasswordByEmail(User user) {
		int rows = userDao.updateUserPasswordByEmail(user);
		// 修改成功
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserMoneyAndJifen(int moneyToJifen, String email) {
		int rows = userDao.updateUserMoneyAndJifen(moneyToJifen, email);
		// 修改成功
		if (rows == 1) {
			// 将消费的金额作为消息存入库中
			int m = messagesDao.insertMessages(moneyToJifen + "赞助兑换" + moneyToJifen * 10 + "积分", 2, email);
			if (m == 1)
				System.out.println("用户赞助兑换积分成功");
			else
				System.out.println("UserServiceImpl.updateUserMoneyAdnJifen()中messagesDao.insertMessages()有bug");
			return true;
		}
		return false;
	}

	@Override
	public List<UserTop> getTop() {
		return userDao.getTop();
	}

	@Override
	public List<ResourcesTop> getResourcesTop() {
		return userDao.getResourcesTop();
	}

	@Override
	public int updateNowtime(String email) {
		return userDao.updateNowtime(email);
	}

	@Override
	public MyLabel getLabelByUser_id(int id) {
		return userDao.getLabelByUser_id(id);
	}

	@Override
	public boolean updateLabel(int id, int money) {
		return userDao.updateLabel(id, money);
	}

	@Override
	public int getNewMessage(String email) {
		return userDao.getNewMessage(email);
	}

	@Override
	public boolean insertUserPay(UserPay userPay) {
		return userDao.insertUserPay(userPay);
	}

	@Override
	public UserPay getUserPay(int id) {
		return userDao.getUserPay(id);
	}

	@Override
	public boolean insertUser(String name, String password, String email, int isactive, int sex, String img) {
		return userDao.insertUser(name, password, email, isactive, sex, img);
	}

	@Override
	public int addJifen(int id) {
		return userDao.addJifen(id);
	}

}
