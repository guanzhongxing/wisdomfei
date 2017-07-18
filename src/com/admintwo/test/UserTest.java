package com.admintwo.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.admintwo.daoImpl.UserDaoImpl;
import com.admintwo.serviceImpl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserTest {

	@Autowired
	private UserDaoImpl userDaoImpl;
	@Autowired
	private UserServiceImpl userServiceImpl;

	String email = "1181014088@qq.com";

	/**
	 * UserDao和UserService：方法进行单元测试判断邮箱是否存在
	 */
	@Test
	public void testExistEmail() {
		int rows = userDaoImpl.existEmail(email);
		System.out.println("返回1代表数据库存在1条数据，返回0无数据：" + rows);
		boolean flag = userServiceImpl.existEmail(email);
		System.out.println("返回true代表邮箱已存在，返回false邮箱不存在：" + flag);
	}

	@Test
	public void testInsertUser() {
		int rows = userDaoImpl.insertUser("1", "1", email);
		System.out.println("返回1代表数据库插入成功，返回0插入失败：" + rows);
		boolean flag = userServiceImpl.existEmail(email);
		System.out.println("返回true代表数据库插入成功，返回false插入失败：" + flag);
	}

	@Test
	public void testDate() {
		System.out.println("20赞助兑换200积分,时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
}
