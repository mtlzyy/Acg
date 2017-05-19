package com.acg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.acg.bean.Comments;
import com.acg.bean.Users;
import com.acg.dao.CommentsDao;
import com.acg.mvc.bean.ModelSupport;

public class PublishAjaxServlet extends HttpServlet{

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
		CommentsDao commentsDao=new CommentsDao();
		String comment=req.getParameter("comment");
		//System.out.println(comment);
		int videoId=Integer.parseInt(req.getParameter("videoId"));
		//System.out.println(req.getSession().getId());
		//≤‚ ‘”√µƒ
		Users loginUser=(Users) req.getSession().getAttribute("loginUser");
		if(loginUser!=null){
			Comments comments=new Comments();
			comments.setContents(comment);
			comments.setUserid(loginUser.getUserid());
			comments.setVideoid(videoId);
			//resp.getWriter().print("");
			commentsDao.addComments(comments);
		}else{
			System.out.println("hello");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			System.out.println("hello");
		}
		
		
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
	}
	
}
