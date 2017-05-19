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
import com.acg.dao.VideoDao;
import com.acg.mvc.bean.GenericModel;
import com.acg.mvc.bean.ModelSupport;
import com.acg.util.DateUtil;

public class UploadModel extends GenericModel{
	private String title;
	private String boardid;
	private String imgFile;
	private String videoFile;
	
	
	public String getImgFile() {
		return imgFile;
	}
	public String getVideoFile() {
		return videoFile;
	}
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}
	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}

	@Override
	public String execute() {
		//是否上传成功的标识
		boolean isSuccess=true;	
		//创建文件上传对象
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
		String timeCode = DateUtil.formatDate2(new Date());
		
		String path = ss.getServletContext().getRealPath("/");
		File primaryDest = new File(path+"\\upload\\"+userid);
		if(!primaryDest.exists()){
			primaryDest.mkdir();
		}
		File dest = new File(primaryDest+"\\"+timeCode);
		dest.mkdir();
		HttpServletRequest req = ModelSupport.getRequest();
		Map<String,String> uploadMap = new HashMap<String, String>();
		uploadMap.put("title", title);
		//读取请求对象中的输入流获取提交对象集合
		try {
			List<FileItem> fileList=disk.parseRequest(req);	
			//遍历提交的对象
			for(FileItem item : fileList)
			{	
				if(!item.isFormField())
				{
					//获取上传文件的输入流
					BufferedInputStream input=new BufferedInputStream(item.getInputStream());
					
					//获取上传文件的文件名
					String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);
					
					//获取项目绝对路径
					
					//设置上传路径
					String sf = dest.getAbsolutePath()+"\\"+fileName;
					File f = new File(sf);
					System.out.println(sf);
					
									
					//获取输出流
					BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream(new File(sf)));
					String str = "upload/"+userid+"/"+timeCode+"/"+fileName;
					uploadMap.put(item.getFieldName(), str);
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
				else{
					if(item.getFieldName().equals("boardid")){
						boardid = item.getString();
					}
					if(item.getFieldName().equals("title")){
						title = item.getString();
					}
				}
			}
		} catch (FileNotFoundException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (FileUploadException e) {
			isSuccess = false;
			e.printStackTrace();
		} catch (IOException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		VideoDao vdao = new VideoDao();
		int b = Integer.parseInt(boardid);
		String vurl =  uploadMap.get("videoFile");
		String purl = uploadMap.get("imgFile");
		if(vurl!=null&&purl!=null){
			System.out.println(title);
			vdao.postVideo(userid,b,vurl, purl,title);
		}
		
		/*pointsup(videoid, point)  --加分+升级
		videoid--视频ID
		point --加的分数*/
		UsersDao udao = new UsersDao();
		int videoid = vdao.findTheLatestVideo().getVideoid();
		udao.updatePt(videoid, 10);
		
		if(isSuccess){
			return SUCCESS;
		}
		else{
			return ERROR;
		}
	}



	
	
}
