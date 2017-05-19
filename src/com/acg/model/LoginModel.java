package com.acg.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acg.bean.Users;
import com.acg.dao.UsersDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class LoginModel extends GenericModel{
	private String userName;
	private String passWord;
	private String rmbpwd;
	private String code;
	@Override
	public synchronized String execute() {
		HttpServletRequest request = ModelSupport.getRequest();
		HttpServletResponse response =ModelSupport.getResponse();
		String sessionCode=(String)ModelSupport.getSession().getAttribute("Code");
		if(sessionCode.equalsIgnoreCase(code)){
			UsersDao userDao=new UsersDao();
			Users user=userDao.findUserByUserName(userName);
			if(user!=null){
				if(user.getPwd().equals(passWord)){	
					if(rmbpwd!=null&&rmbpwd.equals("on")){
		    			Cookie cookiename=new Cookie("userName",userName);	    			
		    			Cookie cookiepwd=new Cookie("passWord",passWord);
		    			cookiename.setMaxAge(3600*24*7);
		    			cookiepwd.setMaxAge(3600*24*7);
		    			response.addCookie(cookiename);
		    			response.addCookie(cookiepwd);
					}
					else{
						Cookie[] cookies=request.getCookies();
	    				if(cookies!=null){
		    				for(Cookie c:cookies){
		    					String key=c.getName();
		    					if(key.equals("userName")||key.equals("passWord")){
		    						c.setMaxAge(0);
		    						response.addCookie(c);
		    					}
		    				}
		    			}
					}
					ModelSupport.getSession().setAttribute("loginUser",user);
					return SUCCESS;
				}	
				else{
					request.setAttribute("pwdError", "密码错误");
					return ERROR;
				}
			}
			else{
				request.setAttribute("nameError", "用户不存在");
				return ERROR;
			}
		}	
		else{
			request.setAttribute("codeError", "验证码错误");
			return ERROR;
		}
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public void setRmbpwd(String rmbpwd) {
		this.rmbpwd = rmbpwd;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
