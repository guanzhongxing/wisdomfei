package com.admintwo.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.admintwo.dao.UserDao;
import com.admintwo.model.Labels;
import com.admintwo.model.User;
import com.admintwo.model.UserPay;
import com.admintwo.model.pojo.MyLabel;
import com.admintwo.model.pojo.ResourcesTop;
import com.admintwo.model.pojo.UserLabels;
import com.admintwo.model.pojo.UserTop;

@Component("userDaoImpl")
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int existEmail(String email) {
		String sql = "select count(*) from user where email = ?";
		int rows = jdbcTemplate.queryForObject(sql, Integer.class, email);
		return rows;
	}

	@Override
	public int insertUser(String name, String password, String email) {
		int number = new Random().nextInt(12);
		String img = "res/images/avatar/" + number + ".jpg";
		String sql = "insert into user(name,password,email,img) values(?,?,?,?)";
		int rows = jdbcTemplate.update(sql, name, password, email, img);
		return rows;
	}

	@Override
	public User getUserByEmail(String email) {
		String sql = "select id,name,password,email,qq,jifen,money,isactive,style,nowtime,sex,label,img,city,motto from user where email = ?";
		User user = jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int num) throws SQLException {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String qq = rs.getString("qq");
				int jifen = rs.getInt("jifen");
				int money = rs.getInt("money");
				int isactive = rs.getInt("isactive");
				int style = rs.getInt("style");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				int sex = rs.getInt("sex");
				String label = rs.getString("label");
				String img = rs.getString("img");
				String city = rs.getString("city");
				String motto = rs.getString("motto");

				User user = new User();
				user.setId(id);
				user.setName(name);
				user.setPassword(password);
				user.setEmail(email);
				user.setQq(qq);
				user.setJifen(jifen);
				user.setMoney(money);
				user.setIsactive(isactive);
				user.setStyle(style);
				user.setNowtime(nowtime);
				user.setSex(sex);
				user.setLabel(label);
				user.setImg(img);
				user.setCity(city);
				user.setMotto(motto);
				return user;
			}
		}, email);
		return user;
	}

	@Override
	public User getUserById(int id) {
		String sql = "select id,name,password,email,qq,jifen,money,isactive,style,nowtime,sex,label,img,city,motto from user where id = ?";
		User user = jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int num) throws SQLException {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String qq = rs.getString("qq");
				int jifen = rs.getInt("jifen");
				int money = rs.getInt("money");
				int isactive = rs.getInt("isactive");
				int style = rs.getInt("style");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				int sex = rs.getInt("sex");
				String label = rs.getString("label");
				String img = rs.getString("img");
				String city = rs.getString("city");
				String motto = rs.getString("motto");

				User user = new User();
				user.setId(id);
				user.setName(name);
				user.setPassword(password);
				user.setEmail(email);
				user.setQq(qq);
				user.setJifen(jifen);
				user.setMoney(money);
				user.setIsactive(isactive);
				user.setStyle(style);
				user.setNowtime(nowtime);
				user.setSex(sex);
				user.setLabel(label);
				user.setImg(img);
				user.setCity(city);
				user.setMotto(motto);
				return user;
			}
		}, id);
		return user;
	}

	@Override
	public UserLabels getUserLabelsById(int id) {
		String sql = "select  user.id,user.name,user.password,user.email,user.jifen,user.money,user.isactive,user.style,user.nowtime,user.sex,user.label,user.img,user.city,user.motto,labels.name as labelsName from user left join labels on user.label=labels.id where labels.isfriend=0 and user.id=?";
		UserLabels userLabels = jdbcTemplate.queryForObject(sql, new RowMapper<UserLabels>() {
			@Override
			public UserLabels mapRow(ResultSet rs, int num) throws SQLException {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String email = rs.getString("email");
				int jifen = rs.getInt("jifen");
				int money = rs.getInt("money");
				int isactive = rs.getInt("isactive");
				int style = rs.getInt("style");
				Timestamp nowtime = rs.getTimestamp("nowtime");
				int sex = rs.getInt("sex");
				String label = rs.getString("label");
				String img = rs.getString("img");
				String city = rs.getString("city");
				String motto = rs.getString("motto");

				String labelsName = rs.getString("labelsName");

				UserLabels userLabels = new UserLabels();
				User user = new User();
				user.setId(id);
				user.setName(name);
				user.setPassword(password);
				user.setEmail(email);
				user.setJifen(jifen);
				user.setMoney(money);
				user.setIsactive(isactive);
				user.setStyle(style);
				user.setNowtime(nowtime);
				user.setSex(sex);
				user.setLabel(label);
				user.setImg(img);
				user.setCity(city);
				user.setMotto(motto);
				Labels labels = new Labels();
				labels.setName(labelsName);

				userLabels.setUser(user);
				userLabels.setLabels(labels);

				return userLabels;
			}
		}, id);
		return userLabels;
	}

	@Override
	public int updateIsactive(String email) {
		String sql = "update user set isactive=1 where isactive=0 and email=? ";
		int rows = jdbcTemplate.update(sql, email);
		return rows;
	}

	@Override
	public int updateStyle(String email) {
		String sql = "update user set style=case when style=0 then 1  when style=1 then 0 else style end  where email=? ";
		int rows = jdbcTemplate.update(sql, email);
		return rows;
	}

	@Override
	public int updateUserSetByEmail(User user) {
		String sql = "update user set name=?,sex=?,city=?,motto=?,qq=? where email=? ";
		int rows = jdbcTemplate.update(sql, user.getName(), user.getSex(), user.getCity(), user.getMotto(),
				user.getQq(), user.getEmail());
		return rows;
	}

	@Override
	public int updateUserImgByEmail(User user) {
		String sql = "update user set img=? where email=? ";
		int rows = jdbcTemplate.update(sql, user.getImg(), user.getEmail());
		return rows;
	}

	@Override
	public int updateUserPasswordByEmail(User user) {
		String sql = "update user set password=? where email=? ";
		int rows = jdbcTemplate.update(sql, user.getPassword(), user.getEmail());
		return rows;
	}

	@Override
	public int updateUserMoneyAndJifen(int moneyToJifen, String email) {
		String sql = "update user set money=money-?,jifen=jifen+10*? where email=? ";
		int rows = jdbcTemplate.update(sql, moneyToJifen, moneyToJifen, email);
		return rows;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<UserTop> getTop() {
		// 最近一个月分享次数最多的人,1为本月分享榜，2为最新新人榜，3为称号排行榜，4为赞助排行榜，5为积分排行榜
		String sql = "(select 1 as sort,user.id,user.name,user.img,r.num from ( ";
		sql += "select user_id,count(user_id) as num ";
		sql += "from resources ";
		sql += "where date_sub(curdate(),interval 30 day)<=nowtime ";
		sql += "group by user_id )r ";
		sql += "left join user on r.user_id=user.id ";
		sql += "order by r.num desc,id limit 12) ";
		sql += "union all (select 2 as sort,user.id,user.name,user.img,user.id as num from user where user.isactive=1 order by id desc limit 4) ";
		sql += "union all (select 3 as sort,user.id,user.name,user.img,labels.name as num from user left join labels on user.label=labels.id where user.label>2 order by user.label desc,id limit 12) ";
		sql += "union all (select 4 as sort,user.id,user.name,user.img,user.money as num from user where user.money>0 order by money desc,id limit 12) ";
		sql += "union all (select 5 as sort,user.id,user.name,user.img,user.jifen as num from user where user.jifen>0 order by jifen desc,id limit 12) ";

		List<UserTop> uts = (List<UserTop>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<UserTop> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<UserTop> uts = new ArrayList<UserTop>();

				while (rs.next()) {
					UserTop ut = new UserTop();

					int sort = rs.getInt("sort");
					int id = rs.getInt("id");
					String name = rs.getString("name");
					String img = rs.getString("img");
					String num = rs.getString("num");

					ut.setSort(sort);
					ut.setId(id);
					ut.setName(name);
					ut.setImg(img);
					ut.setNum(num);

					uts.add(ut);
				}
				return uts;
			}
		});
		return uts;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ResourcesTop> getResourcesTop() {
		String sql = "select r.sort,r.id,resources.name as name,r.num ";
		sql += "from ( ";
		sql += "(select 1 as sort,resources_id as id,count(resources_id) as num from comments where date_sub(curdate(),interval 30 day)<=nowtime group by resources_id order by num desc limit 12) ";
		sql += "union all ";
		sql += "(select 2 as sort,resources_id as id,count(resources_id) as num from download where date_sub(curdate(),interval 30 day)<=nowtime group by resources_id order by num desc limit 12) ";
		sql += "union all ";
		sql += "(select 3 as sort,resources_id as id,count(resources_id) as num from keep where date_sub(curdate(),interval 30 day)<=nowtime group by resources_id order by num desc limit 12) ";
		sql += ") r ";
		sql += "left join resources on r.id = resources.id ";
		sql += "order by r.sort,num desc ";
		List<ResourcesTop> rts = (List<ResourcesTop>) jdbcTemplate.query(sql, new ResultSetExtractor() {
			public List<ResourcesTop> extractData(ResultSet rs) throws SQLException, DataAccessException {

				List<ResourcesTop> rts = new ArrayList<ResourcesTop>();

				while (rs.next()) {
					ResourcesTop rt = new ResourcesTop();

					int sort = rs.getInt("sort");
					int id = rs.getInt("id");
					String name = rs.getString("name");
					int num = rs.getInt("num");

					rt.setSort(sort);
					rt.setId(id);
					rt.setName(name);
					rt.setNum(num);

					rts.add(rt);
				}
				return rts;
			}
		});
		return rts;
	}

	@Override
	public int updateNowtime(String email) {
		String sql = "update user set nowtime=now() where email=? ";
		int rows = jdbcTemplate.update(sql, email);
		return rows;
	}

	@Override
	public MyLabel getLabelByUser_id(int id) {
		String sql = "select id,mymoney,GROUP_CONCAT(mylabel SEPARATOR \".\") as mylabel,money from ";
		sql += " (select u.label as id,u.money as mymoney,l.name as mylabel,l.money from user u left join labels l on u.label <= l.id where u.id = ? limit 0,2) l ";
		MyLabel myLabel = jdbcTemplate.queryForObject(sql, new RowMapper<MyLabel>() {
			@Override
			public MyLabel mapRow(ResultSet rs, int num) throws SQLException {
				int id = rs.getInt("id");
				int money = rs.getInt("money");
				int mymoney = rs.getInt("mymoney");
				String mylabel = rs.getString("mylabel");

				MyLabel myLabel = new MyLabel();
				myLabel.setId(id);
				myLabel.setMoney(money);
				myLabel.setMylabel(mylabel);
				myLabel.setMymoney(mymoney);
				return myLabel;
			}
		}, id);
		return myLabel;
	}

	@Override
	public boolean updateLabel(int id, int money) {
		String sql = "update user set label=label+1,money=money-? where id=? ";
		int rows = jdbcTemplate.update(sql, money, id);
		if (rows == 1) {
			return true;
		}
		return false;
	}

	@Override
	public int getNewMessage(String email) {
		String sql = "select count(*) from messages where email = ? and isRead=0 and sort in(0,1,2,5,6)";
		int rows = jdbcTemplate.queryForObject(sql, Integer.class, email);
		return rows;
	}

	@Override
	@Transactional
	public boolean insertUserPay(UserPay userPay) {
		int user_id = userPay.getUser_id();
		String alipay = userPay.getAlipay();
		String weipay = userPay.getWeipay();
		String sql = "";

		// 先判断用户是否存在，存在的话只需要更新，不存在需要插入
		int rows = jdbcTemplate.queryForObject("select count(*) from userpay where user_id=" + user_id, Integer.class);
		if (rows > 0) {
			// 用户已存在，只需要进行更新操作
			if ("0".equals(alipay)) {
				// 更新微信二维码
				sql = "update userpay set weipay='" + weipay + "' where user_id=" + user_id;
			}
			if ("0".equals(weipay)) {
				// 更新支付宝二维码
				sql = "update userpay set alipay='" + alipay + "' where user_id=" + user_id;
			}
		} else {
			// 用户不存在，需要进行插入操作
			if ("0".equals(alipay)) {
				// 上传微信二维码
				sql = "insert into userpay(user_id,weipay) values(" + user_id + ",'" + weipay + "') ";
			}
			if ("0".equals(weipay)) {
				// 上传支付宝二维码
				sql = "insert into userpay(user_id,alipay) values(" + user_id + ",'" + alipay + "') ";
			}
		}

		int result = jdbcTemplate.update(sql);
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public UserPay getUserPay(int id) {
		String sql = "select alipay,weipay from userpay where user_id = ? ";
		UserPay userPay = jdbcTemplate.queryForObject(sql, new RowMapper<UserPay>() {
			@Override
			public UserPay mapRow(ResultSet rs, int num) throws SQLException {
				String alipay = rs.getString("alipay");
				String weipay = rs.getString("weipay");

				UserPay userPay = new UserPay();
				userPay.setAlipay(alipay);
				userPay.setWeipay(weipay);
				return userPay;
			}
		}, id);
		return userPay;
	}

	@Override
	public boolean insertUser(String name, String password, String email, int isactive, int sex, String img) {
		String sql = "insert into user(name,password,email,isactive,sex,img) values(?,?,?,?,?,?)";
		int rows = jdbcTemplate.update(sql, name, password, email, isactive, sex, img);
		if (rows > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int addJifen(int id) {
		int rows = jdbcTemplate.update("update user set jifen=jifen+1 where id=? ", id);
		return rows;
	}

}
