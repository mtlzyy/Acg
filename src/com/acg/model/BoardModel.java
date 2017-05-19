package com.acg.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Video;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class BoardModel extends GenericModel {
	private String boardid;
	public String getBoardid() {
		return boardid;
	}
	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		int board=Integer.parseInt(boardid);
		VideoDao vd=new VideoDao();
		List<Video> listv=new ArrayList<Video>();
		List<Video> listv2=new ArrayList<Video>();
		HttpServletRequest req=ModelSupport.getRequest();

		listv=vd.findVideoByBoardidAndTime_posted(board);
		listv2=vd.findVideoByBoardidAndClickcount(board);
		req.setAttribute("boardid", boardid);
		req.setAttribute("listv", listv);
		req.setAttribute("listv2", listv2);
		return SUCCESS;
	}
	
}
