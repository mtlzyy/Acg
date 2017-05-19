package com.acg.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.acg.dao.util.bean.ClassInfo;
import com.acg.dao.util.bean.ColumnData;

/**
 * DAO工具类
 * @author Administrator
 *
 */
public class DaoUtil {
	private static DataSource dataSource;
	/**
	 * 加载配置文件
	 */
	static{
		/**********使用JNDI访问远程数据池***********/
		//创建InitialContext对象,用于访问远程资源
		try {
			InitialContext ctx = new InitialContext();
			//查找并获取远程资源
			dataSource = (DataSource) ctx.lookup("java:comp/env/jndi/mysource");
			System.out.println(dataSource);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取数据库连接
	 * @return
	 */
	public static synchronized Connection getConnection(){
		Connection con = null;
		/***********使用JNDI获得数据池中的连接***********/
		try {
			if(dataSource != null){
				con = dataSource.getConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	/**
	 * 关闭数据库对象
	 * @param con 连接
	 * @param stmt 处理器
	 * @param rs 结果集
	 */
	public static synchronized void close(Connection con, Statement stmt, ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();		}
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 解析元数据
	 * @param data
	 * @return
	 */
	public static ColumnData[] parseResultSetMetaData(ResultSetMetaData data){
		ColumnData[] columnData = null;
		if(data != null){
			try {
				columnData = new ColumnData[data.getColumnCount()];
				for(int i = 0; i < data.getColumnCount(); i++){
					ColumnData column = new ColumnData();
					//封装列的名称
					column.setColumnName(data.getColumnName(i+1));
					//封装列的类型
					column.setColumnType(data.getColumnType(i+1));
					columnData[i] = column;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columnData;
	}
	
	/**
	 * 判断数据是否是浮点值
	 * @param number
	 * @return
	 */
	public static boolean isDouble(BigDecimal number){
		boolean flag = false;
		if(number != null){
			if(number.toString().indexOf(".") != -1){
				flag = true;
			}
		}           
		return flag;
	}
	/**
	 * 解析类
	 * @param classObj
	 * @return
	 */
	public static ClassInfo parseClass(Class<?> classObj){
		//存储所有属性的集合
		//方式一
		Set<Field> setF = new HashSet<Field>();
		
		for(Field f : classObj.getDeclaredFields()){
			setF.add(f);
		}
		for(Field f : classObj.getFields()){
			setF.add(f);
		}
		
		//存储所有方法
		Set<Method> setM = new HashSet<Method>();
		for(Method m : classObj.getDeclaredMethods()){
			setM.add(m);
		}
		for(Method m : classObj.getMethods()){
			setM.add(m);
		}
		
		//封装类的属性和方法
		ClassInfo info = new ClassInfo();
		Field[] arrayF = new Field[setF.size()];
		//将集合转换成数组
		setF.toArray(arrayF);
		Method[] arrayM = new Method[setM.size()];
		setM.toArray(arrayM);
		
		info.setFields(arrayF);
		info.setMethods(arrayM);		
		return info;
	}
	
	/**
	 * 首字母大写转换
	 * @param str
	 * @return
	 */
	public static String upperFirstWorld(String str){
		return str.substring(0,1).toUpperCase().concat(str.substring(1));
	}
}
