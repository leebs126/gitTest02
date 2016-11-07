package com.bookshop01.admin.order.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.vo.OrderBean;

public interface AdminOrderDao {
	public ArrayList<OrderBean>listNewOrder(HashMap condMap) throws Exception;
	public void  modifyDeliveryState(HashMap orderMap) throws Exception;
	public ArrayList<OrderBean> orderDetail(String order_id) throws Exception;
	
	public MemberBean findOrderer(String member_id) throws Exception;
}
