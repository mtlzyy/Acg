package com.acg.model;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acg.bean.Users;
import com.acg.dao.UsersDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;
	

public class ChangeInfoModel extends GenericModel{
	private String name;
	private String signature;
	private String password;
	private String gender;
	@Override
	public String execute() {
		HttpServletRequest request = ModelSupport.getRequest();
		HttpServletResponse response = ModelSupport.getResponse();
		try {
			byte[] bytes = signature.getBytes("ISO-8859-1");
			signature = new String(bytes,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		UsersDao ud= new UsersDao();
		Users user =(Users)ModelSupport.getSession().getAttribute("loginUser");
		int id =user.getUserid();
		if(!user.getUserName().equals(name)){
			Users u = ud.findUserByUserName(name);
			if(u!=null){
				request.setAttribute("error", "用户名已存在");
			}
			else{
				ud.updateUserName(id, name);
				Cookie ck = new Cookie("userName", name);
				response.addCookie(ck);
			}
		}
		if(signature.equals("")){
			ud.updateSignature(id,"这个人很懒，什么都没有写");
		}
		else{
			if(!user.getSignature().equals(signature)){
				ud.updateSignature(id, signature);	
			}
		}
		
		if(!user.getPwd().equals(password)){
			ud.updatePassword(id, password);
			Cookie ck = new Cookie("passWord", password);
			response.addCookie(ck);
		}
		if(user.getGender()!=Integer.parseInt(gender)){
			ud.updateGender(id, Integer.parseInt(gender));
		}
		Users newuser = ud.findUserByUserId(id);
		ModelSupport.getSession().setAttribute("loginUser", newuser);
		return SUCCESS;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	 	
}
