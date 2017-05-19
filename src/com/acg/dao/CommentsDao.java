package com.acg.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.acg.bean.Comments;
import com.acg.dao.util.DaoHandle;

public class CommentsDao {
	/**
	 * ��������id�����۱�������Ϣ
	 * @param commentId
	 * @return
	 */
	public Comments findCommentsByCommentId(int commentId){
		return DaoHandle.executeQueryForSingle("select * from comments where commentId=?",new Object[]{commentId},Comments.class);
	}
	/**
	 * ��������
	 * @param comments
	 */
	public void addComments(Comments comments){
		DaoHandle.executeUpdate("insert into comments values(commentid.nextval,?,?,?,sysdate)", new Object[]{comments.getUserid(),comments.getVideoid(),comments.getContents()});
	}
	/**
	 * ��videoId��ҳ��ѯ
	 * @param videoId
	 * @param page
	 * @return
	 */
	public List<Comments> findCommentsPageByVideoId(int videoId,int page){
		List<Comments> list=new ArrayList<Comments>();
		list=DaoHandle.findPage("select * from comments where videoid=? order by time_posted asc", new Object[]{videoId}, Comments.class, page, 5);
		return list;
	}
	/**
	 * ��videoId������������
	 * @param videoId
	 * @return
	 */
	public int findTotalCountByVideoId(int videoId){
		BigDecimal bd=(BigDecimal)DaoHandle.findUniqueResult("select count(*) from comments where videoid=?", new Object[]{videoId});
		return bd.intValue();
	}
}
