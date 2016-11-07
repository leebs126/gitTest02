package com.bookshop01.cart.controller;

import java.io.PrintWriter;
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

@Controller("cartController")
@RequestMapping(value="/cart")
public class CartControllerImpl extends BaseController implements CartController{
	@Autowired
	CartService cartService;
	@RequestMapping(value="/myCartMain.do" ,method = RequestMethod.GET)
	public ModelAndView myCartMain(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		CartBean cartBean=new CartBean();
		String member_id=memberBean.getMember_id();
		cartBean.setMember_id(member_id);
		HashMap<String ,ArrayList> cartHash=cartService.myCartList(cartBean);
		session.setAttribute("cartHash", cartHash);//장바구니 조회 화면에서 바로 주문 시 사용하기 위해서 장바구니 목록을 세션에 저장한다.
		mav.addObject("cartHash", cartHash);
		return mav;
	}

	@RequestMapping(value="/addCart.do" ,method = RequestMethod.POST)
	public void addCart(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String goods_id = request.getParameter("goods_id");
		PrintWriter pw=response.getWriter();
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String member_id=memberBean.getMember_id();
		
		CartBean cartBean=new CartBean();
		cartBean.setMember_id(member_id);
		//카트 등록전에 이미 등록된 제품인지 판별한다.
		cartBean.setGoods_id(goods_id);
		cartBean.setMember_id(member_id);
		boolean isAreadyExisted=cartService.searchCart(cartBean);
		System.out.println("isAreadyExisted:"+isAreadyExisted);
		if(isAreadyExisted==true){
			pw.print("already_existed");
		}else{
			cartService.addCart(cartBean);
			pw.print("add_success");	
		}
		pw.close();
	}
	
	@RequestMapping(value="/modifyCartQty.do" ,method = RequestMethod.POST)
	public void modifyCartQty(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String goods_id = request.getParameter("goods_id");
		PrintWriter pw=response.getWriter();
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String member_id=memberBean.getMember_id();
		String cart_goods_qty = request.getParameter("cart_goods_qty");
		CartBean cartBean=new CartBean();
		cartBean.setGoods_id(goods_id);
		cartBean.setMember_id(member_id);
		cartBean.setCart_goods_qty(Integer.parseInt(cart_goods_qty));
		boolean result=cartService.modifyCartQty(cartBean);
		
		if(result==true){
			pw.print("modify_success");
		}else{
			pw.print("modify_failed");	
		}
		
		pw.close();
	}
	
	@RequestMapping(value="/deleteCartGoods.do" ,method = RequestMethod.POST)
	public ModelAndView deleteCartGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		ModelAndView mav=new ModelAndView();
		String _cart_id=request.getParameter("cart_id");
		int cart_id=Integer.parseInt(_cart_id);
		cartService.deleteCartGoods(cart_id);
		mav.setViewName("redirect:/cart/myCartList.do");
		return mav;
	}
}
