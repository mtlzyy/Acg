package com.acg.bean;

import java.sql.Timestamp;
import java.util.List;

public class Comments {
	private int commentid;
	private int userid;
	private int videoid;
	private String contents;
	private Timestamp time_posted;
	//�ظ��û�
	private Users user;
	//�ظ��б�
	private List<Reply> replyList;
	//����¥��
	private int floor;
	//�ظ�����
	private int total;
	//�ظ���ҳ��
	private int maxPage;
	
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public List<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<Reply> replyList) {
		this.replyList = replyList;
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
	public int getVideoid() {
		return videoid;
	}
	public void setVideoid(int videoid) {
		this.videoid = videoid;
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
