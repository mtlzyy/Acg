package com.acg.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.bean.Video;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class ChangeRankingModel extends GenericModel {
	private String choose;
	private String boardid;

	public void setChoose(String choose) {
		this.choose = choose;
	}

	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest req = ModelSupport.getRequest();
		VideoDao videoDao=new VideoDao();
		int b=Integer.parseInt(boardid);
		if(choose.equals("week")){
			List<Video> weeklyVideo=videoDao.findWeeklyRankingByBoardIdAndClickCount(b);
			if(weeklyVideo==null||weeklyVideo.size()<7){
				weeklyVideo = videoDao.findLatestVideoByBoardId(b,7);
			}
			req.setAttribute("weeklyVideo", weeklyVideo);
			
		}else{
			List<Video> monthlyVideo=videoDao.findMonthlyRankingByBoardIdAndClickCount(b);
			if(monthlyVideo==null||monthlyVideo.size()<7){
				monthlyVideo = videoDao.findLatestVideoByBoardId(b,7);
			}
			req.setAttribute("monthlyVideo", monthlyVideo);	
		}
		req.setAttribute("choose", choose);
		
		return SUCCESS;
	}
	
}
