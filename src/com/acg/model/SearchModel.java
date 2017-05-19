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

public class SearchModel extends GenericModel {
	private String search;
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest req=ModelSupport.getRequest();
		HttpSession session=ModelSupport.getSession();
		List<Video> listv=new ArrayList<Video>();
		VideoDao vd=new VideoDao();
		try {
			byte[] bytes=search.getBytes("ISO-8859-1");
			 search = new String(bytes,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(search.equals("")){
			return ERROR;
		}
		int page=1;
		if(search.length()!=1){
			listv=vd.searchMoreByClickcount(search.length(), search, page);
		}else{
			listv=vd.searchVideoByWordAndClickcount(search, page);
		}
		if(listv.size()==0){
			return ERROR;
		}
		int rownum=vd.searchNumByClickcount(search, search.length());
		int maxpage=rownum/20;
		if(rownum%20!=0){
			maxpage++;
		}
		session.setAttribute("search", search);
		req.setAttribute("page", page);
		req.setAttribute("maxpage", maxpage);
		req.setAttribute("listv", listv);
		return SUCCESS;
	}
}
