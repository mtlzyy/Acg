package com.acg.dao;

import com.acg.bean.Activation;
import com.acg.dao.util.DaoHandle;

public class ActivationDao {
	/**
	 * 添加待激活用户
	 * @param avCode
	 * @param userName
	 * @param pwd
	 * @param gender
	 * @param email
	 */
	public void addActivation(String avCode,String userName,String pwd,int gender,String email){
		DaoHandle.executeUpdate("insert into activation values(?,?,?,?,?,avid.nextval,sysdate)", new Object[]{avCode,userName,pwd,gender,email} );
	}
	
	/**
	 * 查找最近添加的待激活用户
	 * @return
	 */
	public Activation findLastestActivation(){
		return DaoHandle.executeQueryForSingle("select * from (select * from activation order by appid desc ) where rownum=1", null, Activation.class);
	}
	
	/**
	 * 根据激活码和激活ID查询待激活用户
	 * @param avid
	 * @param avcode
	 * @return
	 */
	public Activation findeActivationByCodeAndId(int avid,String avcode){
		return DaoHandle.executeQueryForSingle("select * from activation where appid=? and avcode=?", new Object[]{avid,avcode}, Activation.class);
	}
	
	/**
	 * 根据ID删除指定待激活信息
	 * @param avid
	 */
	public void deleteActivationById(int avid){
		DaoHandle.executeUpdate("delete from activation where appid=?", new Object[]{avid});
	}
	
	/**
	 * 删除两天前的所有临时用户信息
	 */
	public void removeTempUsersTwoDaysAgo(){		
		DaoHandle.executeUpdate("delete from activation where createtime<sysdate-2", null);
	}
	
	/**
	 * select by userName
	 * @param name
	 * @return
	 */
	public Activation findByUserName(String name){
		return DaoHandle.executeQueryForSingle("select * from activation where username=?", new Object[]{name}, Activation.class);
	}
	
	public Activation findByEmail(String email){
		return DaoHandle.executeQueryForSingle("select * from activation where email=?", new Object[]{email}, Activation.class);
	}
}
