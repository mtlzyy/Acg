package com.acg.model;

import com.acg.mvc.bean.GenericModel;

public class MyPostModel extends GenericModel{
	private String userid;

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Override
	public String execute() {
		String s="";
		if(userid==null||userid.trim().equals("")){
			s = ERROR;
		}
		else{
			s = SUCCESS;
		}
		return s;
	}
}
