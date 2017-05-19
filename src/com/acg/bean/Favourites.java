package com.acg.bean;

import java.sql.Timestamp;

public class Favourites {
	private int fid;
	private int videoid;
	private int userid;
	private Timestamp timeAdded;
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
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
	public Timestamp getTimeAdded() {
		return timeAdded;
	}
	public void setTimeAdded(Timestamp timeAdded) {
		this.timeAdded = timeAdded;
	}
	
}
