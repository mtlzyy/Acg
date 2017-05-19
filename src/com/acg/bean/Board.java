package com.acg.bean;

import java.io.Serializable;

public class Board implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int boardid;
	private String boardName;
	public int getBoardid() {
		return boardid;
	}
	public void setBoardid(int boardid) {
		this.boardid = boardid;
	}
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	
}
