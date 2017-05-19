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
	 * ͨ�õ�������ɾ�ķ���
	 * @param sql DML���
	 * @param params ����
	 */
	public static synchronized void executeUpdate(String sql,Object[] params){
		Connection con = DaoUtil.getConnection();
		PreparedStatement pstmt = null;
		if(con != null){
			try {
				pstmt = con.prepareStatement(sql);
				//ע�����
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
	 * ʹ�ô洢������ɾ��
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
	 * ͨ�õ����ݲ�ѯ����
	 * @return ��������
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
	 * ͨ�õ����ݲ�ѯ����
	 * @return ��������
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
	 * ���ص�ֵ�Ĳ�ѯ
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
	 * ͨ�õ����ݷ�ҳ����
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
					//ѭ��ע�����
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
				//ִ�в�ѯ
				rs = pstmt.executeQuery();
				//��������������ݽṹ
				ColumnData[] data = DaoUtil.parseResultSetMetaData(rs.getMetaData());
				//����ӳ����Ľṹ
				ClassInfo info = DaoUtil.parseClass(classObj);
				//��ȡ�����
				while(rs.next()){
					//ʵ����
					t = classObj.newInstance();
					//ÿ�е�ֵ
					Object value = null;
					//���ݽ���������ݽṹ��ȡÿ���е�����
					for(int i = 0; i < data.length; i++){
						ColumnData column = data[i];
						//�ж��е���������
						//�ַ�
						if(column.getColumnType() == OracleTypes.VARCHAR){
							value = rs.getString(i+1);
						}
						//��ֵ
						else if(column.getColumnType() == OracleTypes.NUMBER){
							BigDecimal number = rs.getBigDecimal(i+1);
							if(number != null){
								//�ж��Ƿ��Ǹ���ֵ
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
						//����
						else if(column.getColumnType() == OracleTypes.DATE){
							value = rs.getTimestamp(i+1);
						}
						//���ַ�
						else if(column.getColumnType() == OracleTypes.CLOB){
							Clob clob = rs.getClob(i+1);
							if(clob != null){
								//��װ�����ַ���
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
						//�ж�ӳ�������Ƿ���ڶ�Ӧ������
						for(Field f : info.getFields()){
							if(f.getName().equalsIgnoreCase(column.getColumnName())){
								//�ж�ӳ�����Ƿ���ڸ����Զ�Ӧ��setter����
								for(Method m : info.getMethods()){
									if(m.getName().equals("set"+DaoUtil.upperFirstWorld(f.getName()))){
										//�������������invokeִ��
										
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
