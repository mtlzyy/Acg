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
 * ���Ŀ�����
 * @author Administrator
 *
 */
public class ActionServlet extends HttpServlet{
	protected static Map<String, IModel> modelMap;
	//�����ļ���
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
		//��ȡ����
		String requestPath = req.getRequestURI();
		IModel model = MVCTool.getModel(requestPath);
		//ʹ��ģ�Ͷ�����ҵ���߼�
		String actionForwardName = model.execute();
		//����ͼ���н���
		String url = MVCTool.parseActionForward(actionForwardName, model);
		//���ݽ����ĵ�ַ������ͼ��Ӧ
		req.getRequestDispatcher(url).forward(req, resp);
	}
	
	/**
	 * ���ó�ʼ�������ݶ�ȡ
	 */
	private void initConfig(){
		//��ȡ��ǰ���Ŀ������Ĳ���
		String configLocation = getInitParameter("configLocation");
		if(configLocation != null){
			config = configLocation;
		}
	}
	
	/**
	 * ����MVC�����ļ��ķ���
	 * @throws DocumentException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void parseConfig() throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		//����������
		SAXReader reader = new SAXReader();
		//��ȡ��Ŀ·��
		String path = ActionServlet.class.getResource("/").getPath();
		//System.out.println(path);
		//�����ĵ�
		Document document = reader.read(path+config);
		//��ȡ���ڵ�
		Element root = document.getRootElement();
		//��ȡ����ģ�ͽڵ�
		List<Element> modelList = root.elements("model");
		if(modelList != null){
			//����
			for(Element modelElement : modelList){
				String id = "";
				IModel model = null;
				
				//��ȡģ�ͽڵ��id����ֵ
				Attribute idAtt = modelElement.attribute("id");
				if(idAtt != null){
					id = idAtt.getValue();
				}
				//��ȡmodel-class�ӽڵ�
				Element classElement = modelElement.element("model-class");
				
				//��ȡclass�ı�ǩ��
				if(classElement != null){
					String modelClassStr = classElement.getText();
					//�������model-class
					Class<IModel> modelClass = (Class<IModel>) Class.forName(modelClassStr);
					//�����ȡʵ��
					model = modelClass.newInstance();
				}
				//��������forward��ǩ
				List<Element> forwardList = modelElement.elements("forward");
				if(forwardList != null){
					//����
					for(Element forwardElement : forwardList){
						String name = "";
						String url = "";
						//����forward�ڵ��name����
						Attribute nameAttr = forwardElement.attribute("name");
						if(nameAttr != null){
							name = nameAttr.getValue();
						}
						//����forward�ڵ��url
						String body = forwardElement.getText();
						if(body != null && !body.trim().equals("")){
							url = body;
						}
						//��װforward����
						ActionForward forward = new ActionForward(name, url);
						//��forward�����װ��model��
						model.getActionForwards().add(forward);
					}
				}
				
				
				//���������ģ�ͷ�װ������
				modelMap.put(id, model);
			}
		}
		
		
	}

	@Override
	public void init() throws ServletException {
		//����ģ�ͼ���
		modelMap = new LinkedHashMap<String, IModel>();
		//��ʼ�����ò���
		RemoveTempUserThread rtut = new RemoveTempUserThread();
		initConfig();
		try {
			//���������ļ�
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
