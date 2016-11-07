package com.bookshop01.order.dao;

import java.util.ArrayList;

import com.bookshop01.order.vo.OrderBean;

public interface OrderDao {
	public ArrayList<OrderBean> listMyOrderGoods(OrderBean orderBean) throws Exception;
	public void addOrderGoods(ArrayList<OrderBean> my_order_list) throws Exception;
	public OrderBean findMyOrder(String order_id) throws Exception;
	public void removeGoodsFromCart(OrderBean orderBean)throws Exception;
	public void removeGoodsFromCart(ArrayList<OrderBean> my_order_list)throws Exception;
}
