package com.bookshop01.admin.goods.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.order.vo.OrderBean;

public interface AdminGoodsService {
	public void  addNewGoods(HttpServletRequest request, HttpServletResponse response,HashMap goodsMap) throws Exception;
	public void  addNewImage(ArrayList fileList) throws Exception;
	public ArrayList<GoodsBean> listNewGoods(HashMap condMap) throws Exception;
	public GoodsBean goodsDetail(String goods_id) throws Exception;
	public ArrayList goodsImageFile(String goods_id) throws Exception;
	public void modifyGoodsInfo(HashMap goodsMap) throws Exception;
	public void modifyGoodsImage(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void deleteImageInfo(HttpServletRequest request, HttpServletResponse response,String goods_id) throws Exception;
	public ArrayList<OrderBean> listOrderGoods(HashMap condMap) throws Exception;
	public void modifyOrderGoods(HashMap orderMap) throws Exception;
}
