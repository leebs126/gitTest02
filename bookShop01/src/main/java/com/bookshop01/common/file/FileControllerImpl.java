package com.bookshop01.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller("fileController")
public class FileControllerImpl  extends MultiActionController {
   @RequestMapping(value="/fileDownload.do")	
   protected void downLoadFile(HttpServletRequest request,HttpServletResponse response)
		  throws ServletException, IOException{
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			String file_repo="C:\\file_repo";
			//자바 I/O를 이용해서 파일을 전송한다.
			String fileName = (String)request.getParameter("fileName");
			String goods_id = (String)request.getParameter("goods_id");
			OutputStream out = response.getOutputStream();
			String downFile=file_repo+"\\"+goods_id+"\\"+fileName;
			File f=new File(downFile);
			
			response.setHeader("Cache-Control","no-cache");
			response.addHeader("Content-disposition", "attachment; fileName="+fileName);
			FileInputStream in=new FileInputStream(f); 
			byte[] buffer=new byte[1024*8];
			while(true){
				int count=in.read(buffer); //버퍼에 읽어들인 문자개수
				if(count==-1)  //버퍼의 마지막에 도달했는지 체크
					break;
				out.write(buffer,0,count);
			}
			in.close();
			out.close();
	    }

}
