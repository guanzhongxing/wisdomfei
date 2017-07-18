package com.admintwo.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.admintwo.model.pojo.KeepTree;
import com.admintwo.model.pojo.KnowledgeTree;
import com.admintwo.model.pojo.SimpleZtree;
import com.admintwo.model.pojo.TreeLaba;
import com.admintwo.model.pojo.TreesForFenye;
import com.admintwo.model.pojo.WaitTree;
import com.admintwo.service.ZtreeService;
import com.admintwo.util.GsonUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Title: Ztree
 * @Description: 该类用于处理知识库
 * @author 徐江飞
 * @date 2017年3月18日 下午4:29:14
 * @version V1.0
 */
@Controller("ztree")
@Scope("prototype")
public class Ztree extends ActionSupport implements ModelDriven<com.admintwo.model.Ztree> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ZtreeService ztreeService;

	private com.admintwo.model.Ztree ztree = new com.admintwo.model.Ztree();

	@Override
	public com.admintwo.model.Ztree getModel() {
		return ztree;
	}

	/**
	 * 该方法获取左侧根节点树
	 * 
	 * @throws Exception
	 */
	public void getZtree() throws Exception {
		List<KnowledgeTree> ztrees = ztreeService.getZtree();
		System.out.println("====ztree/getztree获取首页左侧结点树：" + ztrees);
		returnJson(GsonUtils.toJson(ztrees).toString());
	}

	/**
	 * 该方法获取简单节点树
	 * 
	 * @throws Exception
	 */
	public void getSimpleZtree() throws Exception {
		List<SimpleZtree> ztrees = ztreeService.getSimpleZtree();
		System.out.println("====ztree/getztree获取分享知识结点树：" + ztrees);
		returnJson(GsonUtils.toJson(ztrees).toString());
	}

	private int sort;
	private String keyword;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 该方法获取数量
	 * 
	 * @throws Exception
	 */
	public void getNumber() throws Exception {
		System.out.println("====ztree/getNumber获取分类(0：首页加载、1：树节点、2：关键词)和关键参数：" + sort + "," + keyword);
		int num = ztreeService.getNumber(sort, keyword);
		System.out.println("====ztree/getNumber获取总数量：" + num);
		returnJson(num);
	}

	/**
	 * 该方法获取待审核数量
	 * 
	 * @throws Exception
	 */
	public void getWaitNum() throws Exception {
		int num = ztreeService.getWaitNum();
		System.out.println("====ztree/getWaitNum获取待审核数量：" + num);
		returnJson(num);
	}

	/**
	 * 该方法插入一条ztree数据
	 * 
	 * @throws Exception
	 */
	public void insertTree() throws Exception {
		System.out.println("====ztree/insertTree需要插入的一条数据：" + ztree);
		boolean flag = ztreeService.insertTree(ztree);
		System.out.println("====ztree/insertTree插入是否成功？：" + flag);
		returnJson(flag);
	}

	/**
	 * 该方法获取待审核节点
	 * 
	 * @throws Exception
	 */
	public void getWaitTrees() throws Exception {
		List<WaitTree> ztrees = ztreeService.getWaitTrees();
		System.out.println("====ztree/getztree获取待审核知识结点树：" + ztrees);
		returnJson(ztrees);
	}

	private int treeid;
	private int flag;

	public int getTreeid() {
		return treeid;
	}

	public void setTreeid(int treeid) {
		this.treeid = treeid;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * 该方法获取待审核节点
	 * 
	 * @throws Exception
	 */
	public void shenheTree() throws Exception {
		System.out.println("====ztree/shenheTree审核知识id：" + treeid + ",0代表通过，1不通过：" + flag);
		boolean result = ztreeService.shenheTree(treeid, flag);
		System.out.println("====ztree/shenheTree结果：" + result);
		returnJson(result);
	}

	private int maxResult;
	private int page;

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 该方法通过分页获取数据
	 * 
	 * @throws Exception
	 */
	public void getTreesByFenye() throws Exception {
		System.out.println("====ztree/getTreesByFenye获取数据传递参数，分类sort：" + sort + "，每页展示maxResult：" + maxResult
				+ "，当前页page：" + page + "，关键参数keyword：" + keyword);
		/* 偏移量 */
		int start = (page - 1) * maxResult;
		List<TreesForFenye> result = ztreeService.getTreesByFenye(sort, keyword, start, maxResult);
		System.out.println("====ztree/getTreesByFenye获取首页数据：" + result);
		returnJson(result);
	}

	private int numid;

	public int getNumid() {
		return numid;
	}

	public void setNumid(int numid) {
		this.numid = numid;
	}

	/**
	 * 该方法增加收藏数
	 * 
	 * @throws Exception
	 */
	public void addNumById() throws Exception {
		System.out.println("====ztree/addNumById增加收藏数id：" + numid);
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		boolean flag = false;
		if (user != null) {
			// 说明已经登录了，可以收藏知识
			// 增加一条收藏记录，保存在keepztree表中，并且收藏数+1.这个操作在ztreeDaoImpl中用事务操作
			flag = ztreeService.addNumById(numid);
		}
		returnJson(flag);
	}

	/**
	 * 该方法获取今日公告
	 * 
	 * @throws Exception
	 */
	public void getToday() throws Exception {
		List<TreeLaba> tls = ztreeService.getToday();
		System.out.println("====tree/getToday获取今日公告：" + tls);
		returnJson(tls);
	}

	/**
	 * 该方法获取用户收藏的知识
	 * 
	 * @throws Exception
	 */
	public void getKeepTreeByUser_id() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		if (user != null) {
			List<KeepTree> kpts = ztreeService.getKeepTreeByUser_id(user.getId());
			System.out.println("====tree/getKeepTreeByUser_id获取用户收藏的知识：" + kpts);
			returnJson(kpts);
		} else {
			returnJson(false);
		}
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
