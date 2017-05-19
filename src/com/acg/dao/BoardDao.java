package com.acg.dao;

import java.util.List;

import com.acg.bean.Board;
import com.acg.dao.util.DaoHandle;

public class BoardDao {
	/**
	 * ���ݰ��id�Ұ���������Ϣ
	 * @param boardId
	 * @return
	 */
	public Board findBoardByBoardId(int boardId){
		return DaoHandle.executeQueryForSingle("select * from board where boardId=?",new Object[]{boardId},Board.class);
	}
	/**
	 * �������а��
	 * @return
	 */
	public List<Board> findBoard(){
		return DaoHandle.executeQueryForMultiple("select * from board", null, Board.class);
	}
	
}
