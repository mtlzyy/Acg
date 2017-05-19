package com.acg.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Video;
import com.acg.dao.FavouritesDao;
import com.acg.dao.HistoryDao;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class UserVideoModel extends GenericModel{
	private String id;
	private String pageNo;
	private String menu;
	 
	@Override
	public String execute() {
		HttpServletRequest request= ModelSupport.getRequest();
		VideoDao vd =new VideoDao();
		int choice=Integer.parseInt(menu);
		int userid=Integer.parseInt(id);
		int page = Integer.parseInt(pageNo);
		int num=1;
		List<Video> vlist = new ArrayList<Video>();
		if(choice==1){
			FavouritesDao fd = new FavouritesDao();
			num = fd.findFavouritesCount(userid);
			vlist = vd.findByUsersFavourites(userid,page,12);
		}
		if(choice==2){
			HistoryDao hd = new HistoryDao();
			num = hd.findHistoryCount(userid);
			vlist = vd.findByUsersHistory(userid, page, 12);
		}
		if(choice==3){
			num = vd.findVideoCount(userid);
			vlist = vd.findVideoByUserIdAndPage(userid,page,12);
		}
		
		int max=num/12;
		if(num%12!=0){
			max=num/12+1;
		}
		request.setAttribute("id", id);
		request.setAttribute("menu",menu);
		request.setAttribute("max", max);
		request.setAttribute("pageNo", pageNo);
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
