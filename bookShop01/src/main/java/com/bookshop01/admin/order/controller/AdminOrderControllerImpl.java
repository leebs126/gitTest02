package com.bookshop01.admin.order.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.goods.service.AdminGoodsService;
import com.bookshop01.admin.order.service.AdminOrderService;
import com.bookshop01.common.controller.BaseController;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.mypage.controller.MyPageController;
import com.bookshop01.mypage.service.MyPageService;
import com.bookshop01.order.vo.OrderBean;

@Controller("adminOrderController")
@RequestMapping(value="/admin/order")
public class AdminOrderControllerImpl extends BaseController  implements AdminOrderController{
	@Autowired
	AdminOrderService adminOrderService;
	
	@RequestMapping(value="/adminOrderMain.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminOrderMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
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
		ArrayList<OrderBean> new_order_list=adminOrderService.listNewOrder(condMap);
		mav.addObject("new_order_list",new_order_list);
		
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
	
	@RequestMapping(value="/modifyDeliveryState.do" ,method={RequestMethod.POST})
	public void modifyDeliveryState(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		PrintWriter pw=response.getWriter();
		String order_id=request.getParameter("order_id");
		String mod_type=request.getParameter("mod_type");
		String value=request.getParameter("value");

		HashMap<String,String> orderMap=new HashMap<String,String>();
		orderMap.put("order_id", order_id);
		orderMap.put(mod_type, value);
		adminOrderService.modifyDeliveryState(orderMap);
		pw.print("mod_success");
		pw.close();		
		
	}
	
	@RequestMapping(value="/orderDetail.do" ,method={RequestMethod.POST})
	public ModelAndView orderDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		MemberBean orderer=(MemberBean)session.getAttribute("member_info");
		
		String order_id=(String) request.getParameter("order_id");
		HashMap orderMap =adminOrderService.orderDetail(order_id);
		
		mav.addObject("orderMap", orderMap);
		return mav;
	}
	
}
