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

import com.admintwo.dao.MessagesDao;
import com.admintwo.model.Messages;
import com.admintwo.util.ToolsUtils;

@Component("MessagesDaoImpl")
public class MessagesDaoImpl implements MessagesDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertMessages(String message, int sort, String email) {
		String sql = "insert into messages(message,sort,email) values(?,?,?)";
		int rows = jdbcTemplate.update(sql, message, sort, email);
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Messages> getMessageByEmail(String email) {
		String sql = "select m.id,m.message,m.sort,m.isRead,m.nowtime ";
		sql += "from messages m ";
		sql += "where m.email=? and (sort in(0,2,5,6) or isRead=0) ";
		sql += "order by m.nowtime desc ";

		List<Messages> msgs = (List<Messages>) jdbcTemplate.query(sql, new Object[] { email },
				new ResultSetExtractor() {
					public List<Messages> extractData(ResultSet rs) throws SQLException, DataAccessException {

						List<Messages> msgs = new ArrayList<Messages>();

						while (rs.next()) {
							Messages msg = new Messages();

							int id = rs.getInt("id");
							String message = rs.getString("message");
							int sort = rs.getInt("sort");
							int isRead = rs.getInt("isRead");

							Timestamp d = rs.getTimestamp("nowtime");
							Date date = new Date();
							try {
								date = d;
							} catch (Exception e) {
								System.out.println("====messagesDaoImpl.getMessageByEmail()方法中Timestamp转Date出错");
							}
							String nowtime = ToolsUtils.getTimeFormatText(date);

							msg.setId(id);
							msg.setMessage(message);
							msg.setSort(sort);
							msg.setIsRead(isRead);
							msg.setNowtime(nowtime);

							msgs.add(msg);
						}
						return msgs;
					}
				});
		return msgs;
	}

	@Override
	public int updateIsReadByIdAndEmail(int id, String email) {
		String sql = "update messages set isRead=1 where id=? and email=? ";
		int rows = jdbcTemplate.update(sql, id, email);
		return rows;
	}

	@Override
	public boolean updateALlIsReadByEmail(String email) {
		String sql = "update messages set isRead=1 where email=? ";
		int rows = jdbcTemplate.update(sql, email);
		if (rows > 0) {
			return true;
		}
		return false;
	}

}
