package com.bookshop01.admin.goods.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.goods.service.AdminGoodsService;
import com.bookshop01.common.controller.BaseController;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.mypage.controller.MyPageController;
import com.bookshop01.mypage.service.MyPageService;
import com.bookshop01.order.vo.OrderBean;

@Controller("adminGoodsController")
@RequestMapping(value="/admin/goods")
public class AdminGoodsControllerImpl extends BaseController  implements AdminGoodsController{
	@Autowired
	AdminGoodsService adminGoodsService;
	
	@RequestMapping(value="/adminGoodsMain.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminGoodsMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		session=request.getSession();
		session.setAttribute("side_menu", "admin_mode"); //마이페이지 사이드 메뉴로 설정한다.

		String _chapter=null,_pageNum=null,period;
		String _beginDate=null,_endDate=null,beginDate=null,endDate=null;
		int curChapter=0,curPageNum=0;
        _beginDate=request.getParameter("beginDate");
        _endDate=request.getParameter("endDate");
		_chapter=request.getParameter("chapter");
		_pageNum=request.getParameter("pageNum");
		
		if(_endDate==null){
			String [] temp=calcSearchPeriod().split(",");
			beginDate=temp[0];
			endDate=temp[1];
		}else{
			beginDate=_beginDate;
			endDate=_endDate;	
		}
		
		if(_chapter==null){
			curChapter=1;
		}else{
			curChapter=Integer.parseInt(_chapter);
		}
		if(_pageNum==null){
			curPageNum=1;
		}else{
			curPageNum=Integer.parseInt(_pageNum);
		}
		HashMap<String,Object> condMap=new HashMap<String,Object>();
		condMap.put("chapter",curChapter);
		condMap.put("pageNum",curPageNum);
		condMap.put("beginDate",beginDate);
		condMap.put("endDate", endDate);
		ArrayList<GoodsBean> new_goods_list=adminGoodsService.listNewGoods(condMap);
		mav.addObject("new_goods_list", new_goods_list);
		
		String beginDate1[]=beginDate.split("-");
		String endDate2[]=endDate.split("-");
		mav.addObject("beginYear",beginDate1[0]);
		mav.addObject("beginMonth",beginDate1[1]);
		mav.addObject("beginDay",beginDate1[2]);
		mav.addObject("endYear",endDate2[0]);
		mav.addObject("endMonth",endDate2[1]);
		mav.addObject("endDay",endDate2[2]);
		
		mav.addObject("chapter", curChapter);
		mav.addObject("pageNum", curPageNum);
		return mav;
		
	}
	
	@RequestMapping(value="/saveNewGoods.do" ,method={RequestMethod.POST})
	public ModelAndView saveNewGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		String _goods_title=request.getParameter("goods_title");
		String goods_title = URLDecoder.decode(_goods_title, "UTF-8") ;
		
		GoodsBean newGoodsBean=new GoodsBean();
		bind(request,newGoodsBean);
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		
		HttpSession session=request.getSession();
		session.setAttribute("newGoodsBean", newGoodsBean);
		session.setAttribute("command", "add_new_goods");
		mav.setViewName("redirect:/admin/goods/addNewGoodsImageForm.do");
		return mav;
		
	}
	
	@RequestMapping(value="/addNewGoods.do" ,method={RequestMethod.POST})
	public ModelAndView addNewGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HashMap newGoodsMap=new HashMap();
		HttpSession session=request.getSession();
		GoodsBean newGoodsBean=(GoodsBean)session.getAttribute("newGoodsBean");
		String command=(String)session.getAttribute("command");
		newGoodsMap.put("newGoodsBean",newGoodsBean);
		newGoodsMap.put("command",command);
		adminGoodsService.addNewGoods(request,response,newGoodsMap);
		
		return mav;
	}
	
	@RequestMapping(value="/modifyGoodsForm.do" ,method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView modifyGoodsForm(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		
		String goods_id=request.getParameter("goods_id");
		GoodsBean goodsBean=adminGoodsService.goodsDetail(goods_id);
		mav.addObject("goods",goodsBean);
		
		return mav;
	}
	
	@RequestMapping(value="/modifyGoodsInfo.do" ,method={RequestMethod.POST})
	public void modifyGoodsInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		PrintWriter pw=response.getWriter();
		GoodsBean goodsBean=new GoodsBean();
		String goods_id=request.getParameter("goods_id");
		String mod_type=request.getParameter("mod_type");
		String value =request.getParameter("value");
		HashMap<String,String> goodsMap=new HashMap<String,String>();
		goodsMap.put("goods_id", goods_id);
		goodsMap.put(mod_type, value);
		adminGoodsService.modifyGoodsInfo(goodsMap);
		pw.print("mod_success");
		pw.close();
		
	}
	
	@RequestMapping(value="/modifyGoodsImageForm.do" ,method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView modifyGoodsImageForm(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		String goods_id=request.getParameter("goods_id");
		ArrayList imageFileList = adminGoodsService.goodsImageFile(goods_id);
		session.setAttribute("goods_id", goods_id);
		session.setAttribute("command","modify_image");
		session.setAttribute("imageFileList", imageFileList);
		return mav;
	}
	
	
	@RequestMapping(value="/modifyGoodsImage.do" ,method={RequestMethod.POST})
	public ModelAndView  modifyGoodsImage(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		adminGoodsService.modifyGoodsImage(request,response);
		mav.setViewName("redirect:/admin/goods/adminGoodsMain.do");
		return mav;
	}
	
	@RequestMapping(value="/deleteImageFile.do" ,method={RequestMethod.POST})
	public void  deleteImageFile(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		PrintWriter pw=response.getWriter();
		
		HttpSession session=request.getSession();
		String goods_id=(String)session.getAttribute("goods_id");
		adminGoodsService.deleteImageInfo(request,response,goods_id);
		pw.write("delete_success");
	}
	
	/*@RequestMapping(value="/goods/*.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public  ModelAndView adminGoodsForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		return mav;
	}*/
	
		
}
