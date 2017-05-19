package com.acg.dao;

import com.acg.bean.Users;
import com.acg.dao.util.DaoHandle;

public class UsersDao {
	/**
	 * 根据用户id找用户表其他信息
	 * @param userId
	 * @return
	 */
	public Users findUserByUserId(int userId){
		return DaoHandle.executeQueryForSingle("select * from users where userId=?",new Object[]{userId},Users.class);
	}
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	public Users findUserByUserName(String userName){
		return DaoHandle.executeQueryForSingle("select * from users where username=?", new Object[]{userName}, Users.class);
	}
	
	/**
	 * 根据Email查找用户
	 * @param email
	 * @return
	 */
	public Users findUserByEmail(String email){
		return DaoHandle.executeQueryForSingle("select * from users where email=?", new Object[]{email}, Users.class);
	}
	
	/**
	 * 注册
	 */
	
	public void addUser(String name,int gender,String pwd,String email){
		DaoHandle.executeUpdate("insert into users values(userid.nextval,?,?,?,?,0,1,'这个人很懒，什么都没写','noface.gif',sysdate)", new Object[]{name,gender,pwd,email});
	}
	
	/**
	 * 修改用户名
	 * @param id
	 * @param userName
	 */
	public void updateUserName(int id,String userName){
		DaoHandle.executeUpdate("update users set username=? where userid=?", new Object[]{userName,id});
	}
	
	/**
	 * 修改性别
	 * @param id
	 * @param gender
	 */
	public void updateGender(int id,int gender){
		DaoHandle.executeUpdate("update users set gender=? where userid=?", new Object[]{gender,id});
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param pwd
	 */
	public void updatePassword(int id,String pwd){
		DaoHandle.executeUpdate("update users set pwd=? where userid=?", new Object[]{pwd,id});
	}
	
	/**
	 * 修改签名
	 * @param id
	 * @param signature
	 */
	public void updateSignature(int id,String signature){
		DaoHandle.executeUpdate("update users set signature=? where userid=?", new Object[]{signature,id});
	}
	
	/**
	 * 修改头像
	 * @param id
	 * @param photo
	 */
	public void updatePhoto(int id,String photo){
		DaoHandle.executeUpdate("update users set photo=? where userid=?", new Object[]{photo,id});
	}
	
	public void updatePt(int videoid, int pts){
		DaoHandle.callexecute("call pointsup(?,?)", new Object[]{videoid,pts});
	}
}
