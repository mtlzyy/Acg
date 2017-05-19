package com.acg.dao;

import com.acg.bean.Activation;
import com.acg.dao.util.DaoHandle;

public class ActivationDao {
	/**
	 * ��Ӵ������û�
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
	 * ���������ӵĴ������û�
	 * @return
	 */
	public Activation findLastestActivation(){
		return DaoHandle.executeQueryForSingle("select * from (select * from activation order by appid desc ) where rownum=1", null, Activation.class);
	}
	
	/**
	 * ���ݼ�����ͼ���ID��ѯ�������û�
	 * @param avid
	 * @param avcode
	 * @return
	 */
	public Activation findeActivationByCodeAndId(int avid,String avcode){
		return DaoHandle.executeQueryForSingle("select * from activation where appid=? and avcode=?", new Object[]{avid,avcode}, Activation.class);
	}
	
	/**
	 * ����IDɾ��ָ����������Ϣ
	 * @param avid
	 */
	public void deleteActivationById(int avid){
		DaoHandle.executeUpdate("delete from activation where appid=?", new Object[]{avid});
	}
	
	/**
	 * ɾ������ǰ��������ʱ�û���Ϣ
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
