package com.acg.bean;

import java.sql.Timestamp;

public class Reply {
	private int replyid;
	private int commentid;
	private int userid;
	private String contents;
	private Timestamp time_posted;
	private Users user;
	
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public int getReplyid() {
		return replyid;
	}
	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(Timestamp time_posted) {
		this.time_posted = time_posted;
	}
	
	
}
