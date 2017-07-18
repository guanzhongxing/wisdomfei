package com.admintwo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.ZtreeDao;
import com.admintwo.model.Ztree;
import com.admintwo.model.pojo.KeepTree;
import com.admintwo.model.pojo.KnowledgeTree;
import com.admintwo.model.pojo.SimpleZtree;
import com.admintwo.model.pojo.TreeLaba;
import com.admintwo.model.pojo.TreesForFenye;
import com.admintwo.model.pojo.WaitTree;
import com.admintwo.service.ZtreeService;

@Component("ztreeServiceImpl")
public class ZtreeServiceImpl implements ZtreeService {

	@Autowired
	private ZtreeDao ztreeDao;

	@Override
	public List<KnowledgeTree> getZtree() {
		return ztreeDao.getZtree();
	}

	@Override
	public int getNumber(int sort, String keyword) {
		return ztreeDao.getNumber(sort, keyword);
	}

	@Override
	public List<SimpleZtree> getSimpleZtree() {
		return ztreeDao.getSimpleZtree();
	}

	@Override
	public boolean insertTree(Ztree ztree) {
		return ztreeDao.insertTree(ztree);
	}

	@Override
	public int getWaitNum() {
		return ztreeDao.getWaitNum();
	}

	@Override
	public List<WaitTree> getWaitTrees() {
		return ztreeDao.getWaitTrees();
	}

	@Override
	public boolean shenheTree(int treeid, int flag) {
		return ztreeDao.shenheTree(treeid, flag);
	}

	@Override
	public List<TreesForFenye> getTreesByFenye(int sort, String keyword, int start, int maxResult) {
		return ztreeDao.getTreesByFenye(sort, keyword, start, maxResult);
	}

	@Override
	public boolean addNumById(int numid) {
		return ztreeDao.addNumById(numid);
	}

	@Override
	public List<TreeLaba> getToday() {
		return ztreeDao.getToday();
	}

	@Override
	public List<KeepTree> getKeepTreeByUser_id(int id) {
		return ztreeDao.getKeepTreeByUser_id(id);
	}

}
