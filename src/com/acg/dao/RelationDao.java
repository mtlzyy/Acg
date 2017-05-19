package com.acg.dao;

import com.acg.bean.Relation;
import com.acg.dao.util.DaoHandle;

public class RelationDao {
	/**
	 * 根据关系id找关系表的其他信息
	 * @param relationId
	 * @return
	 */
	public Relation findRelationByRelationId(int relationId){
		return DaoHandle.executeQueryForSingle("select * from Relation where RelationId=?",new Object[]{relationId},Relation.class);
	}
}
