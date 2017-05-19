package com.acg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.acg.bean.Users;
import com.acg.dao.UsersDao;
import com.acg.mvc.bean.ModelSupport;


public class ModelSupportFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//记录当次请求
		ModelSupport.setRequest((HttpServletRequest)request);
		ModelSupport.setResponse((HttpServletResponse)response);
		Cookie[] cks = ((HttpServletRequest)request).getCookies();
		HttpSession  session = ((HttpServletRequest)request).getSession();
		String logout =request.getParameter("isLogOut");
		if(cks!=null&&logout==null){
			UsersDao udao = new UsersDao();
			for(Cookie c:cks){
				if(c.getName().equals("userName")){
					Users u = udao.findUserByUserName(c.getValue());
					if(u!=null){
						session.setAttribute("loginUser", u);
					}				
				}
			}
		}
		else{
			if(session.getAttribute("loginUSer")!=null){
				session.removeAttribute("loginUser");
			}
		}
		chain.doFilter(request, response);
		//销毁请求
		ModelSupport.destroy();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
