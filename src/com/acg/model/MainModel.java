package com.acg.model;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.acg.bean.Board;
import com.acg.bean.Video;
import com.acg.dao.BoardDao;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class MainModel extends GenericModel {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		VideoDao videoDao=new VideoDao();
		BoardDao boardDao=new BoardDao();
		//找到所有版块
		List<Board> listBoard=boardDao.findBoard();
		//针对不同的排行获取版块内不同的视频
		Map<Integer,List<Video>> videoMap=new HashMap<Integer,List<Video>>();
		Map<Integer,List<Video>> weeklyVideoMap=new HashMap<Integer,List<Video>>();
		//获得request
		HttpServletRequest request=ModelSupport.getRequest();
		HttpSession session = ModelSupport.getSession();
		//System.out.println(111);
		//将版块对应的视频封装至map中
		for (Board board : listBoard) {
			int boardid = board.getBoardid();
			videoMap.put(boardid, videoDao.findLatestVideoByBoardId(boardid,8));
			List<Video> weeklyVideo = new ArrayList<Video>();
			weeklyVideo = videoDao.findWeeklyRankingByBoardIdAndClickCount(boardid);
			if(weeklyVideo==null||weeklyVideo.size()<7){
				weeklyVideo = videoDao.findLatestVideoByBoardId(boardid,7);
			}
			//System.out.println(weeklyVideo.size());
			weeklyVideoMap.put(boardid, weeklyVideo);	
		}
		request.setAttribute("weeklyVideoMap", weeklyVideoMap);
		request.setAttribute("videoMap", videoMap);
		session.setAttribute("listBoard", listBoard);
		//数据测试
		/*Set<Entry<Integer,List<Video>>> es = videoMap.entrySet();
		for(Entry e : es){
			Integer x = Integer.parseInt(e.getKey().toString());
			List<Video> vs = (List<Video>)e.getValue();
			String s ="";
			for(int i =0;i<vs.size();i++){
				s+=(vs.get(i).getVideoid()+"\t");
			}
			System.out.println(x+"\t"+s);
		}*/
		//System.out.println("board:\t"+listBoard.size());
		
		
		
		return SUCCESS;
		
	}

	
}
