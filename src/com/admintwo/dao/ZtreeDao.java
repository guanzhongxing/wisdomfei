package com.admintwo.dao;

import java.util.List;

import com.admintwo.model.Ztree;
import com.admintwo.model.pojo.KeepTree;
import com.admintwo.model.pojo.KnowledgeTree;
import com.admintwo.model.pojo.SimpleZtree;
import com.admintwo.model.pojo.TreeLaba;
import com.admintwo.model.pojo.TreesForFenye;
import com.admintwo.model.pojo.WaitTree;

public interface ZtreeDao {

	// 获取左侧结点树
	public List<KnowledgeTree> getZtree();

	// 获取总数量
	public int getNumber(int sort, String keyword);

	// 获取简单树
	public List<SimpleZtree> getSimpleZtree();

	// 插入一条数据
	public boolean insertTree(Ztree ztree);

	// 待审核数量
	public int getWaitNum();

	// 获取待审核节点
	public List<WaitTree> getWaitTrees();

	// 审核树节点
	public boolean shenheTree(int treeid, int flag);

	// 分页获取数据
	public List<TreesForFenye> getTreesByFenye(int sort, String keyword, int start, int maxResult);

	// 增加点赞数
	public boolean addNumById(int numid);

	// 获取今日公告
	public List<TreeLaba> getToday();

	// 获取用户收藏知识
	public List<KeepTree> getKeepTreeByUser_id(int id);
}
