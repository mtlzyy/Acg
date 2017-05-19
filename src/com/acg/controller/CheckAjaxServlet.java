package com.acg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acg.bean.Reply;
import com.acg.bean.Users;
import com.acg.dao.ReplyDao;
import com.acg.dao.UsersDao;

public class CheckAjaxServlet extends HttpServlet{

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
		UsersDao userDao=new UsersDao();
		int cid=Integer.parseInt(req.getParameter("cid"));
		int page;
		if(req.getParameter("pageNum")==null){
			page=1;
		}else{
			page=Integer.parseInt(req.getParameter("pageNum"));
		}
		int total=replyDao.findTotalCountByCommentId(cid);
		int maxPage=total/5;
		if(total%5!=0){
			maxPage++;
		}
		List<Reply> replyList=replyDao.findReplyPageByCommentId(cid, page);
		for(int i=0;i<replyList.size();i++){
			Users replyUser=userDao.findUserByUserId(replyList.get(i).getUserid());
			replyList.get(i).setUser(replyUser);
		}
		req.setAttribute("cid", cid);
		req.setAttribute("pageNum", page);
		req.setAttribute("maxPage", maxPage);
		req.setAttribute("replyList", replyList);
		System.out.println(cid);
		req.getRequestDispatcher("include/check.jsp").forward(req, resp);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
	}
	
}
