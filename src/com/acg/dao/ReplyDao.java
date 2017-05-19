package com.acg.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.acg.bean.Comments;
import com.acg.bean.Reply;
import com.acg.dao.util.DaoHandle;

public class ReplyDao {
	/**
	 * 根据回复id找回复表其他信息
	 * @param replyId
	 * @return
	 */
	public Reply findReplyByReplyDao(int replyId){
		return DaoHandle.executeQueryForSingle("select * from Reply where replyId=?",new Object[]{replyId},Reply.class);
	}
	/**
	 * 根据评论id找回复
	 * @param commentId
	 * @return
	 */
	public List<Reply> findReplyByCommentId(int commentId){
		List<Reply> list=new ArrayList<Reply>();
		list=DaoHandle.executeQueryForMultiple("select * from reply where commentid=? order by time_posted", new Object[]{commentId}, Reply.class);
		return list;
	}
	/**
	 * 添加回复
	 * @param commentId
	 * @param userId
	 * @param contents
	 */
	public void addReply(int commentId,int userId,String contents){
		DaoHandle.executeUpdate("insert into reply values(replyid.nextval,?,?,?,sysdate)", new Object[]{commentId,userId,contents});
	}
	/**
	 * 分页查找
	 * @param commentId
	 * @param page
	 * @return
	 */
	public List<Reply> findReplyPageByCommentId(int commentId,int page){
		List<Reply> list=new ArrayList<Reply>();
		list=DaoHandle.findPage("select * from reply where commentid=? order by time_posted", new Object[]{commentId}, Reply.class, page, 5);
		return list;
	}
	public int findTotalCountByCommentId(int commentId){
		BigDecimal bd=(BigDecimal)DaoHandle.findUniqueResult("select count(*) from reply where commentid=?", new Object[]{commentId});
		return bd.intValue();
	}
}
