package com.admintwo.service;

import java.util.List;

import com.admintwo.model.pojo.KeepTree;
import com.admintwo.model.pojo.KnowledgeTree;
import com.admintwo.model.pojo.SimpleZtree;
import com.admintwo.model.pojo.TreeLaba;
import com.admintwo.model.pojo.TreesForFenye;
import com.admintwo.model.pojo.WaitTree;

public interface ZtreeService {

	// 获取左侧结点树
	List<KnowledgeTree> getZtree();

	// 获取总数量
	public int getNumber(int sort, String keyword);

	// 获取简单树
	List<SimpleZtree> getSimpleZtree();

	// 插入一条数据
	boolean insertTree(com.admintwo.model.Ztree ztree);

	// 获取待审核总数量
	int getWaitNum();

	// 获得待审核知识f
	List<WaitTree> getWaitTrees();

	// 审核树节点
	boolean shenheTree(int treeid, int flag);

	// 分页获取数据
	List<TreesForFenye> getTreesByFenye(int sort, String keyword, int start, int maxResult);

	// 增加点赞数
	boolean addNumById(int numid);

	// 获取今日公告
	List<TreeLaba> getToday();

	// 获取用户收藏的知识
	List<KeepTree> getKeepTreeByUser_id(int id);

}
