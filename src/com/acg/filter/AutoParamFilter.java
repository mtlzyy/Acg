package com.acg.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.acg.controller.MVCTool;
import com.acg.mvc.bean.IModel;
import com.acg.util.CommonUtil;


/**
 * 注参过滤器
 * @author Administrator
 *
 */
public class AutoParamFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//获取当前请求要分发的模型
		String path = ((HttpServletRequest)request).getRequestURI();
		IModel model = MVCTool.getModel(path);
		//反射获取模型的字节码对象
		Class<IModel> classModel = (Class<IModel>) model.getClass();
		//获取模型对应属性的setter方法
		//Method[] methods = classModel.getDeclaredMethods();
		/*********获取请求中的所有参数*********/
		Enumeration<String> names = request.getParameterNames();
		//迭代
		while(names.hasMoreElements()){
			//获取请求的键名
			String key = names.nextElement();
			//根据键名获取请求中的参数值
			String value = request.getParameter(key);
			try {
				
				Method[] methods =classModel.getDeclaredMethods();
				for(Method method:methods){
					if(method.getName().equals("set"+CommonUtil.parseFirstUpper(key))){
						method.invoke(model, value);
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//继续跳转
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
