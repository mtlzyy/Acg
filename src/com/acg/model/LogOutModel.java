package com.acg.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class LogOutModel extends GenericModel{
	
	@Override
	public String execute() {
		HttpServletRequest req = ModelSupport.getRequest();
		HttpServletResponse resp = ModelSupport.getResponse();
		Cookie[] cks = req.getCookies();
		if(cks!=null){
			for(Cookie c :cks){
				if(c.getName().equals("userName")){
					c.setMaxAge(0);
					resp.addCookie(c);
				}
				if(c.getName().equals("passWord")){
					c.setMaxAge(0);
					resp.addCookie(c);
				}
			}
		}
		ModelSupport.getSession().removeAttribute("loginUser");
		return SUCCESS;
	}
}
