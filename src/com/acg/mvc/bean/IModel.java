package com.acg.mvc.bean;

import java.util.List;


/**
 * 模型的通用接口
 * @author Administrator
 *
 */
public interface IModel {
	/**
	 * 处理业务的方法
	 * @return
	 */
	public String execute();
	
	/**
	 * 获取所有视图跳转对象
	 * @return
	 */
	public List<ActionForward> getActionForwards();
}
