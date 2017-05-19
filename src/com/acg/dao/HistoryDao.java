package com.acg.dao;

import java.math.BigDecimal;

import com.acg.bean.History;
import com.acg.dao.util.DaoHandle;

public class HistoryDao {
	/**
	 * 根据浏览记录id找浏览记录表其他信息
	 * @param historyId
	 * @return
	 */
	public History findHistoryByHistoryId(int historyId){
		return DaoHandle.executeQueryForSingle("select * from history where historyId=?",new Object[]{historyId},History.class);
	}
	
	/**
	 * 根据用户ID查找浏览记录数量
	 * @param id
	 * @return
	 */
	public int findHistoryCount(int id){
		return ((BigDecimal)DaoHandle.findUniqueResult("select count(*) from history where userid=?", new Object[]{id})).intValue();	
	}
	
	public History findByUserAndVideo(int userid,int videoid){
		return DaoHandle.executeQueryForSingle("select * from history where userid=? and videoid=?", new Object[]{userid,videoid}, History.class);
	}
	
	public void addHistory(int userid,int videoid){
		DaoHandle.executeUpdate("insert into history values(historyid.nextval,?,?,sysdate)", new Object[]{userid,videoid});
	}
	public void updateHistory(int userid,int videoid){
		DaoHandle.executeUpdate("update history set time_created = sysdate where userid=? and videoid=?", new Object[]{userid,videoid});
	}
}
