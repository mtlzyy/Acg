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
		//�Ƿ��ϴ��ɹ��ı�ʶ
		boolean isSuccess=true;	
		//�����ļ��ϴ�����
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
		//��ȡ��������е���������ȡ�ύ���󼯺�
		try {
			List<FileItem> fileList=disk.parseRequest(req);	
			//�����ύ�Ķ���
			for(FileItem item : fileList)
			{	
				if(!item.isFormField())
				{
					//��ȡ�ϴ��ļ���������
					BufferedInputStream input=new BufferedInputStream(item.getInputStream());
					
					//��ȡ�ϴ��ļ����ļ���
					String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);
					
					//��ȡ��Ŀ����·��
					
					//�����ϴ�·��
					String sf = dest.getAbsolutePath()+"\\"+fileName;
					File f = new File(sf);
					System.out.println(sf);
					
									
					//��ȡ�����
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
		
		/*pointsup(videoid, point)  --�ӷ�+����
		videoid--��ƵID
		point --�ӵķ���*/
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
