package com.admintwo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admintwo.dao.ResourcesDao;
import com.admintwo.model.Resources;
import com.admintwo.model.pojo.IndexHtmlResources;
import com.admintwo.model.pojo.ResourceInfo;
import com.admintwo.model.pojo.ResourceSortInfo;
import com.admintwo.model.pojo.ResourceUserLabels;
import com.admintwo.service.ResourcesService;

@Component("ResourcesServiceImpl")
public class ResourcesServiceImpl implements ResourcesService {

	@Autowired
	private ResourcesDao resourcesDao;

	@Override
	public boolean insertResources(Resources resources) {
		int rows = resourcesDao.insertResources(resources);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public ResourceUserLabels getResourceUserLabelsByid(int id, int user_id) {
		ResourceUserLabels resourceUserLabels = resourcesDao.getResourceUserLabelsByid(id, user_id);
		return resourceUserLabels;
	}

	@Override
	public Resources getResourceByid(int id) {
		Resources resources = resourcesDao.getResourceByid(id);
		return resources;
	}

	@Override
	public boolean updateUserJifenByDownloadResources(int user_id, int r_user_id, int jifen) {
		boolean flag = resourcesDao.updateUserJifenByDownloadResources(user_id, r_user_id, jifen);
		return flag;
	}

	@Override
	public List<IndexHtmlResources> getIndexHtmlResources(int offset, int rows) {
		return resourcesDao.getIndexHtmlResources(offset, rows);
	}

	@Override
	public List<ResourceInfo> getResourcesInfoByUser_id(int user_id) {
		return resourcesDao.getResourcesInfoByUser_id(user_id);
	}

	@Override
	public List<ResourceInfo> getUser_indexResourcesInfoByUser_id(int user_id) {
		return resourcesDao.getUser_indexResourcesInfoByUser_id(user_id);
	}

	@Override
	public List<ResourceSortInfo> getResourcesSortInfoByUser_id(int user_id) {
		return resourcesDao.getResourcesSortInfoByUser_id(user_id);
	}

	@Override
	public int getAllResourcesNum() {
		return resourcesDao.getAllResourcesNum();
	}

	@Override
	public List<Resources> getFirstResourcesList() {
		return resourcesDao.getFirstResourcesList();
	}

	@Override
	public List<IndexHtmlResources> getResourcesListBySearch(Resources resources) {
		return resourcesDao.getResourcesListBySearch(resources);
	}

}
