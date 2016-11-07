package com.bookshop01.order.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.cart.service.CartService;
import com.bookshop01.cart.vo.CartBean;
import com.bookshop01.common.controller.BaseController;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.service.OrderService;
import com.bookshop01.order.vo.OrderBean;

@Controller("orderController")
@RequestMapping(value="/order")
public class OrderControllerImpl extends BaseController implements OrderController {
	@Autowired
	OrderService orderService;
	
	@RequestMapping(value="/orderEachGoods.do" ,method = RequestMethod.POST)
	public ModelAndView myOrderGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		//로그인 여부 체크
		//이전에 로그인 상태인 경우는 주문과정 진행
		//로그아웃 상태인 경우 로그인 화면으로 이동
		OrderBean orderBean=new OrderBean();
		bind(request,orderBean);
		
		HttpSession session=request.getSession();
		session=request.getSession();
		
		Boolean isLogOn=(Boolean)session.getAttribute("isLogOn");
		String isComeFrom=(String)session.getAttribute("isComeFrom");
		
		if(isLogOn==null || isLogOn==false){
			session.setAttribute("orderInfo", orderBean);
			session.setAttribute("isComeFrom", "/order/orderEachGoods.do");
			return new ModelAndView("redirect:/member/loginForm.do");
		}else if(isComeFrom!=null && isComeFrom.equals("/order/orderEachGoods.do")){
			orderBean=(OrderBean)session.getAttribute("orderInfo");
			session.removeAttribute("isComeFrom");
		}
		
		
		request.setCharacterEncoding("utf-8");
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		
		ArrayList my_order_list=new ArrayList<OrderBean>();
		my_order_list.add(orderBean);

		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		
		session.setAttribute("my_order_list", my_order_list);
		session.setAttribute("orderer", memberBean);
		return mav;
	}
	
	@RequestMapping(value="/orderAllCartGoods.do" ,method = RequestMethod.POST)
	public ModelAndView orderAllCartGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		ArrayList my_order_list=new ArrayList<OrderBean>();
		String[] select_goods_ids =request.getParameterValues("select_goods");
		String[] cart_goods_qty =request.getParameterValues("cart_goods_qty");
		HttpSession session=request.getSession();
		HashMap cartHash=(HashMap)session.getAttribute("cartHash");
		ArrayList<GoodsBean> my_goods_list=(ArrayList<GoodsBean>)cartHash.get("my_goods_list");
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		
		for(int i=0; i<select_goods_ids.length;i++){
			String goods_id=select_goods_ids[i];
		
			for(int j=0; i<cart_goods_qty.length;i++){
				String[] temp=cart_goods_qty[i].split(":");
				if(goods_id.equals(temp[0])){
					OrderBean orderBean=new OrderBean();
					int order_goods_qty=Integer.parseInt(temp[1]);
					orderBean.setGoods_id(goods_id);
					orderBean.setOrder_goods_qty(order_goods_qty);
					for(int k=0; k<my_goods_list.size();k++){
						GoodsBean goodsBean=my_goods_list.get(i);
						if(goods_id.equals(goodsBean.getGoods_id())){
							String goods_title=goodsBean.getGoods_title();
							int goods_sales_price=goodsBean.getGoods_sales_price();
							String goods_fileName=goodsBean.getGoods_fileName();
							orderBean.setGoods_id(goods_id);
							orderBean.setGoods_title(goods_title);
							orderBean.setGoods_sales_price(goods_sales_price);
							orderBean.setGoods_fileName(goods_fileName);
							break;
						}
					} //end for
					
					my_order_list.add(orderBean);
					break;
				}
			}
		} //end for
		session.setAttribute("my_order_list", my_order_list);
		session.setAttribute("orderer", memberBean);
		return mav;
	}	
	
	@RequestMapping(value="/payOrderGoods.do" ,method = RequestMethod.POST)
	public ModelAndView payOrderGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String member_id=memberBean.getMember_id();
		String orderer_name=memberBean.getMember_name();
		ArrayList<OrderBean> my_order_list=(ArrayList<OrderBean>)session.getAttribute("my_order_list");
		
		
		String receiver_name=request.getParameter("receiver_name");
		String receiver_hp1=request.getParameter("receiver_hp1");
		String receiver_hp2=request.getParameter("receiver_hp2");
		String receiver_hp3=request.getParameter("receiver_hp3");
		String receiver_tel1=request.getParameter("receiver_tel1");
		String receiver_tel2=request.getParameter("receiver_tel2");
		String receiver_tel3=request.getParameter("receiver_tel3");
		
		String delivery_address=request.getParameter("delivery_address");
		String delivery_message=request.getParameter("delivery_message");
		String delivery_method=request.getParameter("delivery_method");
		String gift_wrapping=request.getParameter("gift_wrapping");
		String pay_method=request.getParameter("pay_method");
		String card_com_name=request.getParameter("card_com_name");
		String card_pay_month=request.getParameter("card_pay_month");
		String pay_orderer_hp_num=request.getParameter("pay_orderer_hp_num");
		
		for(int i=0; i<my_order_list.size();i++){
			OrderBean orderBean=(OrderBean)my_order_list.get(i);
			orderBean.setMember_id(member_id);
			orderBean.setOrderer_name(orderer_name);
			orderBean.setReceiver_name(receiver_name);
			
			orderBean.setReceiver_hp1(receiver_hp1);
			orderBean.setReceiver_hp2(receiver_hp2);
			orderBean.setReceiver_hp3(receiver_hp3);
			orderBean.setReceiver_tel1(receiver_tel1);
			orderBean.setReceiver_tel2(receiver_tel2);
			orderBean.setReceiver_tel3(receiver_tel3);
			
			orderBean.setDelivery_address(delivery_address);
			orderBean.setDelivery_message(delivery_message);
			orderBean.setDelivery_method(delivery_method);
			orderBean.setGift_wrapping(gift_wrapping);
			orderBean.setPay_method(pay_method);
			orderBean.setCard_com_name(card_com_name);
			orderBean.setCard_pay_month(card_pay_month);
			orderBean.setPay_orderer_hp_num(pay_orderer_hp_num);	
			my_order_list.set(i, orderBean); //각 orderBean에 주문자 정보를 세팅한 후 다시 my_order_list에 저장한다.
			session.setAttribute("my_order_list", my_order_list);
			
			if(i==0){
				OrderBean my_order_info=(OrderBean)my_order_list.get(i);
				mav.addObject("my_order_info",my_order_info);//OrderBean으로 주문결과 페이지에  주문자 정보를 표시한다.
			}
		}//end for
		
		orderService.addOrderGoods(my_order_list);
		mav.addObject("my_order_list", my_order_list);
		return mav;
	}

}
