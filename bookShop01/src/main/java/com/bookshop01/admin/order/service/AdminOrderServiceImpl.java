package com.bookshop01.admin.order.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.admin.goods.dao.AdminGoodsDao;
import com.bookshop01.admin.order.dao.AdminOrderDao;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.vo.OrderBean;


@Service("adminOrderService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminOrderServiceImpl implements AdminOrderService {
	@Autowired
	AdminOrderDao adminOrderDao;
	
	public ArrayList<OrderBean>listNewOrder(HashMap condMap) throws Exception{
		return adminOrderDao.listNewOrder(condMap);
	}
	
	public void  modifyDeliveryState(HashMap orderMap) throws Exception{
		 adminOrderDao.modifyDeliveryState(orderMap);
	}
	
	public HashMap orderDetail(String order_id) throws Exception{
		HashMap orderMap=new HashMap();
		ArrayList<OrderBean> order_list =adminOrderDao.orderDetail(order_id);
		OrderBean order_info=(OrderBean)order_list.get(0);
		String member_id=(String)order_info.getMember_id();
		MemberBean orderer=adminOrderDao.findOrderer(member_id);
		orderMap.put("order_list",order_list);
		orderMap.put("order_info",order_info);
		orderMap.put("orderer", orderer);
		return orderMap;
	}
	
	
	

}
