package com.admintwo.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.admintwo.model.Resources;
import com.admintwo.model.UserPay;
import com.admintwo.model.pojo.MyLabel;
import com.admintwo.model.pojo.ResourcesTop;
import com.admintwo.model.pojo.UserTop;
import com.admintwo.service.CommentsService;
import com.admintwo.service.MessagesService;
import com.admintwo.service.ResourcesService;
import com.admintwo.service.UserService;
import com.admintwo.util.EmailUtils;
import com.admintwo.util.GsonUtils;
import com.admintwo.util.ToolsUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @Title: User
 * @Description: 该类用于处理用户操作
 * @author 徐江飞
 * @date 2017年3月18日 下午4:29:14
 * @version V1.0
 */
@Controller("user")
@Scope("prototype")
public class User extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// 生成验证码所需参数
	private ByteArrayInputStream inputStream;
	private static int WIDTH = 100;
	private static int HEIGHT = 30;
	private static Random random = new Random();
	private static String number;

	public static String getNumber() {
		return number;
	}

	public static void setNumber(String number) {
		User.number = number;
	}

	public static Random getRandom() {
		return random;
	}

	public static void setRandom(Random random) {
		User.random = random;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	private static String createRandom() {

		String str = "2323456789qwertyuipasdfghjkzpzxcvbnm";
		char[] rands = new char[4];

		for (int i = 0; i < 4; i++) {
			rands[i] = str.charAt(random.nextInt(36));
		}

		return new String(rands);
	}

	private void drawBackground(Graphics g) {

		// 画背景
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 随机产生 120 个干扰点
		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}

	private void drawRands(Graphics g, String rands) {

		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));

		// 在不同的高度上输出验证码的每个字符
		g.drawString("" + rands.charAt(0), 16, 21);
		g.drawString("" + rands.charAt(1), 36, 18);
		g.drawString("" + rands.charAt(2), 51, 22);
		g.drawString("" + rands.charAt(3), 76, 19);

	}

	/**
	 * 该方法用于生成验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String createImage() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		// 设置浏览器不要缓存此图片
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String rands = createRandom();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		// 产生图像
		drawBackground(g);
		drawRands(g, rands);
		System.out.println("====当前验证码：" + rands);

		// 结束图像 的绘制 过程， 完成图像
		g.dispose();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpeg", outputStream);
		ByteArrayInputStream input = new ByteArrayInputStream(outputStream.toByteArray());
		this.setInputStream(input);
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("checkCode", rands);
		input.close();
		outputStream.close();

		return SUCCESS;
	}

	/**
	 * 该方法用于ajax获取当前验证码
	 * 
	 * @throws Exception
	 */
	public void getCheckCode() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();

		String checkCode = ((String) session.getAttribute("checkCode")).replace(" ", "");
		System.out.println("====user/createImage验证码：" + checkCode);

		returnJson(checkCode);
	}

	@Autowired
	private UserService userService;

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 该方法用于ajax判断邮箱是否存在
	 * 
	 * @throws Exception
	 */
	public void existEmail() throws Exception {
		boolean flag = userService.existEmail(email);
		System.out.println("====user/existEmail返回true邮箱存在，返回false不存在：" + flag);

		returnJson(flag);
	}

	/**
	 * 该方法用于用户注册
	 */
	public String register() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String password = ToolsUtils.MD5(request.getParameter("password"));
		String email = request.getParameter("email");
		boolean flag = userService.insertUser(name, password, email);
		if (flag) {
			try {
				EmailUtils.sendEmail(name, email);
			} catch (MessagingException e) {
				System.out.println("====user/register账号激活邮件发送失败");
			}
			return SUCCESS;
		} else
			return ERROR;
	}

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 该方法用于ajax判断邮箱是否激活、是否封号
	 * 
	 * @throws Exception
	 */
	public void isactive() throws Exception {
		password = ToolsUtils.MD5(password);
		System.out.println("密码" + password);
		int flag = userService.isactive(email, password);
		System.out.println("====user/isactivate返回0未激活，1已激活，2封号，3密码不正确：" + flag);

		returnJson(flag);
	}

	/**
	 * 该方法用于邮件激活账户
	 */
	public String activate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String email = request.getParameter("email");
		boolean flag = userService.updateIsactive(email);
		if (flag) {
			return SUCCESS;
		} else
			return ERROR;
	}

	/**
	 * 该方法用于重发邮件激活账户
	 */
	public String reactivate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String email = request.getParameter("email");
		// 修改用户注册时间
		int rows = userService.updateNowtime(email);
		System.out.println("====user/reactivate修改用户注册时间，1成功0失败：" + rows);
		if (rows == 1) {
			try {
				EmailUtils.sendEmail(userService.getUserByEmail(email).getName(), email);
			} catch (MessagingException e) {
				System.out.println("====user/reactivate账号激活邮件发送失败");
			}
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	private String pass;
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * 该方法用于邮箱账户登录成功后处理操作
	 */
	public String login() {
		// 由于js+ajax判断用户登录情况，可以肯定用户登录成功。
		// 登录成功后，根据email获取该账户的所有信息，放到session中
		com.admintwo.model.User user = userService.getUserByEmail(email);
		System.out.println("====user/login加密密码：" + ToolsUtils.MD5(pass));
		// 获取用户之前浏览记录
		if (user.getPassword().equals(ToolsUtils.MD5(pass))) {
			HttpServletRequest request = ServletActionContext.getRequest();
			request.getSession().setAttribute("user", user);
			path = (String) request.getSession().getAttribute("userURL");
			if (path != null && path != "") {
				System.out.println("====user/login登录之前的路径：" + path);
				return "userPath";
			} else {
				return SUCCESS;
			}
		}
		return ERROR;
	}

	/**
	 * 该方法用于ajax判断session(user)是否存在。是：true，否：false
	 * 
	 * @throws Exception
	 */
	public void isUserSession() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean flag;
		// 判断session中user对象为null的话，返回false
		if (request.getSession().getAttribute("user") == null) {
			flag = false;
		} else {
			flag = true;
		}
		returnJson(flag);
	}

	/**
	 * 该方法用于ajax修改界面风格。并且重新保存session
	 * 
	 * @throws Exception
	 */
	public void updateStyle() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag = userService.updateStyle(user.getEmail());
		System.out.println("====user/updateStyle风格修改，true成功，false失败：" + flag + ",email:" + user.getEmail());
		if (flag) {
			user = userService.getUserByEmail(user.getEmail());
			request.getSession().setAttribute("user", user);
		} else {
			flag = false;
		}
		returnJson(flag);
	}

	/**
	 * 该方法用于用户退出，注销账户
	 * 
	 * @throws Exception
	 */
	public String logout() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().removeAttribute("user");
		return SUCCESS;
	}

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 该方法用于ajax根据用户id，获取用户主页信息
	 * 
	 * @throws Exception
	 */
	public void home() throws Exception {
		System.out.println("====user/home用户id：" + id);
		com.admintwo.model.pojo.UserLabels userLabels = userService.getUserLabelsById(id);
		System.out.println("====user/home返回值：" + userLabels);
		returnJson(userLabels);
	}

	private String name;
	private int sex;
	private String city;
	private String motto;
	private String qq;

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getName() {
		return name;
	}

	public int getSex() {
		return sex;
	}

	public String getCity() {
		return city;
	}

	public String getMotto() {
		return motto;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	/**
	 * 该方法根据邮箱，修改主页信息
	 * 
	 * @throws Exception
	 */
	public void updateSet() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		user.setEmail(email);
		user.setName(name);
		user.setSex(sex);
		user.setCity(city);
		user.setMotto(motto);
		user.setQq(qq);
		boolean flag = userService.updateUserSetByEmail(user);
		if (flag) {
			user = userService.getUserByEmail(user.getEmail());
			request.getSession().setAttribute("user", user);
		} else {
			flag = false;
		}
		returnJson(flag);
	}

	private File file; // 接收这个上传的文件
	private String fileFileName; // Struts2提供的格式，在文件名后+FileName就是上传文件的名字

	public File getFile() {
		return file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * 该方法用户上传用户头像图片，修改图片名称，修改数据库url地址
	 * 
	 * @throws Exception
	 */
	public void uploadImg() throws Exception {
		// 文件在src下，直接用文件名
		ResourceBundle resource = ResourceBundle.getBundle("img");
		String realPath = resource.getString("userImg");

		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		// 页面返回信息
		String info = "";
		String[] str = { ".jpg", ".jpeg", ".bmp", ".gif", ".png" };
		// 限定文件大小是4MB
		if (file == null || file.length() > 4194304) {
			info = "请确保上传文件的大小不超过4M";
		}
		for (String s : str) {
			if (fileFileName.endsWith(s)) {
				fileFileName = "user" + user.getId() + s;
				// fileFileName = UUID.randomUUID() + s;
				// String realPath =
				// ServletActionContext.getServletContext().getRealPath("res/images/userImg/");//
				// 实际路径
				File saveFile = new File(new File(realPath), fileFileName); // 在该实际路径下实例化一个文件
				// 判断父目录是否存在
				if (!saveFile.getParentFile().exists()) {
					saveFile.getParentFile().mkdirs();
				}
				try {
					// 执行文件上传
					// FileUtils 类名 org.apache.commons.io.FileUtils;
					// 是commons-io包中的，commons-fileupload 必须依赖
					// commons-io包实现文件上次，实际上就是将一个文件转换成流文件进行读写
					FileUtils.copyFile(file, saveFile);
				} catch (IOException e) {
					info = "文件上传失败";
				}
				// 文件进行尺寸调整以及图片大小压缩处理
				if (!".gif".equals(s)) {
					ToolsUtils.handlePicture(realPath + fileFileName, realPath + fileFileName, 168, 168);
				}
				info = "res/images/userImg/" + fileFileName;
			}
		}
		System.out.println("====user/uploadImg：文件上传返回信息--" + info);
		returnJson(info);
	}

	private String img;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 该方法根据邮箱，修改用户头像连接
	 * 
	 * @throws Exception
	 */
	public void updateImg() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		user.setEmail(email);
		user.setImg(img);
		boolean flag = userService.updateUserImgByEmail(user);
		if (flag) {
			user = userService.getUserByEmail(user.getEmail());
			request.getSession().setAttribute("user", user);
		} else {
			flag = false;
		}
		returnJson(flag);
	}

	/**
	 * 该方法用于ajax判断密码是否正确
	 * 
	 * @throws Exception
	 */
	public void checkPassword() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = userService
				.getUserByEmail(((com.admintwo.model.User) request.getSession().getAttribute("user")).getEmail());
		boolean flag = false;
		System.out.println("====user/checkPassword：判断用户密码是否正确--");
		System.out.println("====用户界面输入密码（加密后）：" + ToolsUtils.MD5(password));
		System.out.println("====用户数据库中密码（加密后）：" + user.getPassword());
		if (user.getPassword().equals(ToolsUtils.MD5(password))) {
			flag = true;
		}
		returnJson(flag);
	}

	/**
	 * 该方法根据邮箱，修改用户密码
	 * 
	 * @throws Exception
	 */
	public void updatePassword() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = new com.admintwo.model.User();
		user.setEmail(((com.admintwo.model.User) request.getSession().getAttribute("user")).getEmail());
		user.setPassword((ToolsUtils.MD5(password)));
		boolean flag = userService.updateUserPasswordByEmail(user);
		System.out.println("====user/updatePassword修改密码：true为修改成功，false为修改失败：" + flag);
		returnJson(flag);
	}

	/**
	 * 该方法邮件修改用户密码
	 * 
	 * @throws Exception
	 */
	public void rePassword() throws Exception {
		System.out.println("====user/rePassword用户邮箱：" + email);
		System.out.println("====user/rePassword用户密码：" + password);
		com.admintwo.model.User user = new com.admintwo.model.User();
		user.setEmail(email);
		user.setPassword((ToolsUtils.MD5(password)));
		boolean flag = userService.updateUserPasswordByEmail(user);
		System.out.println("====user/rePassword修改密码：true为修改成功，false为修改失败：" + flag);
		returnJson(flag);
	}

	/**
	 * 该方法根据邮件重置密码
	 * 
	 * @throws Exception
	 */
	public void emailUpdatePassword() throws Exception {
		EmailUtils.sendEmailForpassword(email + "用户", email);
		System.out.println("====user/emailUpdatePassword发送邮件成功");
		returnJson(true);
	}

	private int money;

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * 该方法用于ajax判断余额是否足够
	 * 
	 * @throws Exception
	 */
	public void checkMoney() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = userService
				.getUserByEmail(((com.admintwo.model.User) request.getSession().getAttribute("user")).getEmail());
		boolean flag = false;
		if (money <= user.getMoney() && money > 0) {
			flag = true;
		}
		System.out.println("====user/checkPassword：判断余额是否足够：" + flag);
		returnJson(flag);
	}

	private int moneyToJifen;

	public int getMoneyToJifen() {
		return moneyToJifen;
	}

	public void setMoneyToJifen(int moneyToJifen) {
		this.moneyToJifen = moneyToJifen;
	}

	/**
	 * 该方法赞助兑换积分
	 * 
	 * @throws Exception
	 */
	public void moneyToJifen() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		System.out.println("====user/moneyToJifen兑换金额：" + moneyToJifen);
		boolean flag = false;
		if (userService.getUserById(user.getId()).getMoney() >= moneyToJifen) {
			flag = userService.updateUserMoneyAndJifen(moneyToJifen, user.getEmail());
			System.out.println("====user/moneyToJifen兑换积分：true为兑换成功，false为兑换失败：" + flag);
			if (flag) {
				user = userService.getUserByEmail(user.getEmail());
				request.getSession().setAttribute("user", user);
			}
		}
		returnJson(flag);
	}

	/**
	 * 该方法用于ajax根据用户email，获取用户信息
	 * 
	 * @throws Exception
	 */
	public void getUserByEmail() throws Exception {
		System.out.println("====user/getUserByEmail用户email：" + email);
		com.admintwo.model.User user = userService.getUserByEmail(email);
		System.out.println("====user/getUserByEmail返回值：" + user);
		returnJson(user);
	}

	@Autowired
	private CommentsService commentsService;

	@Autowired
	private MessagesService messagesService;

	@Autowired
	private ResourcesService resourcesService;

	private String c_content;
	private int c_resources_id;
	private int c_user2_id;
	private int c_lou;

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	public int getC_resources_id() {
		return c_resources_id;
	}

	public void setC_resources_id(int c_resources_id) {
		this.c_resources_id = c_resources_id;
	}

	public int getC_user2_id() {
		return c_user2_id;
	}

	public void setC_user2_id(int c_user2_id) {
		this.c_user2_id = c_user2_id;
	}

	public int getC_lou() {
		return c_lou;
	}

	public void setC_lou(int c_lou) {
		this.c_lou = c_lou;
	}

	/**
	 * 该方法ajax用户发表评论
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void addcomments() throws Exception {
		// 获取发表评论当前用户
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		// 根据资源id获取资源信息
		Resources resources = resourcesService.getResourceByid(c_resources_id);
		// 根据资源的用户id获取资源拥有者email
		String message_user_email = userService.getUserById(resources.getUser_id()).getEmail();

		com.admintwo.model.Comments comment = new com.admintwo.model.Comments();
		comment.setContent(c_content);
		comment.setResources_id(c_resources_id);
		if (user == null) {
			comment.setUser1_id(0);
		} else {
			comment.setUser1_id(user.getId());
		}
		comment.setUser2_id(c_user2_id);
		comment.setLou(c_lou);

		// 如果评论的是公告、原创代码、高质量代码，评论之前进行判断，用户是否第一次评论
		boolean isFirst = false;
		if (resources.getIsfirst() == 1 || resources.getIsyuan() == 1 || resources.getIsjing() == 1) {
			// 判断用户是否是第一次评论
			Boolean flag = commentsService.isfirstComment(user.getId(), resources.getId());
			if (flag) {
				System.out.println("====user/addcomments用户已经评论过该资源，不加积分");
			} else {
				System.out.println("====user/addcomments用户尚未评论过该资源，加1积分");
				int rows = userService.addJifen(user.getId());
				if (rows > 0) {
					System.out.println("====user/addcomments用户评论公告、原创、高质量代码资源，兑换1积分");
					com.admintwo.model.Comments c = new com.admintwo.model.Comments();
					c.setContent("积分已+1（系统自动回复消息）");
					c.setResources_id(c_resources_id);
					c.setUser1_id(9);
					if (user == null) {
						c.setUser2_id(0);
					} else {
						c.setUser2_id(user.getId());
					}
					c.setLou(c_lou);
					flag = commentsService.addComments(c);
					System.out.println("====user/addcomments系统自动回复积分增加。ture评论成功，false评论失败：" + flag);
				}
			}
		}

		System.out.println("====user/addcomments评论信息：" + comment);
		boolean flag = commentsService.addComments(comment);
		System.out.println("====user/addcomments返回值。ture评论成功，false评论失败：" + flag);
		if (flag) {
			// 评论成功之后，需要创建消息发送给资源拥有者和被回复者
			messagesService.insertMessages(
					"<a style=\"color:#4F99CF;\" target=\"_blank\" href=\"user_home?id=" + user.getId() + "\">"
							+ user.getName()
							+ "</a> 在：<a style=\"color:#4F99CF;\" target=\"_blank\" href=\"resource_detail?id="
							+ resources.getId() + "\">" + resources.getName() + "</a> 评论你：  " + comment.getContent(),
					1, message_user_email);

			// 如果用户2不等于0，意味着是回复评论，需要给被回复者发送一条消息
			if (comment.getUser2_id() != 0) {
				messagesService.insertMessages(
						"<a style=\"color:#4F99CF;\" target=\"_blank\" href=\"user_home?id=" + user.getId() + "\">"
								+ user.getName()
								+ "</a> 在  <a style=\"color:#4F99CF;\" target=\"_blank\" href=\"resource_detail?id="
								+ resources.getId() + "\">" + resources.getName() + "</a> 回复你：" + comment.getContent(),
						1, userService.getUserById(comment.getUser2_id()).getEmail());
			}
		}
		returnJson(flag);
	}

	/**
	 * 该方法删除最新消息
	 * 
	 * @throws Exception
	 */
	public void readMessage() throws Exception {
		System.out.println("====user/readMessage消息id：" + id);
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag = messagesService.updateIsReadByIdAndEmail(id, user.getEmail());
		System.out.println("====user/readMessage消息：" + true);
		returnJson(flag);
	}

	/**
	 * 该方法删除最新消息
	 * 
	 * @throws Exception
	 */
	public void readAllMessage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag = messagesService.updateALlIsReadByEmail(user.getEmail());
		returnJson(flag);
	}

	/**
	 * 该方法获取用户分享榜
	 * 
	 * @throws Exception
	 */
	public void top() throws Exception {
		List<UserTop> uts = userService.getTop();
		System.out.println("====user/top前12分享：" + uts);
		returnJson(uts);
	}

	/**
	 * 该方法获取资源榜
	 * 
	 * @throws Exception
	 */
	public void resourcestop() throws Exception {
		List<ResourcesTop> rts = userService.getResourcesTop();
		System.out.println("====user/getResourcesTop资源前12分享：" + rts);
		returnJson(rts);
	}

	/**
	 * 该方法获取用户称号
	 * 
	 * @throws Exception
	 */
	public void getlabel() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		MyLabel mylabel = userService.getLabelByUser_id(user.getId());
		System.out.println("====user/getlabel获取用户称号：" + mylabel);
		returnJson(mylabel);
	}

	/**
	 * 该方法升级用户称号
	 * 
	 * @throws Exception
	 */
	public void updateLabel() throws Exception {
		System.out.println("====user/updateLabel用户升级称号所需的money：" + money);
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag = false;
		if (userService.getUserById(user.getId()).getMoney() >= money) {
			flag = userService.updateLabel(user.getId(), money);
			user = userService.getUserByEmail(user.getEmail());
			request.getSession().setAttribute("user", user);
		}
		System.out.println("====user/updateLabel升级称号：" + flag);
		returnJson(flag);
	}

	/**
	 * 获取用户最新消息
	 * 
	 * @throws Exception
	 */
	public void getNewMessage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		int rows = userService.getNewMessage(user.getEmail());
		returnJson(rows);
	}

	/**
	 * 该方法用户上传支付宝图片
	 * 
	 * @throws Exception
	 */
	public void zhifubao() throws Exception {
		// 文件在src下，直接用文件名
		ResourceBundle resource = ResourceBundle.getBundle("img");
		String realPath = resource.getString("userpayImg");

		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		// 页面返回信息
		String info = "";
		String[] str = { ".jpg", ".jpeg", ".bmp", ".gif", ".png" };
		// 限定文件大小是4MB
		if (file == null || file.length() > 4194304) {
			info = "请确保上传文件的大小不超过4M";
		}
		for (String s : str) {
			if (fileFileName.endsWith(s)) {
				fileFileName = "alipay" + user.getId() + s;
				// fileFileName = UUID.randomUUID() + s;
				// String realPath =
				// ServletActionContext.getServletContext().getRealPath("res/images/userImg/");//
				// 实际路径
				File saveFile = new File(new File(realPath), fileFileName); // 在该实际路径下实例化一个文件
				// 判断父目录是否存在
				if (!saveFile.getParentFile().exists()) {
					saveFile.getParentFile().mkdirs();
				}
				try {
					// 执行文件上传
					// FileUtils 类名 org.apache.commons.io.FileUtils;
					// 是commons-io包中的，commons-fileupload 必须依赖
					// commons-io包实现文件上次，实际上就是将一个文件转换成流文件进行读写
					FileUtils.copyFile(file, saveFile);
				} catch (IOException e) {
					info = "文件上传失败";
				}
				// 文件进行尺寸调整以及图片大小压缩处理
				if (!".gif".equals(s)) {
					ToolsUtils.handlePicture(realPath + fileFileName, realPath + fileFileName, 140, 140);
				}
				info = "res/images/dashang/img/userpayimg/" + fileFileName;
				UserPay userPay = new UserPay();
				userPay.setUser_id(user.getId());
				userPay.setAlipay(info);
				userPay.setWeipay("0");
				// 图片地址插入到数据库中
				boolean flag = userService.insertUserPay(userPay);
				System.out.println("====user/zhifubao用户上传支付宝二维码：" + flag);
			}
		}
		System.out.println("====user/zhifubao：文件上传返回信息--" + info);
		returnJson(info);
	}

	/**
	 * 该方法用户上传微信图片
	 * 
	 * @throws Exception
	 */
	public void weixin() throws Exception {
		// 文件在src下，直接用文件名
		ResourceBundle resource = ResourceBundle.getBundle("img");
		String realPath = resource.getString("userpayImg");

		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		// 页面返回信息
		String info = "";
		String[] str = { ".jpg", ".jpeg", ".bmp", ".gif", ".png" };
		// 限定文件大小是4MB
		if (file == null || file.length() > 4194304) {
			info = "请确保上传文件的大小不超过4M";
		}
		for (String s : str) {
			if (fileFileName.endsWith(s)) {
				fileFileName = "weipay" + user.getId() + s;
				// fileFileName = UUID.randomUUID() + s;
				// String realPath =
				// ServletActionContext.getServletContext().getRealPath("res/images/userImg/");//
				// 实际路径
				File saveFile = new File(new File(realPath), fileFileName); // 在该实际路径下实例化一个文件
				// 判断父目录是否存在
				if (!saveFile.getParentFile().exists()) {
					saveFile.getParentFile().mkdirs();
				}
				try {
					// 执行文件上传
					// FileUtils 类名 org.apache.commons.io.FileUtils;
					// 是commons-io包中的，commons-fileupload 必须依赖
					// commons-io包实现文件上次，实际上就是将一个文件转换成流文件进行读写
					FileUtils.copyFile(file, saveFile);
				} catch (IOException e) {
					info = "文件上传失败";
				}
				// 文件进行尺寸调整以及图片大小压缩处理
				if (!".gif".equals(s)) {
					ToolsUtils.handlePicture(realPath + fileFileName, realPath + fileFileName, 140, 140);
				}
				info = "res/images/dashang/img/userpayimg/" + fileFileName;
				UserPay userPay = new UserPay();
				userPay.setUser_id(user.getId());
				userPay.setWeipay(info);
				userPay.setAlipay("0");
				// 图片地址插入到数据库中
				boolean flag = userService.insertUserPay(userPay);
				System.out.println("====user/zhifubao用户上传微信二维码：" + flag);
			}
		}
		System.out.println("====user/weixin：文件上传返回信息--" + info);
		returnJson(info);
	}

	/**
	 * 获取用户支付二维码
	 * 
	 * @throws Exception
	 */
	public void getUserPay() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		UserPay userPay = userService.getUserPay(user.getId());
		System.out.println("====user/getUserPay获取用户支付二维码图片地址：" + userPay);
		returnJson(userPay);
	}

	private int resources_id;

	public int getResources_id() {
		return resources_id;
	}

	public void setResources_id(int resources_id) {
		this.resources_id = resources_id;
	}

	/**
	 * 根据资源id获取用户支付二维码
	 * 
	 * @throws Exception
	 */
	public void getUserPayByResources_id() throws Exception {
		System.out.println("====user/getUserPayByResources_id获取用户二维码所需资源id：" + resources_id);
		Resources r = resourcesService.getResourceByid(resources_id);
		System.out.println("====user/getUserPayByResources_id根据资源id获取资源所有信息：" + r);
		UserPay userPay = userService.getUserPay(r.getUser_id());
		System.out.println("====user/getUserPayByResources_id获取用户支付二维码图片地址：" + userPay);
		returnJson(userPay);
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
