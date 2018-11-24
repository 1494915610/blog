package bz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import bean.User;
import dao.DBHelper;

public class UserBiz {
	public User login(String username,String userpwd)throws BizException{
		
		
		if(username==null || username.trim().isEmpty()){
			
			throw new BizException("请填写用户名：");
		}
		if(userpwd==null || userpwd.trim().isEmpty()){
			
			throw new BizException("请填写密码：");
		}
		
		
		
		//查询数据库判断用户是否存在
		/*DBHelper dbhelper = new DBHelper();
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(userpwd);*/
		String sql="select * from user where account = ? and pwd = ?";
		/*Map<String,String> user = dbhelper.findMap(sql,params);
		return user;*/
		return DBHelper.unique(sql, User.class, username, userpwd);
		
	}
	
	public List<User> findAll() {
		
		return DBHelper.select("select * from user", User.class);
	}

	public void add(User user) throws BizException {
		if(user.getName()==null || user.getName().trim().isEmpty()){
			throw new BizException("请填写用户名！");
		}
		
		String sql = "insert into user (name,account,tel,pwd) value(?,?,?,?)";
		DBHelper.insert(sql, user.getName(),user.getAccount(),user.getTel(),user.getPwd());
	}

	public Object find(User user) {
		String sql = "select * from user where 1=1";
		ArrayList<Object> params = new ArrayList<Object>();
		if(user.getAccount()!=null && user.getAccount().trim().isEmpty() == false){
			sql += " and account like ?";
			params.add("%"+user.getAccount()+"%");
		}
		if(user.getName()!=null && user.getName().trim().isEmpty() == false){
			sql += " and name like ?";
			params.add("%"+user.getName()+"%");
		}
		if(user.getTel()!=null && user.getTel().trim().isEmpty() == false){
			sql += " and tel like ?";
			params.add("%"+user.getTel()+"%");
		}
		return DBHelper.select(sql, params);
	}

	public User findById(String id) {
		
		return DBHelper.unique("select * from user where id=?", User.class,id);
	}

	public void save(User user) throws ServletException, IOException, BizException{
		if(user.getAccount() == null || user.getAccount().trim().isEmpty()){
			throw new BizException("请填写用户名！");
		}
		if(user.getName() == null || user.getName().trim().isEmpty()){
			throw new BizException("请填写姓名！");
		}
		if(user.getTel() == null || user.getTel().trim().isEmpty()){
			throw new BizException("请填写电话！");
		}
		
		DBHelper.update("update user set name=?,account=?,tel=? where id=?", user.getName(),user.getAccount(),user.getTel(),user.getId());
	}
}
