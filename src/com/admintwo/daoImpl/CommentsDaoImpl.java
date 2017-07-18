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
import org.springframework.stereotype.Component;

import com.admintwo.dao.CommentsDao;
import com.admintwo.model.Comments;
import com.admintwo.model.Labels;
import com.admintwo.model.User;
import com.admintwo.model.pojo.CommentsUserLabels;
import com.admintwo.model.pojo.UserCommentsResources;
import com.admintwo.model.pojo.UserLabels;
import com.admintwo.util.ToolsUtils;

@Component("CommentsDaoImpl")
public class CommentsDaoImpl implements CommentsDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CommentsUserLabels> getCommentsByResources_id(int resources_id) {
		String sql = "select c.id as c_id,c.content as c_content,c.nowtime as c_nowtime,c.user1_id as c_user1_id,c.user2_id as c_user2_id,c.lou as c_lou, ";
		sql += "u1.user1_id,u1.name as user1_name,u1.img as user1_img,u1.label as user1_label, ";
		sql += "ifnull(u2.user2_id,0) as user2_id,u2.name as user2_name,u2.img as user2_img,u2.label as user2_label ";
		sql += "from comments c ";
		sql += "left join (select u1.id as user1_id,u1.name,u1.img, l.name as label from user u1,labels l where u1.label=l.id) u1 on c.user1_id=u1.user1_id ";
		sql += "left join (select u2.id as user2_id,u2.name,u2.img, l.name as label from user u2,labels l where u2.label=l.id) u2 on c.user2_id=u2.user2_id ";
		sql += "where resources_id=? and isfriend=0 ";
		sql += "order by user2_id,c_lou ";

		List<CommentsUserLabels> commentsUserLabelsList = (List<CommentsUserLabels>) jdbcTemplate.query(sql,
				new Object[] { resources_id }, new ResultSetExtractor() {
					public List<CommentsUserLabels> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<CommentsUserLabels> commentsUserLabelsList = new ArrayList<CommentsUserLabels>();

						while (rs.next()) {
							CommentsUserLabels commentsUserLabels = new CommentsUserLabels();

							int c_id = rs.getInt("c_id");
							String c_content = rs.getString("c_content");
							Timestamp d = rs.getTimestamp("c_nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out
										.println("====CommentsDaoImpl.getCommentsByResources_id()方法中Timestamp转Date出错");
							}
							String c_nowtime = ToolsUtils.getTimeFormatText(date);
							int c_lou = rs.getInt("c_lou");

							int user1_id = rs.getInt("c_user1_id");
							String user1_name = rs.getString("user1_name");
							String user1_img = rs.getString("user1_img");
							String user1_label = rs.getString("user1_label");

							int user2_id = rs.getInt("c_user2_id");
							String user2_name = rs.getString("user2_name");
							String user2_img = rs.getString("user2_img");
							String user2_label = rs.getString("user2_label");

							Comments comments = new Comments();
							comments.setId(c_id);
							comments.setContent(c_content);
							comments.setNowtime(c_nowtime);
							comments.setLou(c_lou);

							UserLabels userLabels1 = new UserLabels();

							User user1 = new User();
							user1.setId(user1_id);
							user1.setName(user1_name);
							user1.setImg(user1_img);

							Labels labels1 = new Labels();
							labels1.setName(user1_label);

							userLabels1.setUser(user1);
							userLabels1.setLabels(labels1);

							UserLabels userLabels2 = new UserLabels();

							User user2 = new User();
							user2.setId(user2_id);
							user2.setName(user2_name);
							user2.setImg(user2_img);

							Labels labels2 = new Labels();
							labels2.setName(user2_label);

							userLabels2.setUser(user2);
							userLabels2.setLabels(labels2);

							commentsUserLabels.setComments(comments);
							commentsUserLabels.setU1(userLabels1);
							commentsUserLabels.setU2(userLabels2);

							commentsUserLabelsList.add(commentsUserLabels);
						}
						return commentsUserLabelsList;
					}
				});
		return commentsUserLabelsList;
	}

	@Override
	public int addComments(Comments comment) {
		String sql = "insert into comments(content,resources_id,user1_id,user2_id,lou) values(?,?,?,?,?)";
		int rows = jdbcTemplate.update(sql, comment.getContent(), comment.getResources_id(), comment.getUser1_id(),
				comment.getUser2_id(), comment.getLou());
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserCommentsResources> getCommentsResourcesByUser_id(int user_id) {
		String sql = "select r.id as r_id,r.name as r_name,c.content as c_content,c.nowtime as c_nowtime ";
		sql += "from comments c ";
		sql += "left join resources r on c.resources_id=r.id ";
		sql += "where c.user1_id = ? ";
		sql += "order by c.nowtime desc limit 10 ";

		List<UserCommentsResources> ucrss = (List<UserCommentsResources>) jdbcTemplate.query(sql,
				new Object[] { user_id }, new ResultSetExtractor() {
					public List<UserCommentsResources> extractData(ResultSet rs)
							throws SQLException, DataAccessException {

						List<UserCommentsResources> ucrss = new ArrayList<UserCommentsResources>();

						while (rs.next()) {
							UserCommentsResources ucrs = new UserCommentsResources();

							int r_id = rs.getInt("r_id");
							String r_name = rs.getString("r_name");
							String c_content = rs.getString("c_content");

							Timestamp d = rs.getTimestamp("c_nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out.println(
										"====CommentsDaoImpl.getCommentsResourcesByUser_id()方法中Timestamp转Date出错");
							}
							String c_nowtime = ToolsUtils.getTimeFormatText(date);

							ucrs.setR_id(r_id);
							ucrs.setR_name(r_name);
							ucrs.setC_content(c_content);
							ucrs.setC_nowtime(c_nowtime);

							ucrss.add(ucrs);
						}
						return ucrss;
					}
				});
		return ucrss;
	}

	@Override
	public boolean isfirstComment(int user_id, int resources_id) {
		int counts = jdbcTemplate.queryForObject(
				"select count(*) from comments where user1_id = ? and resources_id = ? ", Integer.class, user_id,
				resources_id);
		if (counts > 0) {
			return true;
		}
		return false;
	}

}
