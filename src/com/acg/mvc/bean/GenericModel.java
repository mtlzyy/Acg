package com.acg.mvc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用模型类
 * @author Administrator
 *
 */
public class GenericModel implements IModel{
	
	private List<ActionForward> forwords;
	
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public GenericModel(){
		forwords = new ArrayList<ActionForward>();
	}

	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ActionForward> getActionForwards() {
		return forwords;
	}
	
}
