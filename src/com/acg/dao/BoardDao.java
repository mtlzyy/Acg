package com.acg.dao;

import java.util.List;

import com.acg.bean.Board;
import com.acg.dao.util.DaoHandle;

public class BoardDao {
	/**
	 * 根据板块id找板块表其他信息
	 * @param boardId
	 * @return
	 */
	public Board findBoardByBoardId(int boardId){
		return DaoHandle.executeQueryForSingle("select * from board where boardId=?",new Object[]{boardId},Board.class);
	}
	/**
	 * 查找所有版块
	 * @return
	 */
	public List<Board> findBoard(){
		return DaoHandle.executeQueryForMultiple("select * from board", null, Board.class);
	}
	
}
