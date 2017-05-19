package com.acg.model;

import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Activation;
import com.acg.bean.Users;
import com.acg.dao.ActivationDao;
import com.acg.dao.UsersDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class ActivationModel extends GenericModel{
	
	private String avCode;
	private String avId;
	@Override
	public String execute() {
		HttpServletRequest request = ModelSupport.getRequest();
		int activationId=Integer.parseInt(avId);
		ActivationDao avDao = new ActivationDao();
		Activation av= avDao.findeActivationByCodeAndId(activationId, avCode);
		
		if(av!=null){
			UsersDao usersDao = new UsersDao();
			Users user1=usersDao.findUserByUserName(av.getUserName());
			if(user1!=null){
				request.setAttribute("error", "用户名已被使用");
				return ERROR;
			}
			Users user2= usersDao.findUserByEmail(av.getEmail());
			if(user2!=null){
				request.setAttribute("error", "此邮箱已被使用");
				return ERROR;
			}
			usersDao.addUser(av.getUserName(), av.getGender(), av.getPwd(), av.getEmail());
			avDao.deleteActivationById(activationId);
			Users user = usersDao.findUserByUserName(av.getUserName());
			ModelSupport.getSession().setAttribute("loginUser", user);
			return SUCCESS;
		}
		else{
			request.setAttribute("error", "验证信息已过期");
			return ERROR;
		}
		
	}
	
	public void setAvCode(String avCode) {
		this.avCode = avCode;
	}
	public void setAvId(String avId) {
		this.avId = avId;
	}

}
