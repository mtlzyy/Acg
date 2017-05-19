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
		//设定上传内容的字符集
		disk.setHeaderEncoding("utf-8");	
		//创建缓冲临时文件夹
		File tempDirectory=new File("d:/temp");
		if(!tempDirectory.exists())
		{
			tempDirectory.mkdir();
		}	
		//设定缓冲临时文件
		disk.setRepositoryPath("d:/temp");	
		//设定缓冲文件大小  单位字节
		disk.setSizeThreshold(1024*10);
		//设置上传文件大小
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
		//读取请求对象中的输入流获取提交对象集合
		try {
			List<FileItem> fileList=disk.parseRequest(req);	
			//遍历提交的对象
			for(FileItem item : fileList)
			{
				//获取上传文件的路径(仅限于file)
				//System.out.println(item.getName());
				//获取表单元素的name属性
				//System.out.println(item.getFieldName());
				//获取文件大小
				//System.out.println(item.getSize());
				//获取字符串形式的文件内容
				//System.out.println(item.getString());
				//文本框中文乱码需要重构字符串
				//System.out.println(new String(item.getString("iso-8859-1").getBytes("iso-8859-1"),"utf-8"));
				
				//判断文件是否是上传对象(判断FileItem是普通元素类型还是文件域类型,false表示为文件域类型)
				if(!item.isFormField())
				{
					//获取上传文件的输入流
					BufferedInputStream input=new BufferedInputStream(item.getInputStream());
					//获取上传文件的文件名
					String fileName=item.getName().substring(item.getName().lastIndexOf(".")+1);
					//设置上传路径
					String sf = dest.getAbsolutePath()+"\\head.jpg";
					System.out.println("sf:\t"+sf);
					//获取输出流
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
