package com.acg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.acg.mvc.bean.ActionForward;
import com.acg.mvc.bean.IModel;



/**
 * MVC������
 * @author Administrator
 *
 */
public class MVCTool {
	/**
	 * ��ȡģ�ͱ��
	 * @param path
	 * @return
	 */
	public static String getModelId(String path){
		return path.substring(path.lastIndexOf("/")+1,path.indexOf(".do"));
	}
	/**
	 * ��ȡģ��
	 * @param path
	 * @return
	 */
	public static IModel getModel(String path){
		//System.out.println(requestPath);
		//���������ȡģ��ʵ��
		String modelId = MVCTool.getModelId(path);
		IModel model = ActionServlet.modelMap.get(modelId);
		return model;
	}
	/**
	 * ����ActionForward��name������ת��ͼ�ķ���
	 * @param forwardName
	 * @return
	 */
	public synchronized static String parseActionForward(String forwardName,IModel model){
		//��ȡģ���з�װ����ת��ͼ����
		List<ActionForward> list = model.getActionForwards();
		for(ActionForward forward : list){
			if(forward.getName().equals(forwardName)){
				return forward.getUrl();
			}
		}
		return null;
	}
}
