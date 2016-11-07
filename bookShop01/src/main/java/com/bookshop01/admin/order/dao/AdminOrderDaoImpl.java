package com.bookshop01.admin.order.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.vo.OrderBean;

@Repository("adminOrderDao")
public class AdminOrderDaoImpl  implements AdminOrderDao{
	@Autowired
	private SqlSession sqlSession;
	
	public ArrayList<OrderBean>listNewOrder(HashMap condMap) throws Exception{
		ArrayList<OrderBean>  orderList=(ArrayList)sqlSession.selectList("mapper.admin.order.list_new_order",condMap);
		return orderList;
	}
	public void  modifyDeliveryState(HashMap orderMap) throws Exception{
		sqlSession.update("mapper.admin.order.modifyDeliveryState",orderMap);
	}
	
	public ArrayList<OrderBean> orderDetail(String order_id) throws Exception{
		ArrayList<OrderBean> order_list=(ArrayList)sqlSession.selectList("mapper.admin.order.orderDetail",order_id);
		return order_list;
	}


	public MemberBean findOrderer(String member_id) throws Exception{
		MemberBean orderer=(MemberBean)sqlSession.selectOne("mapper.admin.order.findOrderer",member_id);
		return orderer;
		
	}

}
