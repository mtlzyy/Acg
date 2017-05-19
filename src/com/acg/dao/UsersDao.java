package com.acg.dao;

import com.acg.bean.Users;
import com.acg.dao.util.DaoHandle;

public class UsersDao {
	/**
	 * �����û�id���û���������Ϣ
	 * @param userId
	 * @return
	 */
	public Users findUserByUserId(int userId){
		return DaoHandle.executeQueryForSingle("select * from users where userId=?",new Object[]{userId},Users.class);
	}
	
	/**
	 * �����û��������û�
	 * @param userName
	 * @return
	 */
	public Users findUserByUserName(String userName){
		return DaoHandle.executeQueryForSingle("select * from users where username=?", new Object[]{userName}, Users.class);
	}
	
	/**
	 * ����Email�����û�
	 * @param email
	 * @return
	 */
	public Users findUserByEmail(String email){
		return DaoHandle.executeQueryForSingle("select * from users where email=?", new Object[]{email}, Users.class);
	}
	
	/**
	 * ע��
	 */
	
	public void addUser(String name,int gender,String pwd,String email){
		DaoHandle.executeUpdate("insert into users values(userid.nextval,?,?,?,?,0,1,'����˺�����ʲô��ûд','noface.gif',sysdate)", new Object[]{name,gender,pwd,email});
	}
	
	/**
	 * �޸��û���
	 * @param id
	 * @param userName
	 */
	public void updateUserName(int id,String userName){
		DaoHandle.executeUpdate("update users set username=? where userid=?", new Object[]{userName,id});
	}
	
	/**
	 * �޸��Ա�
	 * @param id
	 * @param gender
	 */
	public void updateGender(int id,int gender){
		DaoHandle.executeUpdate("update users set gender=? where userid=?", new Object[]{gender,id});
	}
	
	/**
	 * �޸�����
	 * @param id
	 * @param pwd
	 */
	public void updatePassword(int id,String pwd){
		DaoHandle.executeUpdate("update users set pwd=? where userid=?", new Object[]{pwd,id});
	}
	
	/**
	 * �޸�ǩ��
	 * @param id
	 * @param signature
	 */
	public void updateSignature(int id,String signature){
		DaoHandle.executeUpdate("update users set signature=? where userid=?", new Object[]{signature,id});
	}
	
	/**
	 * �޸�ͷ��
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
