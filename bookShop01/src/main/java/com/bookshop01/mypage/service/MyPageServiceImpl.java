package com.bookshop01.mypage.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.mypage.dao.MyPageDao;
import com.bookshop01.mypage.vo.MyPageBean;
import com.bookshop01.order.vo.OrderBean;

@Service("myPageService")
public class MyPageServiceImpl  implements MyPageService{
	@Autowired
	MyPageDao myPageDao;

	public ArrayList<OrderBean> listMyOrderGoods(MemberBean memberBean) throws Exception{
		return myPageDao.listMyOrderGoods(memberBean);
	}
	
	public ArrayList findMyOrderInfo(String order_id) throws Exception{
		return myPageDao.findMyOrderInfo(order_id);
	}
	
	public ArrayList<OrderBean> listMyOrderHistory(MyPageBean myPageBean) throws Exception{
		return myPageDao.listMyOrderHistory(myPageBean);
	}
	
	public MemberBean  modifyMyInfo(HashMap memberMap) throws Exception{
		 String member_id=(String)memberMap.get("member_id");
		 myPageDao.modifyMyInfo(memberMap);
		 return myPageDao.myInfoDetail(member_id);
	}
	
	public void cancelOrder(String order_id) throws Exception{
		myPageDao.cancelOrder(order_id);
	}
	public MemberBean myInfoDetail(String member_id) throws Exception{
		return myPageDao.myInfoDetail(member_id);
	}
}
