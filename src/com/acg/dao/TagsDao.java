package com.acg.dao;

import com.acg.bean.Tags;
import com.acg.dao.util.DaoHandle;

public class TagsDao {
	/**
	 * 根据类型id找类型表其他信息
	 * @param tagsId
	 * @return
	 */
	public Tags findTagsByTagsId(int tagId){
		return DaoHandle.executeQueryForSingle("select * from Tags where tagId=?",new Object[]{tagId},Tags.class);
	}
}
