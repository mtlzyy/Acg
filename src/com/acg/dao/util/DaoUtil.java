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
 * DAO������
 * @author Administrator
 *
 */
public class DaoUtil {
	private static DataSource dataSource;
	/**
	 * ���������ļ�
	 */
	static{
		/**********ʹ��JNDI����Զ�����ݳ�***********/
		//����InitialContext����,���ڷ���Զ����Դ
		try {
			InitialContext ctx = new InitialContext();
			//���Ҳ���ȡԶ����Դ
			dataSource = (DataSource) ctx.lookup("java:comp/env/jndi/mysource");
			System.out.println(dataSource);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��ȡ���ݿ�����
	 * @return
	 */
	public static synchronized Connection getConnection(){
		Connection con = null;
		/***********ʹ��JNDI������ݳ��е�����***********/
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
	 * �ر����ݿ����
	 * @param con ����
	 * @param stmt ������
	 * @param rs �����
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
	 * ����Ԫ����
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
					//��װ�е�����
					column.setColumnName(data.getColumnName(i+1));
					//��װ�е�����
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
	 * �ж������Ƿ��Ǹ���ֵ
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
	 * ������
	 * @param classObj
	 * @return
	 */
	public static ClassInfo parseClass(Class<?> classObj){
		//�洢�������Եļ���
		//��ʽһ
		Set<Field> setF = new HashSet<Field>();
		
		for(Field f : classObj.getDeclaredFields()){
			setF.add(f);
		}
		for(Field f : classObj.getFields()){
			setF.add(f);
		}
		
		//�洢���з���
		Set<Method> setM = new HashSet<Method>();
		for(Method m : classObj.getDeclaredMethods()){
			setM.add(m);
		}
		for(Method m : classObj.getMethods()){
			setM.add(m);
		}
		
		//��װ������Ժͷ���
		ClassInfo info = new ClassInfo();
		Field[] arrayF = new Field[setF.size()];
		//������ת��������
		setF.toArray(arrayF);
		Method[] arrayM = new Method[setM.size()];
		setM.toArray(arrayM);
		
		info.setFields(arrayF);
		info.setMethods(arrayM);		
		return info;
	}
	
	/**
	 * ����ĸ��дת��
	 * @param str
	 * @return
	 */
	public static String upperFirstWorld(String str){
		return str.substring(0,1).toUpperCase().concat(str.substring(1));
	}
}
