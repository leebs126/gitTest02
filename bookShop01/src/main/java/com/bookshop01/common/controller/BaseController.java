package com.bookshop01.common.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class BaseController  extends MultiActionController {
	protected HashMap<String,String>  processFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HashMap<String,String> writeMap=new HashMap<String,String>();
		String encoding="utf-8";
		File currentDirPath =new File("C:\\file_repo");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath );
		factory.setSizeThreshold(1024*1024);
		
		ServletFileUpload upload=new ServletFileUpload(factory);
		
		List<?> items = upload.parseRequest(request);
		for(int i=0; i < items.size();i++){
			FileItem fileItem = (FileItem) items.get(i);
			
			if(fileItem.isFormField()){
				System.out.println(fileItem.getFieldName()+ "=" +fileItem.getString(encoding));
				writeMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
			}else{
				System.out.println("파라미터명:"+fileItem.getFieldName());
				System.out.println("파일명:"+fileItem.getName());
				System.out.println("파일크기:"+fileItem.getSize( ) + "bytes");
				writeMap.put(fileItem.getFieldName(), fileItem.getName());
				//업로드한 파일이 존재하는 경우
				if(fileItem.getSize() > 0){
					int idx = fileItem.getName().lastIndexOf("\\");
					if(idx ==-1){
						idx = fileItem.getName().lastIndexOf("/");
					}
					
					String fileName = fileItem.getName().substring(idx+1);
					try{
						File uploadFile = new  File(currentDirPath +"\\"+ fileName);
						fileItem.write(uploadFile);
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			
			}
		
		}
		
		return writeMap;
	}
	
	private void deleteFile(String fileName) {
		File file =new File("C:\\file_repo\\"+fileName);
		try{
			file.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected  String getFileName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String)request.getAttribute("javax.servlet.include.request_uri");

		if(uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if(!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if(uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if(uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String fileName = uri.substring(begin, end);
		if(fileName.indexOf(".") != -1) {
		        fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}
	@RequestMapping(value="/*.do" ,method={RequestMethod.POST,RequestMethod.GET})
	protected  ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		return mav;
	}
	
	
	protected String calcSearchPeriod(){
		String beginDate=null;
		String endDate=null;
		String endYear=null;
		String endMonth=null;
		String endDay=null;
		String beginYear=null;
		String beginMonth=null;
		String beginDay=null;
		HashMap<String,String> hashDate=new HashMap<String,String>();
		
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal=Calendar.getInstance();
		endYear   = Integer.toString(cal.get(Calendar.YEAR));
		endMonth  = df.format(cal.get(Calendar.MONTH) + 1);
		endDay   = df.format(cal.get(Calendar.DATE));
		endDate = endYear +"-"+ endMonth +"-"+endDay;
		cal.add(cal.MONTH,-4);
		beginYear   = Integer.toString(cal.get(Calendar.YEAR));
		beginMonth  = df.format(cal.get(Calendar.MONTH) + 1);
		beginDay   = df.format(cal.get(Calendar.DATE));
		beginDate = beginYear +"-"+ beginMonth +"-"+beginDay;
		return beginDate+","+endDate;
	}
	
}
