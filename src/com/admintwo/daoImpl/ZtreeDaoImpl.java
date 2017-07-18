package com.admintwo.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.admintwo.dao.ZtreeDao;
import com.admintwo.model.Ztree;
import com.admintwo.model.pojo.KeepTree;
import com.admintwo.model.pojo.KnowledgeTree;
import com.admintwo.model.pojo.SimpleZtree;
import com.admintwo.model.pojo.TreeLaba;
import com.admintwo.model.pojo.TreesForFenye;
import com.admintwo.model.pojo.WaitTree;
import com.admintwo.util.ToolsUtils;

@Component("ztreeDaoImpl")
public class ZtreeDaoImpl implements ZtreeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<KnowledgeTree> getZtree() {
		String sql = "select id,pid,name,url,parent,open,click from ztree where istree in(1,2) and isfriend=0 order by istree,isorder ";

		List<KnowledgeTree> ztrees = (List<KnowledgeTree>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<KnowledgeTree> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<KnowledgeTree> ztrees = new ArrayList<KnowledgeTree>();

				while (rs.next()) {
					KnowledgeTree ztree = new KnowledgeTree();

					String id = rs.getString("id");
					String pid = rs.getString("pid");
					String name = rs.getString("name");
					String url = rs.getString("url");
					int parent = rs.getInt("parent");
					int open = rs.getInt("open");
					String click = rs.getString("click");

					ztree.setId(id);
					ztree.setName(name);
					ztree.setPid(pid);
					if ("0".equals(url)) {
						ztree.setUrl("javascript:void();");
					} else {
						ztree.setUrl(url);
					}
					ztree.setTarget("_self");
					if (parent == 1) {
						ztree.setParent(true);
					} else {
						ztree.setParent(false);
					}
					if (open == 1) {
						ztree.setOpen(true);
					} else {
						ztree.setOpen(false);
					}
					if ("0".equals(click)) {
						ztree.setClick("javascript:void();");
					} else {
						ztree.setClick(click);
					}

					ztrees.add(ztree);
				}
				return ztrees;
			}
		});
		return ztrees;
	}

	@Override
	public int getNumber(int sort, String keyword) {
		if (sort == 0) {
			String sql = "select count(*) from ztree where istree=3 and isfriend=1 ";
			return jdbcTemplate.queryForObject(sql, Integer.class);
		} else if (sort == 1) {
			String sql = "select count(*) from ztree where pid=? and istree=3 and isfriend=1 ";
			return jdbcTemplate.queryForObject(sql, Integer.class, Integer.valueOf(keyword));
		} else if (sort == 2) {
			String sqlkeyword = "%" + keyword + "%";
			String sql = "select count(*) from ztree where name like '" + sqlkeyword + "' and istree=3 and isfriend=1 ";
			return jdbcTemplate.queryForObject(sql, Integer.class);
		}
		return 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SimpleZtree> getSimpleZtree() {
		String sql = "select istree,id,pid,name,url,parent,open,click from ztree where istree in(1,2) and isfriend=0 order by istree,isorder ";

		List<SimpleZtree> ztrees = (List<SimpleZtree>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<SimpleZtree> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<SimpleZtree> ztrees = new ArrayList<SimpleZtree>();

				while (rs.next()) {
					SimpleZtree ztree = new SimpleZtree();

					@SuppressWarnings("unused")
					int istree = rs.getInt("istree");
					String id = rs.getString("id");
					String pid = rs.getString("pid");
					String name = rs.getString("name");
					@SuppressWarnings("unused")
					String url = rs.getString("url");
					int parent = rs.getInt("parent");
					@SuppressWarnings("unused")
					int open = rs.getInt("open");
					@SuppressWarnings("unused")
					String click = rs.getString("click");

					ztree.setId(id);
					ztree.setName(name);
					ztree.setPid(pid);
					ztree.setUrl("javascript:void();");
					ztree.setTarget("_self");
					if (parent == 1) {
						ztree.setParent(true);
					} else {
						ztree.setParent(false);
					}
					ztree.setClick("clickSimpleZtree(" + id + ",'" + name + "');");
					ztree.setOpen(false);

					ztrees.add(ztree);
				}
				return ztrees;
			}
		});
		return ztrees;
	}

	@Override
	@Transactional
	public boolean insertTree(Ztree ztree) {
		String sql = "insert into ztree(istree,id,pid,name,url,remark) values(3,?,?,?,?,?)";
		String uuid = UUID.randomUUID().toString();
		int rows = jdbcTemplate.update(sql, uuid, Integer.valueOf(ztree.getPid()), ztree.getName(), ztree.getUrl(),
				ztree.getRemark());
		if (rows == 1) {
			// 用户成功分享一条有用的知识
			/*HttpServletRequest request = ServletActionContext.getRequest();
			com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
			if (user != null) {
				// 用户已登录
				sql = " insert into usertree(user_id,tree_id) values(?,?) ";
				int num = jdbcTemplate.update(sql, user.getId(), uuid);
				if (num == 1) {
					System.out.println("====ztreeDaoImpl/insertTree用户分享一条知识成功且保存记录成功");
					int counts = jdbcTemplate.queryForObject(
							"select count(*) from usertree where user_id = ? and sort=0 ", Integer.class, user.getId());
					if (counts != 0 && counts % 5 == 0) {
						// 从数据库重新查询该用户分享知识数量。用户分享知识已经5条，积分加1
						int add = jdbcTemplate.update("update user set jifen=jifen+1 where id=? ", user.getId());
						if (add == 1) {
							System.out.println("====用户分享5个知识，积分增加1。用户id：" + user.getId());
							sql = " insert into messages(message,sort,email) values(?,?,?) ";
							int message = jdbcTemplate.update(sql,
									"<a style=\"color:#4F99CF;\" target=\"_blank\" href=\"user_home?id=" + user.getId()
											+ "\">" + user.getName() + "</a>&nbsp;恭喜你，成功分享5条有用的知识，兑换1积分。",
									7, user.getEmail());
							if (message == 1) {
								System.out.println("====用户分享5条有用知识消息增加成功");
							}
						}
					}
				}
			}*/
			return true;
		}
		return false;
	}

	@Override
	public int getWaitNum() {
		String sql = "select count(*) from ztree where istree=3 and isfriend=0 ";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WaitTree> getWaitTrees() {
		String sql = "select tid as id,a.name as title,url,b.name as sort from ztree a left join (select id,name from ztree where istree in(1,2)) b on a.pid=b.id where isfriend=0 and istree=3 ";

		List<WaitTree> ztrees = (List<WaitTree>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<WaitTree> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<WaitTree> ztrees = new ArrayList<WaitTree>();

				while (rs.next()) {
					WaitTree ztree = new WaitTree();

					int id = rs.getInt("id");
					String title = rs.getString("title");
					String url = rs.getString("url");
					String sort = rs.getString("sort");

					ztree.setId(id);
					ztree.setTitle(title);
					ztree.setUrl(url);
					ztree.setSort(sort);

					ztrees.add(ztree);
				}
				return ztrees;
			}
		});
		return ztrees;
	}

	@Override
	public boolean shenheTree(int treeid, int flag) {
		String sql = "";
		if (flag == 0) {
			sql = "update ztree set isfriend=1 where tid = ? and istree=3";
		} else if (flag == 1) {
			sql = "update ztree set isfriend=2 where tid = ? and istree=3";
		}
		int rows = jdbcTemplate.update(sql, treeid);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TreesForFenye> getTreesByFenye(int sort, String keyword, int start, int maxResult) {
		String sql = "";
		if (sort == 0) {
			// 表示是首页搜索
			sql = "select tid as id,a.name as title,url,b.name as sort,number,remark,nowtime from ztree a left join (select id,name from ztree where istree in(1,2)) b on a.pid=b.id where isfriend=1 and istree=3 order by a.tid desc limit ?,? ";
			List<TreesForFenye> ztrees = (List<TreesForFenye>) jdbcTemplate.query(sql,
					new Object[] { start, maxResult }, new ResultSetExtractor() {
						public List<TreesForFenye> extractData(ResultSet rs) throws SQLException, DataAccessException {
							List<TreesForFenye> ztrees = new ArrayList<TreesForFenye>();
							while (rs.next()) {
								TreesForFenye ztree = new TreesForFenye();
								int id = rs.getInt("id");
								String title = rs.getString("title");
								String url = rs.getString("url");
								String sort = rs.getString("sort");
								int number = rs.getInt("number");
								String remark = rs.getString("remark");

								Timestamp d = rs.getTimestamp("nowtime");
								Date date = new Date();
								try {
									date = d;
								} catch (Exception e) {
									System.out.println("====ztreeDaoImpl.getTreesByFenye()方法中Timestamp转Date出错");
								}
								String nowtime = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
								/* HH:mm:ss */
								ztree.setId(id);
								ztree.setTitle(title);
								ztree.setUrl(url);
								ztree.setSort(sort);
								ztree.setNumber(number);
								ztree.setRemark(remark);
								ztree.setNowtime(nowtime);

								ztrees.add(ztree);
							}
							return ztrees;
						}
					});
			return ztrees;
		} else if (sort == 1) {
			// 表示是节点搜索
			sql = "select tid as id,a.name as title,url,b.name as sort,number,remark,nowtime from ztree a left join (select id,name from ztree where istree in(1,2)) b on a.pid=b.id where a.pid=? and isfriend=1 and istree=3 order by a.tid desc limit ?,? ";
			List<TreesForFenye> ztrees = (List<TreesForFenye>) jdbcTemplate.query(sql,
					new Object[] { Integer.valueOf(keyword), start, maxResult }, new ResultSetExtractor() {
						public List<TreesForFenye> extractData(ResultSet rs) throws SQLException, DataAccessException {
							List<TreesForFenye> ztrees = new ArrayList<TreesForFenye>();
							while (rs.next()) {
								TreesForFenye ztree = new TreesForFenye();
								int id = rs.getInt("id");
								String title = rs.getString("title");
								String url = rs.getString("url");
								String sort = rs.getString("sort");
								int number = rs.getInt("number");
								String remark = rs.getString("remark");

								Timestamp d = rs.getTimestamp("nowtime");
								Date date = new Date();
								try {
									date = d;
								} catch (Exception e) {
									System.out.println("====ztreeDaoImpl.getTreesByFenye()方法中Timestamp转Date出错");
								}
								String nowtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);

								ztree.setId(id);
								ztree.setTitle(title);
								ztree.setUrl(url);
								ztree.setSort(sort);
								ztree.setNumber(number);
								ztree.setRemark(remark);
								ztree.setNowtime(nowtime);

								ztrees.add(ztree);
							}
							return ztrees;
						}
					});
			return ztrees;
		} else {
			// 手动拼接模糊查询，不然执行不成功
			String value = "%" + keyword + "%";
			// 表示是关键字搜索
			sql = "select tid as id,a.name as title,url,b.name as sort,number,remark,nowtime from ztree a left join (select id,name from ztree where istree in(1,2)) b on a.pid=b.id where a.name like '"
					+ value + "' and isfriend=1 and istree=3 order by a.tid desc limit ?,? ";
			List<TreesForFenye> ztrees = (List<TreesForFenye>) jdbcTemplate.query(sql,
					new Object[] { start, maxResult }, new ResultSetExtractor() {
						public List<TreesForFenye> extractData(ResultSet rs) throws SQLException, DataAccessException {
							List<TreesForFenye> ztrees = new ArrayList<TreesForFenye>();
							while (rs.next()) {
								TreesForFenye ztree = new TreesForFenye();
								int id = rs.getInt("id");
								String title = rs.getString("title");
								String url = rs.getString("url");
								String sort = rs.getString("sort");
								int number = rs.getInt("number");
								String remark = rs.getString("remark");

								Timestamp d = rs.getTimestamp("nowtime");
								Date date = new Date();
								try {
									date = d;
								} catch (Exception e) {
									System.out.println("====ztreeDaoImpl.getTreesByFenye()方法中Timestamp转Date出错");
								}
								String nowtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);

								ztree.setId(id);
								ztree.setTitle(title);
								ztree.setUrl(url);
								ztree.setSort(sort);
								ztree.setNumber(number);
								ztree.setRemark(remark);
								ztree.setNowtime(nowtime);

								ztrees.add(ztree);
							}
							return ztrees;
						}
					});
			return ztrees;
		}
	}

	@Override
	@Transactional
	public boolean addNumById(int numid) {
		HttpServletRequest request = ServletActionContext.getRequest();
		com.admintwo.model.User user = (com.admintwo.model.User) request.getSession().getAttribute("user");
		// 插入一条记录之前，查询是否已经收藏
		String sql = "select count(*) from usertree where user_id=? and tree_id=? and sort=1 ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, user.getId(), numid);
		if (count > 0) {
			// 已经收藏了，不执行
			return false;
		}
		sql = " insert into usertree(user_id,tree_id,sort) values(?,?,1) ";
		int num = jdbcTemplate.update(sql, user.getId(), numid);
		if (num == 1) {
			// 保存一份记录成功，增加一个收藏量
			sql = "update ztree set number=number+1 where tid=? and istree=3 and isfriend=1";
			int rows = jdbcTemplate.update(sql, numid);
			if (rows == 1) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TreeLaba> getToday() {
		String sql = "select message,nowtime,a.num as daynum,b.num as allnum from messages m ";
		sql += "left join (select count(*) as num from ztree where to_days(nowtime) = to_days(now()) and istree=3) a on 1=1 ";
		sql += "left join (select count(*) as num from ztree where istree=3) b on 1=1 ";
		sql += "where m.sort=7 order by id desc limit 10 ";
		List<TreeLaba> ztrees = (List<TreeLaba>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<TreeLaba> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<TreeLaba> ztrees = new ArrayList<TreeLaba>();
				while (rs.next()) {
					TreeLaba ztree = new TreeLaba();
					String message = rs.getString("message");
					Timestamp d = rs.getTimestamp("nowtime");
					Date date = new Date();
					try {
						date = d;
					} catch (Exception e) {
						System.out.println("====ztreeDaoImpl.getToday()方法中Timestamp转Date出错");
					}
					String nowtime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
					int daynum = rs.getInt("daynum");
					int allnum = rs.getInt("allnum");

					ztree.setMessage(message);
					ztree.setNowtime(nowtime);
					ztree.setDaynum(daynum);
					ztree.setAllnum(allnum);

					ztrees.add(ztree);
				}
				return ztrees;
			}
		});
		return ztrees;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<KeepTree> getKeepTreeByUser_id(int id) {
		String sql = "select a.id,b.name,b.url,a.nowtime from usertree a left join ztree b on a.tree_id=b.tid where user_id =? and sort=1 order by nowtime desc ";

		List<KeepTree> kpts = (List<KeepTree>) jdbcTemplate.query(sql, new Object[] { id }, new ResultSetExtractor() {
			public List<KeepTree> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<KeepTree> kpts = new ArrayList<KeepTree>();

				while (rs.next()) {
					KeepTree kpt = new KeepTree();

					int id = rs.getInt("id");
					String name = rs.getString("name");
					String url = rs.getString("url");

					Timestamp d = rs.getTimestamp("nowtime");
					Date date = new Date();
					try {
						date = d;
					} catch (Exception e) {
						System.out.println("====ResourcesDaoImpl.getResourcesByUser_id()方法中Timestamp转Date出错");
					}
					String nowtime = ToolsUtils.getTimeFormatText(date);

					kpt.setId(id);
					kpt.setName(name);
					kpt.setUrl(url);
					kpt.setNowtime(nowtime);

					kpts.add(kpt);
				}
				return kpts;
			}
		});
		return kpts;
	}

}
