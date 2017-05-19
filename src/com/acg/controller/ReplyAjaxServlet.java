package com.acg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acg.bean.Users;
import com.acg.dao.ReplyDao;

public class ReplyAjaxServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ReplyDao replyDao=new ReplyDao();
		String replyContent=req.getParameter("replyContent");
		int commentId=Integer.parseInt(req.getParameter("commentId"));
		Users user=(Users) req.getSession().getAttribute("loginUser");
		//System.out.println(replyContent);
		//System.out.println(commentId);
		//System.out.println(user);
		if(user!=null){
			replyDao.addReply(commentId, user.getUserid(), replyContent);
		}else{
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
		
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(arg0, arg1);
	}
	
}
