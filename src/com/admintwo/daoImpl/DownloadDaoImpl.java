package com.admintwo.daoImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.admintwo.dao.DownloadDao;
import com.admintwo.model.Download;

@Component("DownloadDaoImpl")
public class DownloadDaoImpl implements DownloadDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int getDownload(Download download) {
		String sql = "select count(*) from download where user_id=? and resources_id=?";
		int rows = jdbcTemplate.queryForObject(sql, Integer.class, download.getUser_id(), download.getResources_id());
		return rows;
	}

	@Override
	public int insertDownload(Download download) {
		String sql = "insert into download(user_id,resources_id) values(?,?)";
		int rows = jdbcTemplate.update(sql, download.getUser_id(), download.getResources_id());
		return rows;
	}

}
