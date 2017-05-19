package com.acg.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Video;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class SearchBoxModel extends GenericModel {
	private String keyWord;
	@Override
	public String execute() {
		HttpServletRequest req = ModelSupport.getRequest();
		VideoDao vdao = new VideoDao();
		List<Video> videos = vdao.findVideoByKeyword(keyWord, 8);
		req.setAttribute("searchList", videos);
		return SUCCESS;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
