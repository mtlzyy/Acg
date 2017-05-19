package com.acg.model;



import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Activation;
import com.acg.bean.Users;
import com.acg.dao.ActivationDao;
import com.acg.dao.UsersDao;
import com.acg.email.SendMail;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;
import com.acg.util.CommonUtil;

public class RegistModel extends GenericModel{
	private String userName;
	private String code;
	private String passWord;
	private String gender;
	private String email;
	@Override
	public String execute() {
		HttpServletRequest request= ModelSupport.getRequest();
		String sessionCode = (String)ModelSupport.getSession().getAttribute("Code");

		if(sessionCode!=null&&sessionCode.equalsIgnoreCase(code)){
			UsersDao usersDao=new UsersDao();
			Users user1=usersDao.findUserByUserName(userName);
			Users user2=usersDao.findUserByEmail(email);
			ActivationDao ad= new ActivationDao();
			Activation av1 = ad.findByUserName(userName);
			Activation av2 = ad.findByEmail(email);
			//判断用户名是否已存在
			if(user1!=null||av1!=null){
				request.setAttribute("nameError", "用户名已存在");
				return ERROR;
			}
			//判断邮箱是否已被使用
			if(user2!=null||av2!=null){
				request.setAttribute("emailError", "邮箱已被使用");
				return ERROR;
			}
			
			String avCode= CommonUtil.generateCode(15);
			ActivationDao avDao=new ActivationDao();
			int genderint=Integer.parseInt(gender);
			avDao.addActivation(avCode, userName, passWord, genderint, email);
			Activation activation=avDao.findLastestActivation();
			String activationInfo="http://localhost:8080/ACG/activation.do?avCode="+avCode+"&avId="+activation.getAppId();
			SendMail sm=new SendMail();
			sm.sendActivationMail(email, activationInfo);
			return SUCCESS;
		}
		else{
			request.setAttribute("codeError", "验证码错误");
			return ERROR;
		}
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}
