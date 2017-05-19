package com.acg.dao;

import java.math.BigDecimal;

import com.acg.bean.Favourites;
import com.acg.dao.util.DaoHandle;

public class FavouritesDao {
	/**
	 * 根据收藏id找收藏表其他信息
	 * @param fid
	 * @return
	 */
	public Favourites findFavouritesByFid(int fId){
		return DaoHandle.executeQueryForSingle("select * from favourites where fid=?",new Object[]{fId},Favourites.class);
	}
	
	/**根据用户ID查找收藏总量
	 * @param id
	 * @return
	 */
	public int findFavouritesCount(int id){
		return ((BigDecimal)DaoHandle.findUniqueResult("select count(*) from Favourites where userid=?", new Object[]{id})).intValue();
	}
	/**
	 * 添加收藏表
	 * @param videoId
	 * @param userId
	 */
	public void addFavourites(int videoId,int userId){
		DaoHandle.executeUpdate("insert into favourites values(fid.nextval,?,?,sysdate)", new Object[]{videoId,userId});
	}
	/**
	 * 根据videoId和userId查找收藏表
	 * @param videoId
	 * @param userId
	 * @return
	 */
	public Favourites findFavouritesByVideoIdAndUserId(int videoId,int userId){
		return DaoHandle.executeQueryForSingle("select *from favourites where videoid=? and userid=?", new Object[]{videoId,userId}, Favourites.class);
	}
	/**
	 * 根据videoId和userId删除收藏表
	 * @param videoId
	 * @param userId
	 */
	public void removeFavourites(int videoId,int userId){
		DaoHandle.executeUpdate("delete from favourites where videoid=? and userid=?", new Object[]{videoId,userId});
	}
	
}
