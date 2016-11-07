package com.bookshop01.order.dao;

import java.util.ArrayList;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop01.order.vo.OrderBean;

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {
	@Autowired
	private SqlSession sqlSession;
	
	public ArrayList<OrderBean> listMyOrderGoods(OrderBean orderBean) throws Exception{
		ArrayList<OrderBean> orderGoodsList=new ArrayList<OrderBean>();
		orderGoodsList=(ArrayList)sqlSession.selectList("mapper.order.list_my_order",orderBean);
		return orderGoodsList;
	}
	
	public void addOrderGoods(ArrayList<OrderBean> my_order_list) throws Exception{
		String order_id=getOrder_id();
		for(int i=0; i<my_order_list.size();i++){
			OrderBean orderBean =(OrderBean)my_order_list.get(i);
			orderBean.setOrder_id(order_id);
			sqlSession.insert("mapper.order.add_order_goods",orderBean);
		}
	}	
	
	public OrderBean findMyOrder(String order_id) throws Exception{
		OrderBean orderBean=(OrderBean)sqlSession.selectOne("mapper.order.find_my_order",order_id);		
		return orderBean;
	}
	
	public void removeGoodsFromCart(OrderBean orderBean)throws Exception{
		sqlSession.delete("mapper.order.delete_goods_from_cart",orderBean);
	}
	
	public void removeGoodsFromCart(ArrayList<OrderBean> my_order_list)throws Exception{
		for(int i=0; i<my_order_list.size();i++){
			OrderBean orderBean =(OrderBean)my_order_list.get(i);
			sqlSession.delete("mapper.order.delete_goods_from_cart",orderBean);		
		}
	}	
	private String getOrder_id() throws Exception{
		return sqlSession.selectOne("mapper.order.get_order_id");
		
	}
}

