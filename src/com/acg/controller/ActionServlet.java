package com.acg.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import com.acg.mvc.bean.ActionForward;
import com.acg.mvc.bean.IModel;
import com.acg.thread.RemoveTempUserThread;

/**
 * 核心控制器
 * @author Administrator
 *
 */
public class ActionServlet extends HttpServlet{
	protected static Map<String, IModel> modelMap;
	//配置文件名
	private String config = CONFIG;
	public static final String CONFIG = "mvc_config.xml";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//读取请求
		String requestPath = req.getRequestURI();
		IModel model = MVCTool.getModel(requestPath);
		//使用模型对象处理业务逻辑
		String actionForwardName = model.execute();
		//对视图进行解析
		String url = MVCTool.parseActionForward(actionForwardName, model);
		//根据解析的地址进行视图响应
		req.getRequestDispatcher(url).forward(req, resp);
	}
	
	/**
	 * 配置初始化的数据读取
	 */
	private void initConfig(){
		//读取当前核心控制器的参数
		String configLocation = getInitParameter("configLocation");
		if(configLocation != null){
			config = configLocation;
		}
	}
	
	/**
	 * 解析MVC配置文件的方法
	 * @throws DocumentException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void parseConfig() throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		//创建解析器
		SAXReader reader = new SAXReader();
		//获取项目路径
		String path = ActionServlet.class.getResource("/").getPath();
		//System.out.println(path);
		//加载文档
		Document document = reader.read(path+config);
		//获取根节点
		Element root = document.getRootElement();
		//获取所有模型节点
		List<Element> modelList = root.elements("model");
		if(modelList != null){
			//遍历
			for(Element modelElement : modelList){
				String id = "";
				IModel model = null;
				
				//获取模型节点的id属性值
				Attribute idAtt = modelElement.attribute("id");
				if(idAtt != null){
					id = idAtt.getValue();
				}
				//获取model-class子节点
				Element classElement = modelElement.element("model-class");
				
				//获取class的标签体
				if(classElement != null){
					String modelClassStr = classElement.getText();
					//反射加载model-class
					Class<IModel> modelClass = (Class<IModel>) Class.forName(modelClassStr);
					//反射获取实例
					model = modelClass.newInstance();
				}
				//解析所有forward标签
				List<Element> forwardList = modelElement.elements("forward");
				if(forwardList != null){
					//遍历
					for(Element forwardElement : forwardList){
						String name = "";
						String url = "";
						//解析forward节点的name属性
						Attribute nameAttr = forwardElement.attribute("name");
						if(nameAttr != null){
							name = nameAttr.getValue();
						}
						//解析forward节点的url
						String body = forwardElement.getText();
						if(body != null && !body.trim().equals("")){
							url = body;
						}
						//封装forward集合
						ActionForward forward = new ActionForward(name, url);
						//将forward对象封装至model中
						model.getActionForwards().add(forward);
					}
				}
				
				
				//将解析后的模型封装至集合
				modelMap.put(id, model);
			}
		}
		
		
	}

	@Override
	public void init() throws ServletException {
		//创建模型集合
		modelMap = new LinkedHashMap<String, IModel>();
		//初始化配置参数
		RemoveTempUserThread rtut = new RemoveTempUserThread();
		initConfig();
		try {
			//解析配置文件
			parseConfig();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rtut.start();	
	}
	
	
}
