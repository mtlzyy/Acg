package com.acg.model;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.acg.bean.Video;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class ChangeVideoListModel extends GenericModel {
	private String choose;
	private String pageNum;

	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public void setChoose(String choose) {
		this.choose = choose;
	}
	public String getChoose() {
		return choose;
	}
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest req=ModelSupport.getRequest();
		HttpSession session=ModelSupport.getSession();
		String search=(String) session.getAttribute("search");
		List<Video> listv=new ArrayList<Video>();
		VideoDao vd=new VideoDao();
			try {
				byte[] bytes=choose.getBytes("ISO-8859-1");
				choose = new String(bytes,"utf-8");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int page=Integer.parseInt(pageNum);
			int rownum=0;
			if(choose.equals("最新发布")){
				if(search.length()!=1){
					listv=vd.searchMoreByTime_posted(search.length(), search, page);
				}else{
					listv=vd.searchVideoByWordAndTime_posted(search, page);
				}
				rownum=vd.searchNumByTime_posted(search, search.length());
			
			}else{
				if(search.length()!=1){
					listv=vd.searchMoreByClickcount(search.length(), search, page);
				}else{
					listv=vd.searchVideoByWordAndClickcount(search, page);
				}	
				rownum=vd.searchNumByClickcount(search, search.length());
				
			}
			int maxpage=rownum/20;
			if(rownum%20!=0){
				maxpage++;
			}
		req.setAttribute("maxpage", maxpage);
		req.setAttribute("page", page);	
		req.setAttribute("listv", listv);
		req.setAttribute("choose", choose);
		return SUCCESS;
		
	}
	
}
