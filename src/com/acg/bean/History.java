package com.acg.bean;

import java.sql.Timestamp;

public class History {
	private int historyid;
	private int userid;
	private int videoid;
	private Timestamp time_created;
	public int getHistoryid() {
		return historyid;
	}
	public void setHistoryid(int historyid) {
		this.historyid = historyid;
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
	public Timestamp getTime_created() {
		return time_created;
	}
	public void setTime_created(Timestamp time_created) {
		this.time_created = time_created;
	}
	
	
}
