package com.acg.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.acg.bean.Board;
import com.acg.bean.Comments;
import com.acg.bean.History;
import com.acg.bean.Reply;
import com.acg.bean.Users;
import com.acg.bean.Video;
import com.acg.dao.BoardDao;
import com.acg.dao.CommentsDao;
import com.acg.dao.FavouritesDao;
import com.acg.dao.HistoryDao;
import com.acg.dao.ReplyDao;
import com.acg.dao.UsersDao;
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;

public class VideoModel extends GenericModel{
	//private String userId;
	private String videoId;
	private String pageNo;
	//private String pageNo2;
	//private String commentId;
	//private String click;
	@Override
	public String execute() {
		HttpServletRequest request=ModelSupport.getRequest();
		int id=Integer.parseInt(videoId);
		UsersDao userDao=new UsersDao();
		VideoDao videoDao=new VideoDao();
		CommentsDao commentsDao=new CommentsDao();
		ReplyDao replyDao=new ReplyDao();
		BoardDao boardDao=new BoardDao();
		//
		FavouritesDao favouriteDao=new FavouritesDao();
		Video video=videoDao.findVideoByVideoId(id);
		Users user=userDao.findUserByUserId(video.getUserid());
		Board board=boardDao.findBoardByBoardId(video.getBoardid());
		request.setAttribute("video", video);
		request.setAttribute("user", user);
		request.setAttribute("board", board);
		//测试用的session
		HttpSession session=ModelSupport.getSession();
		//Users loginUser=userDao.findUserByUserId(11);
		//System.out.println(loginUser.getUserName());
		//session.setAttribute("loginUser",loginUser);
		//判断是否收藏，collect=0为已收藏，1为未收藏
		Users loginUser=(Users) session.getAttribute("loginUser");
		if(loginUser!=null){
			if(favouriteDao.findFavouritesByVideoIdAndUserId(id, loginUser.getUserid())!=null){
				request.setAttribute("collect", 0);
			}else{
				request.setAttribute("collect", 1);
			}
		}else{
			request.setAttribute("collect", -1);
		}
		int page;
		//int page2;
		//int cid;
		//int isClick;
		if(pageNo==null){
			page=1;
			videoDao.updateClickCountAndEtc(id);
			if(loginUser!=null){
				HistoryDao historyDao = new HistoryDao();
				History history = historyDao.findByUserAndVideo(loginUser.getUserid(), id);
				if(history!=null){
					historyDao.updateHistory(loginUser.getUserid(), id);
				}
				else{
					historyDao.addHistory(loginUser.getUserid(), id);
				}
			}
			
		}else{
			page=Integer.parseInt(pageNo);
		}
//		if(pageNo2==null){
//			page2=1;
//		}else{
//			page2=Integer.parseInt(pageNo2);
//		}
//		if(commentId==null){
//			cid=-1;
//		}else{
//			cid=Integer.parseInt(commentId);
//		}
//		if(click==null){
//			isClick=-1;
//		}else{
//			isClick=Integer.parseInt(click);
//		}
		request.setAttribute("pageNo", page);
		//request.setAttribute("click", isClick);
		List<Comments>commentsList=commentsDao.findCommentsPageByVideoId(id, page);
		int total=commentsDao.findTotalCountByVideoId(id);
		int max=total/5;
		if(total%5!=0){
			max++;
		}
		request.setAttribute("max", max);
		for(int i=0;i<commentsList.size();i++){
			//获得楼层
			int floor=(page-1)*5+i+1;
			commentsList.get(i).setFloor(floor);
			//获得评论用户
			Users commentUser=userDao.findUserByUserId(commentsList.get(i).getUserid());
			commentsList.get(i).setUser(commentUser);
			//查找回复
			List<Reply> replyList=replyDao.findReplyPageByCommentId(commentsList.get(i).getCommentid(), 1);
//			if(cid==commentsList.get(i).getCommentid()){
//				replyList=replyDao.findReplyPageByCommentId(cid, page2);
//			}else{
//				replyList=replyDao.findReplyPageByCommentId(commentsList.get(i).getCommentid(),commentsList.get(i).getPageNo());
//			}
			//回复总数和页数
			int total2=replyDao.findTotalCountByCommentId(commentsList.get(i).getCommentid());
			int max2=total2/5;
			if(total2%5!=0){
				max2++;
			}
			for(int j=0;j<replyList.size();j++){
				Users replyUser=userDao.findUserByUserId(replyList.get(j).getUserid());
				replyList.get(j).setUser(replyUser);
			}
			//展示前三个回复
//			if(replyList.size()<3){
//				for(int j=0;j<replyList.size();j++){
//					Users replyUser=userDao.findUserByUserId(replyList.get(j).getUserid());
//					replyList.get(j).setUser(replyUser);
//					showList.add(replyList.get(j));
//				}
//			}else{
//				for(int j=0;j<3;j++){
//					Users replyUser=userDao.findUserByUserId(replyList.get(j).getUserid());
//					replyList.get(j).setUser(replyUser);
//					showList.add(replyList.get(j));
//				}
//			}
			commentsList.get(i).setReplyList(replyList);
			commentsList.get(i).setTotal(total2);
			commentsList.get(i).setMaxPage(max2);
			//commentsList.get(i).setReplyList(showList);
		}
		request.setAttribute("commentsList", commentsList);
		return SUCCESS;
	}
	//public String getUserId() {
		//return userId;
	//}
	//public void setUserId(String userId) {
		//this.userId = userId;
	//}
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
//	public String getPageNo2() {
//		return pageNo2;
//	}
//	public void setPageNo2(String pageNo2) {
//		this.pageNo2 = pageNo2;
//	}
//	public String getCommentId() {
//		return commentId;
//	}
//	public void setCommentId(String commentId) {
//		this.commentId = commentId;
//	}
//	public String getClick() {
//		return click;
//	}
//	public void setClick(String click) {
//		this.click = click;
//	}
	
}
