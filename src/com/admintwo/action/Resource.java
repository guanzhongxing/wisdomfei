package com.admintwo.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.admintwo.model.Keep;
import com.admintwo.model.Resources;
import com.admintwo.model.pojo.IndexHtmlResources;
import com.admintwo.model.pojo.ResourceInfo;
import com.admintwo.model.pojo.ResourceSortInfo;
import com.admintwo.service.DownloadService;
import com.admintwo.service.KeepService;
import com.admintwo.service.MessagesService;
import com.admintwo.service.ResourcesService;
import com.admintwo.service.UserService;
import com.admintwo.util.GsonUtils;
import com.admintwo.util.ToolsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Title: Resource
 * @Description: 该类用于处理资源操作
 * @author 徐江飞
 * @date 2017年3月18日 下午4:29:14
 * @version V1.0
 */
@Controller("resource")
@Scope("prototype")
public class Resource extends ActionSupport implements ModelDriven<com.admintwo.model.Resources> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	@Autowired
	private MessagesService messagesService;

	@Autowired
	private ResourcesService resourcesService;

	@Autowired
	private KeepService keepService;

	@Autowired
	private DownloadService downloadService;

	private Resources resources = new Resources();
	private Keep keep = new Keep();

	@Override
	public Resources getModel() {
		return resources;
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
	 * 该方法用户上传图片
	 * 
	 * @throws Exception
	 */
	public void uploadImg() throws Exception {
		System.out.println("====resource/uploadImg文件名：" + fileFileName);
		// 文件在src下，直接用文件名
		ResourceBundle resource = ResourceBundle.getBundle("img");
		String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String realPath = resource.getString("resourceImg") + day + "/";

		// HttpServletRequest request = ServletActionContext.getRequest();
		// com.admintwo.model.User user = (com.admintwo.model.User)
		// request.getSession().getAttribute("user");
		// 页面返回信息
		Map<String, String> info = new HashMap<String, String>();
		String[] str = { ".jpg", ".jpeg", ".bmp", ".gif", ".png" };
		// 限定文件大小是4MB
		if (file == null || file.length() > 4194304) {
			info = new HashMap<String, String>();
			info.put("code", "1");
			info.put("msg", "请确保上传文件的大小不超过4M");
			returnJson(info);
			return;
		}
		for (String s : str) {
			if (fileFileName.toLowerCase().endsWith(s)) {
				// fileFileName = "user" + user.getId() + s;
				fileFileName = UUID.randomUUID() + s;
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
					info.put("code", "1");
					info.put("msg", "文件上传失败");
					returnJson(info);
					return;
				}
				// 文件进行尺寸调整以及图片大小压缩处理
				if (!".gif".equals(s)) {
					ToolsUtils.handlePicture(realPath + fileFileName, realPath + fileFileName, 688, 350);
				}
				// 图片路径
				String src = "res/images/resourceImg/" + day + "/" + fileFileName;
				info.put("code", "0");
				info.put("msg", "文件上传成功");
				info.put("src", src);

				/*
				 * 资源上传成功保存一条记录
				 */
				System.out.println("====resource/uploadImg上传文件：" + src);
				HttpServletRequest request = ServletActionContext.getRequest();
				com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
				int rows = messagesService.insertMessages(
						src + ",size:" + new DecimalFormat("0.00").format(file.length() / 1024.0) + "kb", 3,
						user.getEmail());
				if (rows == 1) {
					System.out.println("====resource/uploadImg上传文件消息添加成功");
				} else {
					System.out.println("====resource/uploadImg上传文件消息添加失败");
				}

				returnJson(info);
			}
		}
	}

	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 该方法用户上传压缩文件
	 * 
	 * @throws Exception
	 */
	public void uploadFile() throws Exception {
		System.out.println("====resource/uploadFile文件名：" + fileFileName);
		// 该name用于返回给页面显示文件名称
		String name = fileFileName;
		// 文件在src下，直接用文件名
		ResourceBundle resource = ResourceBundle.getBundle("img");
		String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String realPath = resource.getString("resourceFile") + day + "/";

		// HttpServletRequest request = ServletActionContext.getRequest();
		// com.admintwo.model.User user = (com.admintwo.model.User)
		// request.getSession().getAttribute("user");
		// 页面返回信息
		Map<String, String> info = new HashMap<String, String>();
		String[] str = { ".zip", ".rar" };
		// 限定文件大小是20MB
		if (file == null || file.length() > 20971520) {
			info = new HashMap<String, String>();
			info.put("code", "1");
			info.put("msg", "请确保上传文件的大小不超过20M");
			returnJson(info);
			return;
		}
		for (String s : str) {
			if (fileFileName.toLowerCase().endsWith(s)) {
				// fileFileName = "user" + user.getId() + s;
				fileFileName = uuid + s;
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
					info.put("code", "1");
					info.put("msg", "文件上传失败");
					returnJson(info);
					return;
				}
				// 文件路径
				String src = "res/images/resourceFile/" + day + "/" + fileFileName;
				info.put("code", "0");
				info.put("msg", "文件上传成功");
				info.put("src", src);
				info.put("title", name);
				/*
				 * 资源上传成功保存一条记录
				 */
				HttpServletRequest request = ServletActionContext.getRequest();
				com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
				int rows = messagesService.insertMessages(
						src + ",size:" + new DecimalFormat("0.00").format(file.length() / 1024.0 / 1024.0) + "M", 4,
						user.getEmail());
				if (rows == 1) {
					System.out.println("====resource/add上传文件消息添加成功");
				} else {
					System.out.println("====resource/add上传文件消息添加失败");
				}

				returnJson(info);
			}
		}
	}

	/**
	 * 该方法用于ajax分享代码
	 * 
	 * @throws Exception
	 */
	public void add() throws Exception {
		System.out.println("====resources/add分享代码：" + resources);
		boolean flag = resourcesService.insertResources(resources);
		if (flag) {
			System.out.println("====resources/add分享代码成功");
		} else {
			System.out.println("====resources/add分享代码失败");
		}
		returnJson(flag);
	}

	/**
	 * 该方法用于ajax根据资源id，获取资源所有信息
	 * 
	 * @throws Exception
	 */
	public void detail() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		int user_id;
		if (user == null) {
			user_id = 0;
		} else {
			user_id = user.getId();
		}
		System.out.println("====resource/detail资源id：" + resources.getId() + "，user用户id：" + user_id);
		com.admintwo.model.pojo.ResourceUserLabels resourceUserLabels = resourcesService
				.getResourceUserLabelsByid(resources.getId(), user_id);
		System.out.println("====resource/detail返回值：" + resourceUserLabels);
		returnJson(resourceUserLabels);
	}

	private int resources_id;

	public int getResources_id() {
		return resources_id;
	}

	public void setResources_id(int resources_id) {
		this.resources_id = resources_id;
	}

	/**
	 * 该方法用于ajax资源收藏
	 * 
	 * @throws Exception
	 */
	public void shoucang() throws Exception {
		System.out.println("====resource/shoucang资源id：" + resources_id);
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag;
		int user_id;
		if (user == null) {
			flag = false;
			returnJson(flag);
			return;
		} else {
			user_id = user.getId();
		}
		keep.setUser_id(user_id);
		keep.setResources_id(resources_id);
		flag = keepService.insertKeep(keep);
		returnJson(flag);
	}

	/**
	 * 该方法用于ajax取消收藏
	 * 
	 * @throws Exception
	 */
	public void deleteshoucang() throws Exception {
		System.out.println("====resource/deleteshoucang资源id：" + resources_id);
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag;
		int user_id;
		if (user == null) {
			flag = false;
			returnJson(flag);
			return;
		} else {
			user_id = user.getId();
		}
		keep.setUser_id(user_id);
		keep.setResources_id(resources_id);
		flag = keepService.deleteKeep(keep);
		returnJson(flag);
	}

	/**
	 * 资源积分和当前用户积分比较
	 * 
	 * @throws Exception
	 */
	public void jifen() throws Exception {
		System.out.println("====resource/jifen资源id：" + resources_id);
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		Map<String, String> map = new HashMap<String, String>();
		// 返回信息：code为1不能下载，code为0可以通过积分下载，code为2已下载可直接下载
		// 首先判断用户是否登录
		if (user == null) {
			System.out.println("====resource/jifen用户未登录");
			map.put("code", "1");
			map.put("msg", "尚未登录");
			returnJson(map);
			return;
		} else {
			// 判断用户是否下载过该积分，若下载过，可不用扣除积分直接下载
			com.admintwo.model.Download download = new com.admintwo.model.Download();
			download.setUser_id(user.getId());
			download.setResources_id(resources_id);
			int rows = downloadService.getDownload(download);
			if (rows > 0) {
				// 用户下载过此资源
				System.out.println("====resource/jifen用户下载过此资源");
				map.put("code", "2");
				map.put("msg", "您已下载过此资源，可免费下载");
				returnJson(map);
				return;
			} else {
				// 用户没有下载过该资源，判断用户积分是否足够
				com.admintwo.model.Resources resources = resourcesService.getResourceByid(resources_id);
				if (user.getJifen() < resources.getJifen()) {
					// 积分不足
					System.out.println("====resource/jifen用户积分不足");
					map.put("code", "1");
					map.put("msg", "积分不足，我的积分：" + user.getJifen());
					returnJson(map);
					return;
				} else {
					// 积分足够
					System.out.println("====resource/jifen用户积分足够，可以下载");
					map.put("code", "0");
					map.put("msg", "下载需要扣除" + resources.getJifen() + "积分，是否确认下载？");
					map.put("url", resources.getUrl());
					returnJson(map);
					return;
				}
			}
		}
	}

	/**
	 * 下载资源。通过积分下载、已下载可直接下载。
	 * 
	 * @throws Exception
	 */
	public String download() throws Exception {
		// 根据传过来的资源id获取资源信息
		System.out.println("====resource/download资源id：" + resources_id);
		Resources resources = resourcesService.getResourceByid(resources_id);

		// 获取当前session用户
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");

		// 判断用户是否下载过该积分，若下载过，可不用扣除积分直接下载
		com.admintwo.model.Download download = new com.admintwo.model.Download();
		download.setUser_id(user.getId());
		download.setResources_id(resources_id);
		int rows = downloadService.getDownload(download);
		if (rows == 0) {
			// 用户没有下载过此资源，需要扣除当前用户积分，增加资源用户积分，并且增加一条下载记录
			System.out.println("====resource/download()用户没有下载过此资源");
			// 没下载过。操作：下载者扣除积分，资源拥有者增加积分，并保存一条记录
			boolean flag = false;
			if (user.getJifen() < resources.getJifen()) {
				// 积分不足
			} else {
				flag = resourcesService.updateUserJifenByDownloadResources(user.getId(), resources.getUser_id(),
						resources.getJifen());
			}
			if (flag) {
				// 积分扣除成功，重新保存下当前session用户
				user = userService.getUserByEmail(user.getEmail());
				request.getSession().setAttribute("user", user);
				// 积分扣除和增加成功，保存一条下载记录
				rows = downloadService.insertDownload(download);
				if (rows > 0) {
					System.out.println("====resource/download下载记录成功");
					// 保存两条消息，用于通知
					// 1 当前用户扣除积分消息
					messagesService.insertMessages(resources.getJifen()
							+ "积分用于下载：<a style=\"color:#4F99CF;\" target=\"_blank\" href='resource_detail?id="
							+ resources.getId() + "'>" + resources.getName() + "</a>", 5, user.getEmail());
					// 2 资源所属用户增加积分消息
					messagesService.insertMessages(
							"账户增加" + resources.getJifen()
									+ "积分，<a style=\"color:#4F99CF;\" target=\"_blank\" href='user_home?id="
									+ user.getId() + "'>" + user.getName()
									+ "</a>下载了您的代码：<a style=\"color:#4F99CF;\" target=\"_blank\" href='resource_detail?id="
									+ resources.getId() + "'>" + resources.getName() + "</a>",
							6, userService.getUserById(resources.getUser_id()).getEmail());
				} else {
					System.out.println("====resource/download下载记录失败");
				}

				System.out.println("====resource/download文件路径：" + "/home/www/web/ROOT/" + resources.getUrl());
				// 不管判断用户是否下载，用户都需要下载。已下载过可以直接下载
				HttpServletResponse response = ServletActionContext.getResponse();
				// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
				response.setContentType("multipart/form-data");
				// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
				response.setHeader("Content-Disposition",
						"attachment;fileName=" + resources.getUrl().substring(resources.getUrl().lastIndexOf("/") + 1));
				BufferedOutputStream out = null;
				InputStream is = null;
				try {
					// 3.通过response获取ServletOutputStream对象(out)
					out = new BufferedOutputStream(response.getOutputStream());
					is = new BufferedInputStream(
							new FileInputStream(new File("/home/www/web/ROOT/" + resources.getUrl())));
					byte[] content = new byte[1024];
					int len = 0;
					while ((len = is.read(content)) > 0) {
						out.write(content, 0, len);
					}
					out.flush();
					out.close();
				} catch (IOException e) {
					System.out.println("====resource/download异常");
				} finally {
					try {
						if (is != null) {
							is.close();
						}
						if (out != null) {
							out.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} else {
				System.out.println("====ERROR：resource/download用户没有下载过此资源，修改积分出错");
			}
		} else {
			System.out.println("====resource/download文件路径：" + "/home/www/web/ROOT/" + resources.getUrl());
			// 不管判断用户是否下载，用户都需要下载。已下载过可以直接下载
			HttpServletResponse response = ServletActionContext.getResponse();
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("multipart/form-data");
			// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + resources.getUrl().substring(resources.getUrl().lastIndexOf("/") + 1));
			BufferedOutputStream out = null;
			InputStream is = null;
			try {
				// 3.通过response获取ServletOutputStream对象(out)
				out = new BufferedOutputStream(response.getOutputStream());
				is = new BufferedInputStream(new FileInputStream(new File("/home/www/web/ROOT/" + resources.getUrl())));
				byte[] content = new byte[1024];
				int len = 0;
				while ((len = is.read(content)) > 0) {
					out.write(content, 0, len);
				}
				out.flush();
				out.close();
			} catch (IOException e) {
				System.out.println("====resource/download异常");
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (out != null) {
						out.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return NONE;
	}

	/**
	 * 该方法ajax获取首页所有资源
	 * 
	 * @throws Exception
	 */
	public void index() throws Exception {
		List<IndexHtmlResources> ihrs = resourcesService.getIndexHtmlResources(0, 60);
		System.out.println("====resource/index获取首页资源信息：" + ihrs);
		returnJson(ihrs);
	}

	/**
	 * 该方法ajax获取某个用户分享资源信息，前25条
	 * 
	 * @throws Exception
	 */
	public void getResourcesInfoByUser_id() throws Exception {
		System.out.println("====resource/getResourcesInfoByUser_id:user_id值为：" + resources.getUser_id());
		List<ResourceInfo> ris = resourcesService.getResourcesInfoByUser_id(resources.getUser_id());
		System.out.println("====resource/getResourcesInfoByUser_id获取用户下分享资源代码：" + ris);
		returnJson(ris);
	}

	/**
	 * 该方法ajax获取某个用户分享资源信息，所有条
	 * 
	 * @throws Exception
	 */
	public void getUser_indexResourcesInfoByUser_id() throws Exception {
		System.out.println("====resource/getUser_indexResourcesInfoByUser_id值为：" + resources.getUser_id());
		List<ResourceInfo> ris = resourcesService.getUser_indexResourcesInfoByUser_id(resources.getUser_id());
		System.out.println("====resource/getUser_indexResourcesInfoByUser_id获取用户下分享资源代码：" + ris);
		returnJson(ris);
	}

	/**
	 * 该方法ajax获取某个用户评论、下载、收藏资源信息
	 * 
	 * @throws Exception
	 */
	public void getResourcesSortInfoByUser_id() throws Exception {
		System.out.println("====resource/getResourcesSortInfoByUser_id:user_id值为：" + resources.getUser_id());
		List<ResourceSortInfo> ris = resourcesService.getResourcesSortInfoByUser_id(resources.getUser_id());
		System.out.println("====resource/getResourcesSortInfoByUser_id获取用户下评论、下载、收藏资源代码：" + ris);
		returnJson(ris);
	}

	/**
	 * 该方法ajax获取资源总个数
	 * 
	 * @throws Exception
	 */
	public void getAllResourcesNum() throws Exception {
		int num = resourcesService.getAllResourcesNum();
		System.out.println("====resource/getAllResourcesNum总个数：" + num);
		returnJson(num);
	}

	private int page;
	private int rows;

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 该方法ajax分页获取所有资源
	 * 
	 * @throws Exception
	 */
	public void list() throws Exception {
		System.out.println("====resource/list页数page：" + page + "，每页个数rows：" + rows);
		List<IndexHtmlResources> ihrs = resourcesService.getIndexHtmlResources((page - 1) * rows, rows);
		System.out.println("====resource/index获取分页资源信息：" + ihrs);
		returnJson(ihrs);
	}

	/**
	 * 该方法获取第一个资源页内容
	 * 
	 * @throws Exception
	 */
	public void getFirstResourcesList() throws Exception {
		List<Resources> rslist = resourcesService.getFirstResourcesList();
		System.out.println("====resource/getFirstResourcesList获取第一个资源详细信息：" + rslist);
		returnJson(rslist);
	}

	/**
	 * 该方法搜索资源
	 * 
	 * @throws Exception
	 */
	public void search() throws Exception {
		System.out.println("====resource/search搜索资源传递参数：" + resources);
		List<IndexHtmlResources> ihrs = resourcesService.getResourcesListBySearch(resources);
		System.out.println("====resource/search搜索资源：" + ihrs);
		returnJson(ihrs);
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
