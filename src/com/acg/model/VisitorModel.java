package com.acg.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Video;
import com.acg.dao.FavouritesDao;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class VisitorModel extends GenericModel {
	private String id;
	private String pageNo;
	private String menu;
	@Override
	public String execute() {
		VideoDao vd= new VideoDao();
		int userid=Integer.parseInt(id);
		List<Video> vlist;
		int page;
		if(pageNo==null){
			page=1;
		}else{
			page=Integer.parseInt(pageNo);
		}
		int num;
		if (Integer.parseInt(menu)==1){
			num=vd.findVideoCount(Integer.parseInt(id));
			vlist = vd.findVideoByUserIdAndPage(userid, page,15);
		}
		else{
			FavouritesDao fd = new FavouritesDao();
			num = fd.findFavouritesCount(userid);
			vlist = vd.findByUsersFavourites(userid,page,15);
		}
		int max=num/15;
		if(num%15!=0){
			max=num/15+1;
		}
		HttpServletRequest request= ModelSupport.getRequest();
		request.setAttribute("id", id);
		request.setAttribute("menu",menu);
		request.setAttribute("max", max);
		request.setAttribute("pageNo", page);
		request.setAttribute("vlist", vlist);
		return SUCCESS;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	

}
