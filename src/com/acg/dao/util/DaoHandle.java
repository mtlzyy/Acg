package com.acg.dao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import com.acg.dao.util.bean.ClassInfo;
import com.acg.dao.util.bean.ColumnData;




public class DaoHandle {
	
	/**
	 * 通用的数据增删改方法
	 * @param sql DML语句
	 * @param params 参数
	 */
	public static synchronized void executeUpdate(String sql,Object[] params){
		Connection con = DaoUtil.getConnection();
		PreparedStatement pstmt = null;
		if(con != null){
			try {
				pstmt = con.prepareStatement(sql);
				//注入参数
				if(params != null){
					for(int i = 0; i < params.length; i++){
						pstmt.setObject(i+1, params[i]);
					}
				}
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DaoUtil.close(con, pstmt, null);
			}
			
		}
		
	}
	
	/**
	 * 使用存储过程增删改
	 */
	public static synchronized void callexecute(String sql,Object[] params){
		Connection con = DaoUtil.getConnection();
		CallableStatement cstmt=null;
		if(con != null){
			try {
				cstmt = con.prepareCall(sql);
				if(params != null){
					for(int i = 0; i < params.length; i++){
						cstmt.setObject(i+1, params[i]);
					}
					cstmt.execute();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				DaoUtil.close(con,cstmt, null);
			}		
		}	
	}
	
	/**
	 * 通用的数据查询方法
	 * @return 单行数据
	 */
	public static synchronized <T> T executeQueryForSingle(String sql,Object[] params, Class<T> classObj){
		T t = null;
		Connection con = DaoUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(con != null){
			try {
				pstmt = con.prepareStatement(sql);
				if(params != null){
					for(int i = 0; i < params.length; i++){
						pstmt.setObject(i+1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
				ColumnData[] data = DaoUtil.parseResultSetMetaData(rs.getMetaData());
				ClassInfo info = DaoUtil.parseClass(classObj);
				while(rs.next()){
					t = classObj.newInstance();
					Object value = null;
					for(int i = 0; i < data.length; i++){
						ColumnData column = data[i];
						if(column.getColumnType() == OracleTypes.VARCHAR){
							value = rs.getString(i+1);
						}
						else if(column.getColumnType() == OracleTypes.NUMBER){
							BigDecimal number = rs.getBigDecimal(i+1);
							if(number!=null){
								if(DaoUtil.isDouble(number)){
									value = number.doubleValue();
								}
								else{
									value = number.intValue();
								}
							}
							else{
								value=-1000;
							}
						}
						else if(column.getColumnType() == OracleTypes.DATE){
							value = rs.getTimestamp(i+1);
						}
						else if(column.getColumnType() == OracleTypes.CLOB){
							Clob clob = rs.getClob(i+1);
							BufferedReader reader = new BufferedReader(clob.getCharacterStream());
							StringBuffer buffer = new StringBuffer();
							String s = "";
							while((s = reader.readLine()) != null){
								buffer.append(s);
							}
							reader.close();
							value = buffer.toString();
						}
						for(Field f : info.getFields()){
							if(f.getName().equalsIgnoreCase(column.getColumnName())){
								for(Method m : info.getMethods()){
									if(m.getName().equals("set"+DaoUtil.upperFirstWorld(f.getName()))){
										m.invoke(t, value);
										break;
									}
								}
								break;
							}
						}	
					}//end-for
				}//end-while	
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			finally{
				DaoUtil.close(con, pstmt, rs);
			}
		}
		return t;
	}
	
	
	/**
	 * 通用的数据查询方法
	 * @return 多行数据
	 */
	public static synchronized <T> List<T> executeQueryForMultiple(String sql, Object[] params, Class<T> classObj){
		List<T> list = new ArrayList<T>();
		T t = null;
		Connection con = DaoUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(con != null){
			try {
				pstmt = con.prepareStatement(sql);
				if(params != null){
					for(int i = 0; i < params.length; i++){
						pstmt.setObject(i+1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
				ColumnData[] data = DaoUtil.parseResultSetMetaData(rs.getMetaData());
				ClassInfo info = DaoUtil.parseClass(classObj);
				while(rs.next()){
					t = classObj.newInstance();
					Object value = null;
					for(int i = 0; i < data.length; i++){
						ColumnData column = data[i];
						if(column.getColumnType() == OracleTypes.VARCHAR){
							value = rs.getString(i+1);
						}
						else if(column.getColumnType() == OracleTypes.NUMBER){
							BigDecimal number = rs.getBigDecimal(i+1);
							if(number!=null){
								if(DaoUtil.isDouble(number)){
									value = number.doubleValue();
								}
								else{
									value = number.intValue();
								}
							}
							else{
								value=-1000;
							}
						}
						else if(column.getColumnType() == OracleTypes.DATE){
							value = rs.getTimestamp(i+1);
						}
						else if(column.getColumnType() == OracleTypes.CLOB){
							Clob clob = rs.getClob(i+1);
							BufferedReader reader = new BufferedReader(clob.getCharacterStream());
							StringBuffer buffer = new StringBuffer();
							String s = "";
							while((s = reader.readLine()) != null){
								buffer.append(s);
							}
							reader.close();
							value = buffer.toString();
						}
						for(Field f : info.getFields()){
							if(f.getName().equalsIgnoreCase(column.getColumnName())){
								for(Method m : info.getMethods()){
									if(m.getName().equals("set"+DaoUtil.upperFirstWorld(f.getName()))){
										m.invoke(t, value);
										break;
									}
								}
								break;
							}
						}	
					}//end-for
					list.add(t);
				}//end-while	
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			finally{
				DaoUtil.close(con, pstmt, rs);
			}
		}
		return list;
	} 
	
	/**
	 * 返回单值的查询
	 * @return
	 */
	public static Object findUniqueResult(String sql, Object[] params){
		Object value = null;
		Connection con = DaoUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(con != null){
			try {
				pstmt = con.prepareStatement(sql);
				if(params != null){
					for(int i = 0; i < params.length; i++){
						pstmt.setObject(i+1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
				while(rs.next()){
					value = rs.getObject(1);
				}//end-while	
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			finally{
				DaoUtil.close(con, pstmt, rs);
			}
		}
		return value;
	}
	/**
	 * 通用的数据分页方法
	 * @param <T>
	 * @param sql
	 * @param parmas
	 * @param classObj
	 * @param page
	 * @return
	 */
	public static <T> List<T> findPage(String sql, Object[] params, Class<T> classObj, int page,int size){
		List<T> list = new ArrayList<T>();
		sql = "select * from (select rownum as rid,n.* from ("+sql+") n) where rid between ? and ?";
		T t = null;
		Connection con = DaoUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if(con != null){
			try {
				pstmt = con.prepareStatement(sql);
				if(params != null){
					//循环注入参数
					for(int i = 0; i < params.length; i++){
						pstmt.setObject(i+1, params[i]);
					}
					pstmt.setInt(params.length+1, (page-1)*size+1);
					pstmt.setInt(params.length+2, page*size);
				}
				else{
					pstmt.setInt(1, (page-1)*size+1);
					pstmt.setInt(2, page*size);
				}
				//执行查询
				rs = pstmt.executeQuery();
				//解析结果集的数据结构
				ColumnData[] data = DaoUtil.parseResultSetMetaData(rs.getMetaData());
				//解析映射类的结构
				ClassInfo info = DaoUtil.parseClass(classObj);
				//读取结果集
				while(rs.next()){
					//实例化
					t = classObj.newInstance();
					//每列的值
					Object value = null;
					//根据结果集的数据结构获取每个列的数据
					for(int i = 0; i < data.length; i++){
						ColumnData column = data[i];
						//判断列的数据类型
						//字符
						if(column.getColumnType() == OracleTypes.VARCHAR){
							value = rs.getString(i+1);
						}
						//数值
						else if(column.getColumnType() == OracleTypes.NUMBER){
							BigDecimal number = rs.getBigDecimal(i+1);
							if(number != null){
								//判断是否是浮点值
								if(DaoUtil.isDouble(number)){
									value = number.doubleValue();
								}
								else{
									value = number.intValue();
								}
							}
							else{
								value = -1000;
							}	
						}
						//日期
						else if(column.getColumnType() == OracleTypes.DATE){
							value = rs.getTimestamp(i+1);
						}
						//大字符
						else if(column.getColumnType() == OracleTypes.CLOB){
							Clob clob = rs.getClob(i+1);
							if(clob != null){
								//封装缓冲字符流
								BufferedReader reader = new BufferedReader(clob.getCharacterStream());
								StringBuffer buffer = new StringBuffer();
								String s = "";
								while((s = reader.readLine()) != null){
									buffer.append(s);
								}
								reader.close();
								value = buffer.toString();
							}	
						}
						//判断映射类中是否存在对应的属性
						for(Field f : info.getFields()){
							if(f.getName().equalsIgnoreCase(column.getColumnName())){
								//判断映射类是否存在该属性对应的setter方法
								for(Method m : info.getMethods()){
									if(m.getName().equals("set"+DaoUtil.upperFirstWorld(f.getName()))){
										//如果方法存在则invoke执行
										
										m.invoke(t, value);
										break;
									}
								}
								break;
							}
						}	
					}//end-for
					list.add(t);
				}//end-while	
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				DaoUtil.close(con, pstmt, rs);
			}
		}
		
		return list;
	}
}
