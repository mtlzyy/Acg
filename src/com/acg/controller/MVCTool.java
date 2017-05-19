package com.acg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.mvc.bean.ActionForward;
import com.acg.mvc.bean.IModel;



/**
 * MVC工具类
 * @author Administrator
 *
 */
public class MVCTool {
	/**
	 * 获取模型编号
	 * @param path
	 * @return
	 */
	public static String getModelId(String path){
		return path.substring(path.lastIndexOf("/")+1,path.indexOf(".do"));
	}
	/**
	 * 获取模型
	 * @param path
	 * @return
	 */
	public static IModel getModel(String path){
		//System.out.println(requestPath);
		//根据请求获取模型实例
		String modelId = MVCTool.getModelId(path);
		IModel model = ActionServlet.modelMap.get(modelId);
		return model;
	}
	/**
	 * 根据ActionForward的name解析跳转视图的方法
	 * @param forwardName
	 * @return
	 */
	public synchronized static String parseActionForward(String forwardName,IModel model){
		//获取模型中封装的跳转视图集合
		List<ActionForward> list = model.getActionForwards();
		for(ActionForward forward : list){
			if(forward.getName().equals(forwardName)){
				return forward.getUrl();
			}
		}
		return null;
	}
}
