package com.acg.model;

import javax.servlet.http.HttpSession;

import com.acg.bean.Users;
import com.acg.dao.FavouritesDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class FavouriteModel extends GenericModel{
	private String videoId;
	@Override
	public String execute() {
		HttpSession session=ModelSupport.getSession();
		Users loginUser=(Users) session.getAttribute("loginUser");
		FavouritesDao favouriteDao=new FavouritesDao();
		int vid=Integer.parseInt(videoId);
		if(favouriteDao.findFavouritesByVideoIdAndUserId(vid, loginUser.getUserid())==null){
			favouriteDao.addFavourites(vid, loginUser.getUserid());
		}else{
			favouriteDao.removeFavourites(vid, loginUser.getUserid());
		}
		return SUCCESS;
	}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
}
