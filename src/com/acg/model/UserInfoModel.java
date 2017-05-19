package com.acg.model;


import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Users;
import com.acg.dao.UsersDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class UserInfoModel extends GenericModel{
	private String userId;
	@Override
	public String execute() {
		UsersDao usersDao = new UsersDao();
		Users user = usersDao.findUserByUserId(Integer.parseInt(userId));
		HttpServletRequest request = ModelSupport.getRequest();
		request.setAttribute("user",user);
		return SUCCESS;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
