package com.acg.dao;

import java.math.BigDecimal;
import java.util.List;

import com.acg.bean.Video;
import com.acg.dao.util.DaoHandle;

public class VideoDao {
	/**
	 * ������Ƶid����Ƶ��Ϣ
	 * @param videoId
	 * @return
	 */
	public Video findVideoByVideoId(int videoId){
		return DaoHandle.executeQueryForSingle("select * from video where videoId=?",new Object[]{videoId},Video.class);
	}
	/**
	 * ���µ��������ǰ��
	 * @param boardId
	 * @return
	 */
/*	public List<Video> findMonthlyRankingByBoardIdAndClickCount(int boardid){	
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardid=? order by clickcount desc ) where rownum<=1 ", new Object[]{boardid}, Video.class);
	}*/
	public List<Video> findMonthlyRankingByBoardIdAndClickCount(int boardid){	
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardid=? and to_char(time_posted,'yyyy-mm')=to_char(sysdate,'yyyy-mm') order by clickcount desc ) where rownum<=7 ", new Object[]{boardid}, Video.class);
	}
	/**
	 * ���ܵ��������ǰ��
	 * @param boardId
	 * @return
	 */
/*	public List<Video> findWeeklyRankingByBoardIdAndClickCount(int boardId){
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardid=? order by clickcount desc) where rownum<=2", new Object[]{boardId}, Video.class);
	}*/
	public List<Video> findWeeklyRankingByBoardIdAndClickCount(int boardId){
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardid=? and time_posted between (trunc(sysdate,'day')+7) and (trunc(sysdate,'day')+1) order by clickcount desc) where rownum<=7", new Object[]{boardId}, Video.class);
	}
	
	/**
	 * ������Ƶǰ��
	 * @param boardId
	 * @return
	 */
	public List<Video> findLatestVideoByBoardId(int boardId,int x){
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardId=? order by time_posted desc )where rownum<= ?", new Object[]{boardId,x}, Video.class);
	}
	/**
	 * �ؼ��ֲ���
	 * @param keyword
	 * @param x
	 * @return
	 */
	public List<Video> findVideoByKeyword(String keyword,int x){
		String sql = "select * from (select * from video where title like '%'||?||'%' order by clickcount) where rownum<=?";
		return DaoHandle.executeQueryForMultiple(sql, new Object[]{keyword,x}, Video.class);
		
	}
	
	/**
	 * �����û�ID��ҳ����
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Video> findVideoByUserIdAndPage(int id,int page,int size){
		return DaoHandle.findPage("select * from video where userid=? order by time_posted desc", new Object[]{id}, Video.class, page, size);
	}
	/**
	 * �������ߵ�ģ��ƥ����Ƶ��ҳ(1����)
	 * @param word
	 * @param page
	 * @return
	 */
	public List<Video> searchVideoByWordAndClickcount(String word,int page){
		return DaoHandle.findPage("select * from Video where title like '%'||?||'%' order by clickcount", new Object[]{word}, Video.class, page, 20);
	}
	/**
	 * ���µ�ģ��ƥ����Ƶ��ҳ(1����)
	 * @param word
	 * @param page
	 * @return
	 */
	public List<Video> searchVideoByWordAndTime_posted(String word,int page){
		return DaoHandle.findPage("select * from Video where title like '%'||?||'%' order by time_posted", new Object[]{word}, Video.class, page, 20);
	}
	
	/**
	 * �������ߵ�ģ��ƥ����Ƶ��ҳ(1��������)
	 * @param sql
	 * @param page
	 * @return
	 */
	public List<Video> searchMoreByClickcount(int length,String word,int page){
		String sql=null;
		for(int i=1;i<length+1;i++){
			if(i==1){ 
				sql="select * from Video where title like '%"+word.substring(i-1,i)+"%' order by clickcount";
			}else if(i==length){
				return DaoHandle.findPage("select * from ("+sql+") where title like '%"+word.substring(i-1,i)+"%' order by clickcount", null, Video.class, page, 20);
			}else{
				sql="select * from ("+sql+") where title like  '%"+word.substring(i-1,i)+"%' order by clickcount";
			}
		}
		return null;
		}
	/**
	 * ���µ�ģ��ƥ����Ƶ��ҳ(1��������)
	 * @param sql
	 * @param page
	 * @return
	 */
	public List<Video> searchMoreByTime_posted(int length,String word,int page){
		String sql=null;
		for(int i=1;i<length+1;i++){
			if(i==1){
				sql="select * from Video where title like '%"+word.substring(i-1,i)+"%' order by time_posted";
			}else if(i==length){
				return DaoHandle.findPage("select * from ("+sql+") where title like '%"+word.substring(i-1,i)+"%' order by time_posted", null, Video.class, page, 20);
			}else{
				sql="select * from ("+sql+") where title like  '%"+word.substring(i-1,i)+"%' order by time_posted";
			}
		}
		return null;
	}
	/**
	 * ��ȡ�Ե������߲����rownum
	 * @param word
	 * @param length
	 * @return
	 */
	public int searchNumByClickcount(String word,int length){
		
		String sql=null;
		for(int i=1;i<length+1;i++){
			if(i==1){ 
				sql="select * from video where title like '%"+word.substring(i-1,i)+"%' order by clickcount";
			}else if(i==length){
				sql="select * from ("+sql+") where title like '%"+word.substring(i-1,i)+"%' order by clickcount";
			}else{
				sql="select * from ("+sql+") where title like  '%"+word.substring(i-1,i)+"%' order by clickcount";
			}
		}
		BigDecimal b=(BigDecimal) DaoHandle.findUniqueResult("select max(rownum) from(select rownum,n.* from ("+sql+")n)", null);
		return b.intValue() ;
	}
	/**
	 * ��ȡ������ʱ������rownum
	 * @param word
	 * @param length
	 * @return
	 */
	public int searchNumByTime_posted(String word,int length){
		
		String sql=null;
		for(int i=1;i<length+1;i++){
			if(i==1){ 
				sql="select * from video where title like '%"+word.substring(i-1,i)+"%' order by time_posted";
			}else if(i==length){
				sql="select * from ("+sql+") where title like '%"+word.substring(i-1,i)+"%' order by time_posted";
			}else{
				sql="select * from ("+sql+") where title like  '%"+word.substring(i-1,i)+"%' order by time_posted";
			}
		}
		BigDecimal b= (BigDecimal) DaoHandle.findUniqueResult("select max(rownum) from(select rownum,n.* from ("+sql+")n)", null);
		return b.intValue();
	}
	
	/**�����û�ID����Ͷ������
	 * @return
	 */
	public int findVideoCount(int id){
		return ((BigDecimal)DaoHandle.findUniqueResult("select count(*) from video where userid=?", new Object[]{id})).intValue();
	}
	/**
	 * ���ݰ��id�����µ��߸���Ƶ
	 * @param boardid
	 * @return
	 */
	public List<Video> findVideoByBoardidAndTime_posted(int boardid){
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardid=? order by time_posted desc)where rownum<8", new Object[]{boardid}, Video.class);
	}
	/**
	 * ���ݰ��id���������10����Ƶ
	 * @param boardid
	 * @return
	 */
	public List<Video> findVideoByBoardidAndClickcount(int boardid){
		return DaoHandle.executeQueryForMultiple("select * from(select * from video where boardid=? order by clickcount desc)where rownum<11", new Object[]{boardid}, Video.class);
	}
	/**
	 * �����û�ID�����ղ���Ƶ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List<Video> findByUsersFavourites(int id,int page,int size){
		String sql="select video.videoid,userid,boardid,time_posted,clickcount,url,title,photo from video inner join (select videoid from favourites where userid = ? order by timeadded desc) n on video.videoid=n.videoid";
		return DaoHandle.findPage(sql, new Object[]{id}, Video.class, page, size);
	}
	
	/**
	 * �����û�ID������ʷ��Ƶ��ϸ��Ϣ
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Video> findByUsersHistory(int id,int page,int size){
		String sql="select video.videoid,userid,boardid,time_posted,clickcount,url,title,photo from video inner join (select videoid from history where userid = ? order by time_created desc) n on video.videoid=n.videoid";
		return DaoHandle.findPage(sql, new Object[]{id}, Video.class, page, size);
	}
	
	/**
	 * ������Ƶ
	 * @param userid
	 * @param boardid
	 * @param url
	 * @param photo
	 * @param title
	 */
	public void postVideo(int userid,int boardid,String url, String photo,String title){
		String sql = "insert into video values(videoid.nextval,?,?,sysdate,0,?,?,?)";
		Object[] obj = new Object[]{userid,boardid,url,title,photo};
		DaoHandle.executeUpdate(sql, obj);
	}
	
	/**
	 * �ҵ�������ӵ�video
	 * @return
	 */
	public Video findTheLatestVideo(){
		return DaoHandle.executeQueryForSingle("select * from (select * from video order by time_posted desc) where rownum=1", null, Video.class);
	}
	
	public void updateClickCountAndEtc(int videoid){
		String sql = "update video set clickcount = clickcount + 1 where videoid = ?";
		DaoHandle.executeUpdate(sql, new Object[]{videoid});
		DaoHandle.callexecute("call pointsup(?,2)", new Object[]{videoid});
	}
}
