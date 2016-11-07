package com.bookshop01.mypage.controller;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.controller.BaseController;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.mypage.service.MyPageService;
import com.bookshop01.mypage.vo.MyPageBean;
import com.bookshop01.order.vo.OrderBean;

@Controller("myPageController")
@RequestMapping(value="/mypage")
public class MyPageControllerImpl extends BaseController  implements MyPageController{
	@Autowired
	MyPageService myPageService;
	@RequestMapping(value="/myPageMain.do" ,method = RequestMethod.GET)
	public ModelAndView myPageMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session=request.getSession();
		session=request.getSession();
		session.setAttribute("side_menu", "my_page"); //마이페이지 사이드 메뉴로 설정한다.
		
		String isComeFrom=request.getParameter("isComeFrom"); //주문 취소후 다시 요청 유무 확인
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String member_id=memberBean.getMember_id();
		
		ArrayList<OrderBean> my_order_list=myPageService.listMyOrderGoods(memberBean);
		mav.addObject("isComeFrom", isComeFrom);
		mav.addObject("my_order_list", my_order_list);
		return mav;
		
	}
	@RequestMapping(value="/myOrderDetail.do" ,method = RequestMethod.GET)
	public ModelAndView myOrderDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		MemberBean orderer=(MemberBean)session.getAttribute("member_info");
		
		String order_id=(String) request.getParameter("order_id");
		ArrayList my_order_list=myPageService.findMyOrderInfo(order_id);
		OrderBean my_order_info=(OrderBean)my_order_list.get(0);
		mav.addObject("my_order_info",my_order_info);//OrderBean으로 주문결과 페이지에  주문자 정보를 표시한다.
		mav.addObject("orderer", orderer);
		mav.addObject("my_order_list",my_order_list);
		return mav;
	}
	
	
	@RequestMapping(value="/listMyOrderHistory.do" ,method = RequestMethod.GET)
	public ModelAndView listMyOrderHistory(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String  member_id=memberBean.getMember_id();
		
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
		
		System.out.println("beginDate:"+beginDate+", endDate:"+endDate);
		MyPageBean myPageBean=new MyPageBean();
		myPageBean.setMember_id(member_id);
		myPageBean.setBeginDate(beginDate);
		myPageBean.setEndDate(endDate);
		ArrayList<OrderBean> my_order_hist_list=myPageService.listMyOrderHistory(myPageBean);
		
		String beginDate1[]=beginDate.split("-");
		String endDate2[]=endDate.split("-");
		mav.addObject("beginYear",beginDate1[0]);
		mav.addObject("beginMonth",beginDate1[1]);
		mav.addObject("beginDay",beginDate1[2]);
		mav.addObject("endYear",endDate2[0]);
		mav.addObject("endMonth",endDate2[1]);
		mav.addObject("endDay",endDate2[2]);
		mav.addObject("my_order_hist_list", my_order_hist_list);
		
		return mav;
	}	
	
	@RequestMapping(value="/cancelMyOrder.do" ,method = RequestMethod.POST)
	public ModelAndView cancelMyOrder(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView();
		String order_id=request.getParameter("order_id");
		myPageService.cancelOrder(order_id);
		mav.addObject("isComeFrom", "cancel_order");
		mav.setViewName("redirect:/mypage/myPageMain.do");
		return mav;
	}
	
	@RequestMapping(value="/myInfoDetail.do" ,method = RequestMethod.GET)
	public ModelAndView myInfoDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String  member_id=memberBean.getMember_id();

		MemberBean myDetailInfo=myPageService.myInfoDetail(member_id);
		
		mav.addObject("myDetailInfo",myDetailInfo);
		
		return mav;
	}		
	@RequestMapping(value="/modifyMyInfo.do" ,method = RequestMethod.POST)
	public void modifyMyInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HashMap<String,String> memberMap=new HashMap<String,String>();
		String val[]=null;
		PrintWriter pw=response.getWriter();
		HttpSession session=request.getSession();
		String member_id=request.getParameter("member_id");
		String mod_type=request.getParameter("mod_type");
		String value =request.getParameter("value");
		if(mod_type.equals("member_birth")){
			val=value.split(",");
			memberMap.put("member_birth_y",val[0]);
			memberMap.put("member_birth_m",val[1]);
			memberMap.put("member_birth_d",val[2]);
			memberMap.put("member_birth_gn",val[3]);
		}else if(mod_type.equals("tel")){
			val=value.split(",");
			memberMap.put("tel1",val[0]);
			memberMap.put("tel2",val[1]);
			memberMap.put("tel3",val[2]);
			
		}else if(mod_type.equals("hp")){
			val=value.split(",");
			memberMap.put("hp1",val[0]);
			memberMap.put("hp2",val[1]);
			memberMap.put("hp3",val[2]);
			memberMap.put("smssts_yn", val[3]);
		}else if(mod_type.equals("email")){
			val=value.split(",");
			memberMap.put("email1",val[0]);
			memberMap.put("email2",val[1]);
			memberMap.put("emailsts_yn", val[2]);
		}else if(mod_type.equals("address")){
			val=value.split(",");
			memberMap.put("zipcode",val[0]);
			memberMap.put("roadAddress",val[1]);
			memberMap.put("jibunAddress", val[2]);
			memberMap.put("namujiAddress", val[3]);
		}
		
		memberMap.put("member_id", member_id);
		
		//수정된 회원 정보를 세션에 저장한다.
		MemberBean member_info=(MemberBean)myPageService.modifyMyInfo(memberMap);
		session.removeAttribute("member_info");
		session.setAttribute("member_info", member_info);
		pw.print("mod_success");
		pw.close();
	}	
	
	
	
	
	
}
