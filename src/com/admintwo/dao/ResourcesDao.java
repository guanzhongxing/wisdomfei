package com.admintwo.dao;

import java.util.List;

import com.admintwo.model.Resources;
import com.admintwo.model.pojo.IndexHtmlResources;
import com.admintwo.model.pojo.ResourceInfo;
import com.admintwo.model.pojo.ResourceSortInfo;
import com.admintwo.model.pojo.ResourceUserLabels;

public interface ResourcesDao {

	// 分享资源。1：成功，0：失败
	public int insertResources(Resources resources);

	// 根据资源id和用户id，查询资源相关信息。
	public ResourceUserLabels getResourceUserLabelsByid(int id, int user_id);

	// 根据资源id，查询资源
	public Resources getResourceByid(int id);

	// 根据下载修改用户积分，参数：当前用户id，资源所属用户id，修改积分数
	public boolean updateUserJifenByDownloadResources(int user_id, int r_user_id, int jifen);

	// 获取首页资源
	public List<IndexHtmlResources> getIndexHtmlResources(int offset, int rows);

	// 获取某个用户分享的资源列表，前25条
	public List<ResourceInfo> getResourcesInfoByUser_id(int user_id);

	// 获取某个用户分享的资源列表，所有条
	List<ResourceInfo> getUser_indexResourcesInfoByUser_id(int user_id);

	// 获取某个用户评论、下载、收藏的资源列表
	public List<ResourceSortInfo> getResourcesSortInfoByUser_id(int user_id);

	// 获取资源总个数
	public int getAllResourcesNum();

	// 获取第一个资源详细内容s
	public List<Resources> getFirstResourcesList();

	// 搜索资源
	public List<IndexHtmlResources> getResourcesListBySearch(Resources resources);

}
