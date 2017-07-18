package com.admintwo.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.admintwo.dao.KeepDao;
import com.admintwo.model.Keep;

@Component("KeepDaoImpl")
public class KeepDaoImpl implements KeepDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertKeep(Keep keep) {
		String sql = "insert into keep(user_id,resources_id) values(?,?)";
		int rows = jdbcTemplate.update(sql, keep.getUser_id(), keep.getResources_id());
		return rows;
	}

	@Override
	public int deleteKeep(Keep keep) {
		String sql = "delete from keep where user_id=? and resources_id=? ";
		int rows = jdbcTemplate.update(sql, keep.getUser_id(), keep.getResources_id());
		return rows;
	}

}
