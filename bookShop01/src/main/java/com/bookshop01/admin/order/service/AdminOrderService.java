package com.bookshop01.admin.order.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.order.vo.OrderBean;

public interface AdminOrderService {
	public ArrayList<OrderBean>listNewOrder(HashMap condMap) throws Exception;
	public void  modifyDeliveryState(HashMap orderMap) throws Exception;
	public HashMap orderDetail(String order_id) throws Exception;
}
