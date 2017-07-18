package com.admintwo.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.admintwo.dao.ResourcesDao;
import com.admintwo.model.Labels;
import com.admintwo.model.Resources;
import com.admintwo.model.User;
import com.admintwo.model.pojo.IndexHtmlResources;
import com.admintwo.model.pojo.ResourceInfo;
import com.admintwo.model.pojo.ResourceSortInfo;
import com.admintwo.model.pojo.ResourceUserLabels;
import com.admintwo.model.pojo.UserLabels;
import com.admintwo.util.ToolsUtils;

@Component("ResourcesDaoImpl")
public class ResourcesDaoImpl implements ResourcesDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertResources(Resources r) {
		String sql = "insert into resources(name,sort,jifen,label,description,title,url,urlpwd,user_id) values(?,?,?,?,?,?,?,?,?)";
		int rows = jdbcTemplate.update(sql, r.getName(), r.getSort(), r.getJifen(), r.getLabel(), r.getDescription(),
				r.getTitle(), r.getUrl(), r.getUrlpwd(), r.getUser_id());
		return rows;
	}

	@Override
	public ResourceUserLabels getResourceUserLabelsByid(int id, int user_id) {
		String sql = "select r.id as r_id,r.name as r_name,r.jifen as r_jifen,r.description,r.url,r.nowtime as r_nowtime,r.isfirst,r.isjing,r.isyuan, ";
		sql += "u.id as u_id,u.name as u_name,u.qq as u_qq,u.jifen as u_jifen,u.img,l.name as label, ";
		sql += "if(isnull(k.id),0,1) as isshoucang, ";
		sql += "ifnull(kp.res_keep_num,0) as res_keep_num, ";
		sql += "ifnull(c.res_comments_num,0) as res_comments_num, ";
		sql += "ifnull(d.res_download_num,0) as res_download_num ";
		sql += "from resources r ";
		sql += "left join user u on u.id=r.user_id ";
		sql += "left join labels l on l.id=u.label ";
		sql += "left join keep k on k.resources_id=r.id and k.user_id=? ";
		sql += "left join (select resources_id,count(kp.id) as res_keep_num from resources r,keep kp where r.id=? and r.id=kp.resources_id)kp on r.id=kp.resources_id ";
		sql += "left join (select resources_id,count(c.id) as res_comments_num from resources r,comments c where r.id=? and r.id=c.resources_id)c on r.id=c.resources_id ";
		sql += "left join (select resources_id,count(d.id) as res_download_num from resources r,download d where r.id=? and r.id=d.resources_id)d on r.id=d.resources_id ";
		sql += "where r.id=? and r.isfriend=0 ";
		System.out.println("====根据资源id查询资源详细信息sql：" + sql);
		ResourceUserLabels resourceUserLabels = jdbcTemplate.queryForObject(sql, new RowMapper<ResourceUserLabels>() {
			@Override
			public ResourceUserLabels mapRow(ResultSet rs, int num) throws SQLException {
				int r_id = rs.getInt("r_id");
				String r_name = rs.getString("r_name");
				int r_jifen = rs.getInt("r_jifen");
				String description = rs.getString("description");
				String url = rs.getString("url");
				Timestamp nowtime = rs.getTimestamp("r_nowtime");
				Date date = new Date();
				try {
					date = nowtime;
				} catch (Exception e) {
					System.out.println("====ResourcesDaoImpl.getResourceUserLabelsByid()方法中Timestamp转Date出错");
				}
				String r_nowtime = ToolsUtils.getTimeFormatText(date);
				int isfirst = rs.getInt("isfirst");
				int isjing = rs.getInt("isjing");
				int isyuan = rs.getInt("isyuan");
				int u_id = rs.getInt("u_id");
				String u_name = rs.getString("u_name");
				String u_qq = rs.getString("u_qq");
				int u_jifen = rs.getInt("u_jifen");
				String img = rs.getString("img");
				String label = rs.getString("label");
				int isshoucang = rs.getInt("isshoucang");
				int res_keep_num = rs.getInt("res_keep_num");
				int res_comments_num = rs.getInt("res_comments_num");
				int res_download_num = rs.getInt("res_download_num");

				ResourceUserLabels resourceUserLabels = new ResourceUserLabels();

				Resources resources = new Resources();
				resources.setId(r_id);
				resources.setName(r_name);
				resources.setJifen(r_jifen);
				resources.setDescription(description);
				resources.setUrl(url);
				resourceUserLabels.setR_nowtime(r_nowtime);
				resources.setIsfirst(isfirst);
				resources.setIsjing(isjing);
				resources.setIsyuan(isyuan);
				resourceUserLabels.setResources(resources);

				UserLabels userLabels = new UserLabels();
				User user = new User();
				user.setId(u_id);
				user.setName(u_name);
				user.setQq(u_qq);
				user.setJifen(u_jifen);
				user.setImg(img);
				Labels labels = new Labels();
				labels.setName(label);
				userLabels.setUser(user);
				userLabels.setLabels(labels);
				resourceUserLabels.setUserLabels(userLabels);

				resourceUserLabels.setWords(ToolsUtils.IkAnalyzer(r_name));
				resourceUserLabels.setIsshoucang(isshoucang);
				resourceUserLabels.setRes_keep_num(res_keep_num);
				resourceUserLabels.setRes_comments_num(res_comments_num);
				resourceUserLabels.setRes_download_num(res_download_num);
				return resourceUserLabels;
			}
		}, user_id, id, id, id, id);
		return resourceUserLabels;
	}

	@Override
	public Resources getResourceByid(int id) {
		String sql = "select id,name,sort,jifen,label,description,title,url,urlpwd,nowtime,isfirst,isjing,isyuan,isfriend,user_id from resources where id = ?";
		Resources resources = jdbcTemplate.queryForObject(sql, new RowMapper<Resources>() {
			@Override
			public Resources mapRow(ResultSet rs, int num) throws SQLException {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int sort = rs.getInt("sort");
				int jifen = rs.getInt("jifen");
				int label = rs.getInt("label");
				String description = rs.getString("description");
				String title = rs.getString("title");
				String url = rs.getString("url");
				String urlpwd = rs.getString("urlpwd");
				Timestamp d = rs.getTimestamp("nowtime");
				Date date = new Date();
				try {
					date = d;
				} catch (Exception e) {
					System.out.println("====ResourcesDaoImpl.getResourcesByid()方法中Timestamp转Date出错");
				}
				String nowtime = ToolsUtils.getTimeFormatText(date);
				int isfirst = rs.getInt("isfirst");
				int isjing = rs.getInt("isjing");
				int isyuan = rs.getInt("isyuan");
				int isfriend = rs.getInt("isfriend");
				int user_id = rs.getInt("user_id");

				Resources resources = new Resources();
				resources.setId(id);
				resources.setName(name);
				resources.setSort(sort);
				resources.setJifen(jifen);
				resources.setLabel(label);
				resources.setDescription(description);
				resources.setTitle(title);
				resources.setUrl(url);
				resources.setUrlpwd(urlpwd);
				resources.setNowtime(nowtime);
				resources.setIsfirst(isfirst);
				resources.setIsjing(isjing);
				resources.setIsyuan(isyuan);
				resources.setIsfriend(isfriend);
				resources.setUser_id(user_id);

				return resources;
			}
		}, id);
		return resources;
	}

	@Override
	@Transactional
	public boolean updateUserJifenByDownloadResources(int user_id, int r_user_id, int jifen) {
		// 扣除下载用户的积分
		String sql = "update user set jifen = jifen-? where id = ?";
		int rows = jdbcTemplate.update(sql, jifen, user_id);
		if (rows > 0) {
			// 增加资源拥有者的积分
			sql = "update user set jifen = jifen+? where id = ?";
			int result = jdbcTemplate.update(sql, jifen, r_user_id);
			if (result > 0) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<IndexHtmlResources> getIndexHtmlResources(int offset, int rows) {
		String sql = "select r.id as r_id,r.sort as r_sort,r.name as r_name,r.urlpwd as r_urlpwd,l2.name as r_label,r.label as r_label_num,r.nowtime as r_nowtime,r.isfirst,r.isjing,r.isyuan, ";
		sql += "u.id as u_id,u.name as u_name,l1.name as u_label,u.img, ";
		sql += "ifnull(c.res_comments_num,0) as res_comments_num, ";
		sql += "ifnull(d.res_download_num,0) as res_download_num, ";
		sql += "ifnull(kp.res_keep_num,0) as res_keep_num ";
		sql += "from resources r ";
		sql += "left join user u on u.id=r.user_id ";
		sql += "left join labels l1 on l1.id=u.label ";
		sql += "left join labels l2 on l2.id=r.label ";
		sql += "left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on r.id=kp.resources_id ";
		sql += "left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on r.id=c.resources_id ";
		sql += "left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on r.id=d.resources_id ";
		sql += "where r.isfriend=0 or r.id=1 ";
		sql += "order by r.id=1 desc,r.nowtime desc limit ?,? ";

		List<IndexHtmlResources> ihrs = (List<IndexHtmlResources>) jdbcTemplate.query(sql,
				new Object[] { offset, rows }, new ResultSetExtractor() {
					public List<IndexHtmlResources> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<IndexHtmlResources> ihrs = new ArrayList<IndexHtmlResources>();

						while (rs.next()) {
							IndexHtmlResources ihr = new IndexHtmlResources();

							int r_id = rs.getInt("r_id");
							int r_sort = rs.getInt("r_sort");
							String r_name = rs.getString("r_name");
							String r_urlpwd = rs.getString("r_urlpwd");
							String r_label = rs.getString("r_label");
							int r_label_num = rs.getInt("r_label_num");

							Timestamp d = rs.getTimestamp("r_nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out.println("====ResourcesDaoImpl.getIndexHtmlResources()方法中Timestamp转Date出错");
							}
							String r_nowtime = ToolsUtils.getTimeFormatText(date);

							int isfirst = rs.getInt("isfirst");
							int isjing = rs.getInt("isjing");
							int isyuan = rs.getInt("isyuan");

							int u_id = rs.getInt("u_id");
							String u_name = rs.getString("u_name");
							String u_label = rs.getString("u_label");
							String img = rs.getString("img");

							int res_comments_num = rs.getInt("res_comments_num");
							int res_download_num = rs.getInt("res_download_num");
							int res_keep_num = rs.getInt("res_keep_num");

							ihr.setR_id(r_id);
							ihr.setR_sort(r_sort);
							ihr.setR_name(r_name);
							ihr.setR_urlpwd(r_urlpwd);
							;

							ihr.setR_label(r_label);
							ihr.setR_label_num(r_label_num);
							ihr.setR_nowtime(r_nowtime);
							ihr.setIsfirst(isfirst);
							ihr.setIsjing(isjing);
							ihr.setIsyuan(isyuan);
							ihr.setU_id(u_id);
							ihr.setU_name(u_name);
							ihr.setU_label(u_label);
							ihr.setImg(img);
							ihr.setRes_comments_num(res_comments_num);
							ihr.setRes_download_num(res_download_num);
							ihr.setRes_keep_num(res_keep_num);

							ihrs.add(ihr);
						}
						return ihrs;
					}
				});
		return ihrs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ResourceInfo> getResourcesInfoByUser_id(int user_id) {
		String sql = "select r.id,r.name,r.nowtime,r.isjing,r.isyuan, ";
		sql += "ifnull(c.res_comments_num,0) as res_comments_num, ";
		sql += "ifnull(d.res_download_num,0) as res_download_num, ";
		sql += "ifnull(kp.res_keep_num,0) as res_keep_num ";
		sql += "from resources r ";
		sql += "left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on r.id=kp.resources_id ";
		sql += "left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on r.id=c.resources_id ";
		sql += "left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on r.id=d.resources_id ";
		sql += "where user_id = ? order by r.nowtime desc limit 25 ";

		List<ResourceInfo> ris = (List<ResourceInfo>) jdbcTemplate.query(sql, new Object[] { user_id },
				new ResultSetExtractor() {
					public List<ResourceInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<ResourceInfo> ris = new ArrayList<ResourceInfo>();

						while (rs.next()) {
							ResourceInfo ri = new ResourceInfo();

							int id = rs.getInt("id");
							String name = rs.getString("name");

							Timestamp d = rs.getTimestamp("nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out.println("====ResourcesDaoImpl.getResourcesByUser_id()方法中Timestamp转Date出错");
							}
							String nowtime = ToolsUtils.getTimeFormatText(date);

							int isjing = rs.getInt("isjing");
							int isyuan = rs.getInt("isyuan");

							int res_comments_num = rs.getInt("res_comments_num");
							int res_download_num = rs.getInt("res_download_num");
							int res_keep_num = rs.getInt("res_keep_num");

							ri.setId(id);
							ri.setName(name);
							ri.setNowtime(nowtime);
							ri.setIsjing(isjing);
							ri.setIsyuan(isyuan);
							ri.setRes_comments_num(res_comments_num);
							ri.setRes_download_num(res_download_num);
							ri.setRes_keep_num(res_keep_num);

							ris.add(ri);
						}
						return ris;
					}
				});
		return ris;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ResourceInfo> getUser_indexResourcesInfoByUser_id(int user_id) {
		String sql = "select r.id,r.name,r.nowtime,r.isjing,r.isyuan, ";
		sql += "ifnull(c.res_comments_num,0) as res_comments_num, ";
		sql += "ifnull(d.res_download_num,0) as res_download_num, ";
		sql += "ifnull(kp.res_keep_num,0) as res_keep_num ";
		sql += "from resources r ";
		sql += "left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on r.id=kp.resources_id ";
		sql += "left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on r.id=c.resources_id ";
		sql += "left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on r.id=d.resources_id ";
		sql += "where user_id = ? order by r.nowtime desc ";

		List<ResourceInfo> ris = (List<ResourceInfo>) jdbcTemplate.query(sql, new Object[] { user_id },
				new ResultSetExtractor() {
					public List<ResourceInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<ResourceInfo> ris = new ArrayList<ResourceInfo>();

						while (rs.next()) {
							ResourceInfo ri = new ResourceInfo();

							int id = rs.getInt("id");
							String name = rs.getString("name");

							Timestamp d = rs.getTimestamp("nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out.println("====ResourcesDaoImpl.getResourcesByUser_id()方法中Timestamp转Date出错");
							}
							String nowtime = ToolsUtils.getTimeFormatText(date);

							int isjing = rs.getInt("isjing");
							int isyuan = rs.getInt("isyuan");

							int res_comments_num = rs.getInt("res_comments_num");
							int res_download_num = rs.getInt("res_download_num");
							int res_keep_num = rs.getInt("res_keep_num");

							ri.setId(id);
							ri.setName(name);
							ri.setNowtime(nowtime);
							ri.setIsjing(isjing);
							ri.setIsyuan(isyuan);
							ri.setRes_comments_num(res_comments_num);
							ri.setRes_download_num(res_download_num);
							ri.setRes_keep_num(res_keep_num);

							ris.add(ri);
						}
						return ris;
					}
				});
		return ris;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ResourceSortInfo> getResourcesSortInfoByUser_id(int user_id) {
		String sql = "select a.sort,a.id,r.name,a.nowtime,r.isjing,r.isyuan, ";
		sql += "ifnull(c.res_comments_num,0) as res_comments_num, ";
		sql += "ifnull(d.res_download_num,0) as res_download_num, ";
		sql += "ifnull(kp.res_keep_num,0) as res_keep_num ";
		sql += "from ";
		sql += "(select 1 as sort,c.resources_id as id,c.nowtime as nowtime from comments c ";
		sql += "where c.user1_id = ? ";
		sql += "group by c.resources_id ";
		sql += "union all ";
		sql += "select 2 as sort,d.resources_id as id,d.nowtime as nowtime from download d ";
		sql += "where d.user_id = ? ";
		sql += "group by d.resources_id ";
		sql += "union all ";
		sql += "select 3 as sort,k.resources_id as id,k.nowtime as nowtime from keep k ";
		sql += "where k.user_id = ? ";
		sql += "group by k.resources_id)as a ";
		sql += "left join resources r on a.id = r.id ";
		sql += "left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on a.id=kp.resources_id ";
		sql += "left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on a.id=c.resources_id ";
		sql += "left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on a.id=d.resources_id ";
		sql += "order by sort,nowtime desc ";

		List<ResourceSortInfo> ris = (List<ResourceSortInfo>) jdbcTemplate.query(sql,
				new Object[] { user_id, user_id, user_id }, new ResultSetExtractor() {
					public List<ResourceSortInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<ResourceSortInfo> ris = new ArrayList<ResourceSortInfo>();

						while (rs.next()) {
							ResourceSortInfo ri = new ResourceSortInfo();

							int sort = rs.getInt("sort");
							int id = rs.getInt("id");
							String name = rs.getString("name");

							Timestamp d = rs.getTimestamp("nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out.println("====ResourcesDaoImpl.getResourcesByUser_id()方法中Timestamp转Date出错");
							}
							String nowtime = ToolsUtils.getTimeFormatText(date);

							int isjing = rs.getInt("isjing");
							int isyuan = rs.getInt("isyuan");

							int res_comments_num = rs.getInt("res_comments_num");
							int res_download_num = rs.getInt("res_download_num");
							int res_keep_num = rs.getInt("res_keep_num");

							ri.setSort(sort);
							ri.setId(id);
							ri.setName(name);
							ri.setNowtime(nowtime);
							ri.setIsjing(isjing);
							ri.setIsyuan(isyuan);
							ri.setRes_comments_num(res_comments_num);
							ri.setRes_download_num(res_download_num);
							ri.setRes_keep_num(res_keep_num);

							ris.add(ri);
						}
						return ris;
					}
				});
		return ris;
	}

	@Override
	public int getAllResourcesNum() {
		String sql = "select count(*) from resources";
		int rows = jdbcTemplate.queryForObject(sql, Integer.class);
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Resources> getFirstResourcesList() {
		String sql = "select id,name,isfirst,isyuan,isjing from resources where isfirst=1 or isyuan=1 or isjing=1 ";

		List<Resources> rslist = (List<Resources>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<Resources> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<Resources> rslist = new ArrayList<Resources>();

				while (rs.next()) {
					Resources r = new Resources();

					int id = rs.getInt("id");
					String name = rs.getString("name");

					int isfirst = rs.getInt("isfirst");
					int isyuan = rs.getInt("isyuan");
					int isjing = rs.getInt("isjing");

					r.setId(id);
					r.setName(name);
					r.setIsfirst(isfirst);
					r.setIsyuan(isyuan);
					r.setIsjing(isjing);

					rslist.add(r);
				}
				return rslist;
			}
		});
		return rslist;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<IndexHtmlResources> getResourcesListBySearch(Resources resources) {
		String name = resources.getName();
		int sort = resources.getSort();
		int jifen = resources.getJifen();
		int label = resources.getLabel();

		String sql = "select r.id as r_id,r.sort as r_sort,r.name as r_name,l2.name as r_label,r.label as r_label_num,r.nowtime as r_nowtime,r.isfirst,r.isjing,r.isyuan, ";
		sql += "u.id as u_id,u.name as u_name,l1.name as u_label,u.img, ";
		sql += "ifnull(c.res_comments_num,0) as res_comments_num, ";
		sql += "ifnull(d.res_download_num,0) as res_download_num, ";
		sql += "ifnull(kp.res_keep_num,0) as res_keep_num ";
		sql += "from resources r ";
		sql += "left join user u on u.id=r.user_id ";
		sql += "left join labels l1 on l1.id=u.label ";
		sql += "left join labels l2 on l2.id=r.label ";
		sql += "left join (select kp.resources_id,count(kp.resources_id) as res_keep_num from resources r,keep kp where r.id=kp.resources_id group by kp.resources_id)kp on r.id=kp.resources_id ";
		sql += "left join (select c.resources_id,count(c.resources_id) as res_comments_num from resources r,comments c where r.id=c.resources_id group by c.resources_id)c on r.id=c.resources_id ";
		sql += "left join (select d.resources_id,count(d.resources_id) as res_download_num from resources r,download d where r.id=d.resources_id group by d.resources_id)d on r.id=d.resources_id ";
		sql += "where r.isfriend=0 ";
		if (sort != 0) {
			sql += " and r.sort=" + sort;
		}
		if (label != 0) {
			sql += " and r.label=" + label;
		}
		if (jifen != 999) {
			if (jifen == 0) {
				sql += " and r.jifen=0 ";
			} else if (jifen == 1) {
				sql += " and r.jifen<=5 and r.jifen>=1 ";
			} else if (jifen == 2) {
				sql += " and r.jifen>=6 ";
			}
		}
		if (name != null && !name.trim().isEmpty()) {
			sql += " and r.name like '%" + name + "%' ";
		}
		sql += " order by r.nowtime desc ";

		List<IndexHtmlResources> ihrs = (List<IndexHtmlResources>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<IndexHtmlResources> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<IndexHtmlResources> ihrs = new ArrayList<IndexHtmlResources>();

				while (rs.next()) {
					IndexHtmlResources ihr = new IndexHtmlResources();

					int r_id = rs.getInt("r_id");
					int r_sort = rs.getInt("r_sort");
					String r_name = rs.getString("r_name");
					String r_label = rs.getString("r_label");
					int r_label_num = rs.getInt("r_label_num");

					Timestamp d = rs.getTimestamp("r_nowtime");
					Date date = new Date();
					try {
						date = d;
					} catch (Exception e) {
						System.out.println("====ResourcesDaoImpl.getIndexHtmlResources()方法中Timestamp转Date出错");
					}
					String r_nowtime = ToolsUtils.getTimeFormatText(date);

					int isfirst = rs.getInt("isfirst");
					int isjing = rs.getInt("isjing");
					int isyuan = rs.getInt("isyuan");

					int u_id = rs.getInt("u_id");
					String u_name = rs.getString("u_name");
					String u_label = rs.getString("u_label");
					String img = rs.getString("img");

					int res_comments_num = rs.getInt("res_comments_num");
					int res_download_num = rs.getInt("res_download_num");
					int res_keep_num = rs.getInt("res_keep_num");

					ihr.setR_id(r_id);
					ihr.setR_sort(r_sort);
					ihr.setR_name(r_name);
					ihr.setR_label(r_label);
					ihr.setR_label_num(r_label_num);
					ihr.setR_nowtime(r_nowtime);
					ihr.setIsfirst(isfirst);
					ihr.setIsjing(isjing);
					ihr.setIsyuan(isyuan);
					ihr.setU_id(u_id);
					ihr.setU_name(u_name);
					ihr.setU_label(u_label);
					ihr.setImg(img);
					ihr.setRes_comments_num(res_comments_num);
					ihr.setRes_download_num(res_download_num);
					ihr.setRes_keep_num(res_keep_num);

					ihrs.add(ihr);
				}
				return ihrs;
			}
		});
		return ihrs;
	}

}
