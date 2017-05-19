package com.acg.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import com.acg.bean.Users;
import com.acg.dao.UsersDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;
import com.acg.util.DateUtil;

public class HeadUploadModel extends GenericModel{
	private String imgFile;

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	
	@Override
	public String execute() {
		DiskFileUpload disk=new DiskFileUpload();	
		//�趨�ϴ����ݵ��ַ���
		disk.setHeaderEncoding("utf-8");	
		//����������ʱ�ļ���
		File tempDirectory=new File("d:/temp");
		if(!tempDirectory.exists())
		{
			tempDirectory.mkdir();
		}	
		//�趨������ʱ�ļ�
		disk.setRepositoryPath("d:/temp");	
		//�趨�����ļ���С  ��λ�ֽ�
		disk.setSizeThreshold(1024*10);
		//�����ϴ��ļ���С
		disk.setSizeMax(1024*1024*100);
		HttpSession ss= ModelSupport.getSession();
		Users u = (Users)ss.getAttribute("loginUser");
		int userid = u.getUserid();
		System.out.println("userid:\t"+userid);		
		String path = ss.getServletContext().getRealPath("/");
		File dest = new File(path+"\\image\\head\\"+userid);
		if(!dest.exists()){
			dest.mkdir();
		}
		HttpServletRequest req = ModelSupport.getRequest();
		//��ȡ��������е���������ȡ�ύ���󼯺�
		try {
			List<FileItem> fileList=disk.parseRequest(req);	
			//�����ύ�Ķ���
			for(FileItem item : fileList)
			{
				//��ȡ�ϴ��ļ���·��(������file)
				//System.out.println(item.getName());
				//��ȡ��Ԫ�ص�name����
				//System.out.println(item.getFieldName());
				//��ȡ�ļ���С
				//System.out.println(item.getSize());
				//��ȡ�ַ�����ʽ���ļ�����
				//System.out.println(item.getString());
				//�ı�������������Ҫ�ع��ַ���
				//System.out.println(new String(item.getString("iso-8859-1").getBytes("iso-8859-1"),"utf-8"));
				
				//�ж��ļ��Ƿ����ϴ�����(�ж�FileItem����ͨԪ�����ͻ����ļ�������,false��ʾΪ�ļ�������)
				if(!item.isFormField())
				{
					//��ȡ�ϴ��ļ���������
					BufferedInputStream input=new BufferedInputStream(item.getInputStream());
					//��ȡ�ϴ��ļ����ļ���
					String fileName=item.getName().substring(item.getName().lastIndexOf(".")+1);
					//�����ϴ�·��
					String sf = dest.getAbsolutePath()+"\\head.jpg";
					System.out.println("sf:\t"+sf);
					//��ȡ�����
					BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream(new File(sf)));
					
					int len;
					byte[] buffer = new byte[1024];
					
					while((len=input.read(buffer))!=-1)
					{
						output.write(buffer,0,len);
					}
					
					output.flush();
					output.close();
					input.close();
					
				}
				UsersDao udao = new UsersDao();
				udao.updatePhoto(userid, userid+"/head.jpg");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
