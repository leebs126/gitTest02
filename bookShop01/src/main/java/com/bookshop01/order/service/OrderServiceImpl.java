package com.bookshop01.order.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.bookshop01.order.dao.OrderDao;
import com.bookshop01.order.vo.OrderBean;


@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao orderDao;
	
	public ArrayList<OrderBean> listMyOrderGoods(OrderBean orderBean) throws Exception{
		ArrayList<OrderBean> orderGoodsList;
		orderGoodsList=orderDao.listMyOrderGoods(orderBean);
		return orderGoodsList;
	}
	
	public void addOrderGoods(ArrayList<OrderBean> my_order_list) throws Exception{
		orderDao.addOrderGoods(my_order_list);
		//카트에서 주문 상품 제거한다.
		orderDao.removeGoodsFromCart(my_order_list);
	}	
	
	public OrderBean findMyOrder(String order_id) throws Exception{
		return orderDao.findMyOrder(order_id);
	}

}
