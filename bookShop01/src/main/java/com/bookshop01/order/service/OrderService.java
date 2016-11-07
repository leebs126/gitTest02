package com.bookshop01.order.service;

import java.util.ArrayList;

import com.bookshop01.order.vo.OrderBean;

public interface OrderService {
	public ArrayList<OrderBean> listMyOrderGoods(OrderBean orderBean) throws Exception;
	public void addOrderGoods(ArrayList<OrderBean> my_order_list) throws Exception;
	public OrderBean findMyOrder(String order_id) throws Exception;
	
	
}
