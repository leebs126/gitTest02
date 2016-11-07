package com.bookshop01.admin.goods.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.admin.goods.dao.AdminGoodsDao;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.vo.OrderBean;


@Service("adminGoodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
	@Autowired
	AdminGoodsDao adminGoodsDao;
	private static String FILE_SERVER_DIR="C:\\file_repo";
	private String filePath = FILE_SERVER_DIR;
	
	public void  addNewGoods(HttpServletRequest request, HttpServletResponse response,HashMap newGoodsMap) throws Exception{
		String goods_id=null;
		GoodsBean newGoodsBean=(GoodsBean)newGoodsMap.get("newGoodsBean");
		goods_id=adminGoodsDao.addNewGoods(newGoodsBean);
		String command=(String)newGoodsMap.get("command");
		ArrayList imageList=processImageFile(request,response,goods_id,command);
		addNewImage(imageList);
	}
	public void  addNewImage(ArrayList fileList) throws Exception{
		adminGoodsDao.addImageFile(fileList);
	}
	
	public ArrayList<GoodsBean> listNewGoods(HashMap condMap) throws Exception{
		return adminGoodsDao.listNewGoods(condMap);
	}
	
	public GoodsBean goodsDetail(String goods_id) throws Exception {
		GoodsBean goodsBean=adminGoodsDao.goodsDetail(goods_id);
		return goodsBean;
	}
	
	public ArrayList goodsImageFile(String goods_id) throws Exception{
		ArrayList imageList =adminGoodsDao.goodsImageFile(goods_id);
		return imageList;
	}
	
	
	public void modifyGoodsInfo(HashMap goodsMap) throws Exception{
		adminGoodsDao.modifyGoodsInfo(goodsMap);
		
	}	
	
	public void modifyGoodsImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String image_id=request.getParameter("image_id");
		
		HttpSession session=request.getSession();
		String command=(String)session.getAttribute("command");
		String goods_id=(String)session.getAttribute("goods_id");
		ArrayList imageFileList=(ArrayList)session.getAttribute("imageFileList");
		adminGoodsDao.deleteImageInfo(imageFileList);  //기존 제품 이미지를 모둔 지운 후 다시 이미지 수정창에서 보낸 이미지파일을 업로드한다.
		
		ArrayList newFileList =processImageFile(request,response,goods_id,command);
		adminGoodsDao.addImageFile(newFileList);
	}
	
	public void deleteImageInfo(HttpServletRequest request, HttpServletResponse response,String goods_id) throws Exception{
		String image_id=request.getParameter("image_id");
		String command=request.getParameter("command");
		processImageFile(request,response,goods_id,command);
		adminGoodsDao.deleteImageInfo(image_id);
	}
	
	public ArrayList<OrderBean> listOrderGoods(HashMap condMap) throws Exception{
		return adminGoodsDao.listOrderGoods(condMap);
	}
	
	public void modifyOrderGoods(HashMap orderMap) throws Exception{
		adminGoodsDao.modifyOrderGoods(orderMap);
	}
	
	private ArrayList  processImageFile(HttpServletRequest request, HttpServletResponse response,String goods_id,String command) throws Exception{
		String encoding="utf-8";
		File uploadFile = null;
		ArrayList<ImageFileBean> fileList=new ArrayList<ImageFileBean>();
		HttpSession session=request.getSession();
		MemberBean member_info=(MemberBean)session.getAttribute("member_info");
		String reg_id=member_info.getMember_id();
		String parameter=null;
		String value=null;
		String fileName=null;
		ArrayList<String> fileNameList=new ArrayList<String>();
		File currentRepoPath =new File(FILE_SERVER_DIR);

		System.out.println(command);
		if(command!= null && command.equals("delete_image")){
			String image_id=request.getParameter("image_id");
			fileName=request.getParameter("fileName");
			deleteImage(image_id,goods_id,fileName);
			return  null;
		}else if(command.equals("add_new_goods") || command.equals("modify_image")){
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setRepository(currentRepoPath );
			factory.setSizeThreshold(1024*1024);
			
			ServletFileUpload upload=new ServletFileUpload(factory);
			
			List<?> items = upload.parseRequest(request);
			for(int i=0; i < items.size();i++){
				FileItem fileItem = (FileItem) items.get(i);
				
				if(fileItem.isFormField()){
					System.out.println(fileItem.getFieldName()+ "=" +fileItem.getString(encoding));
				}else{
					System.out.println("파라미터명:"+fileItem.getFieldName());
					System.out.println("파일명:"+fileItem.getName());
					System.out.println("파일크기:"+fileItem.getSize( ) + "bytes");
					filePath=FILE_SERVER_DIR+"\\"+goods_id;
					new  File(filePath).mkdir();
					
					fileName=fileItem.getName();
					String fileType=fileItem.getFieldName();
					ImageFileBean imageFileBean=new ImageFileBean();
					imageFileBean.setFileName(fileName);
					imageFileBean.setFileType(fileType);
					imageFileBean.setGoods_id(goods_id);
					imageFileBean.setReg_id(reg_id); //제품 등록자 아이디
					fileList.add(imageFileBean);
					
					//업로드한 파일이 존재하는 경우
					if(fileItem.getSize() > 0){
						int idx = fileItem.getName().lastIndexOf("\\");
						if(idx ==-1){
							idx = fileItem.getName().lastIndexOf("/");
						}
						 fileName = fileItem.getName().substring(idx+1);
						try{
							System.out.println("저장 위치:"+filePath);
							uploadFile = new  File(filePath+"\\"+fileName);
							fileItem.write(uploadFile);
						}catch(IOException e){
							e.printStackTrace();
						}
					}
				
				}
			
			}
		}
		return fileList;
	}	
	
	private void deleteImage(String image_id,String goods_id,String fileName) throws Exception{
		  String filePath=FILE_SERVER_DIR+"\\"+goods_id+"\\"+fileName;
		  System.out.println("filePath: "+filePath);
		  new  File(filePath).delete();
	  }	
}
