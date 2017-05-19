package com.acg.bean;

import java.sql.Timestamp;

public class Video {
	private int videoid;
	private int userid;
	private int boardid;
	private String title;
	private Timestamp time_posted;
	private int clickcount;
	private String url;
	private String photo;
	
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getVideoid() {
		return videoid;
	}
	public void setVideoid(int videoid) {
		this.videoid = videoid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getBoardid() {
		return boardid;
	}
	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}
	public Timestamp getTime_posted() {
		return time_posted;
	}
	public void setTime_posted(Timestamp time_posted) {
		this.time_posted = time_posted;
	}
	public int getClickcount() {
		return clickcount;
	}
	public void setClickcount(int clickcount) {
		this.clickcount = clickcount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
