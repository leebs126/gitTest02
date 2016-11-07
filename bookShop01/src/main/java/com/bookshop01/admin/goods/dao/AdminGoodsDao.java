package com.bookshop01.admin.goods.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.order.vo.OrderBean;

public interface AdminGoodsDao {
	public String addNewGoods(GoodsBean goodsBean) throws Exception;
	public ArrayList<GoodsBean>listNewGoods(HashMap condMap) throws Exception;
	public GoodsBean goodsDetail(String goods_id) throws Exception;
	public ArrayList goodsImageFile(String goods_id) throws Exception;
	public void addImageFile(ArrayList fileList);
	public void modifyGoodsInfo(HashMap goodsMap) throws Exception;
	public void deleteImageInfo(String image_id) throws Exception;
	public void deleteImageInfo(ArrayList fileList) throws Exception;
	public ArrayList<OrderBean>listOrderGoods(HashMap condMap) throws Exception;
	public void modifyOrderGoods(HashMap hashOrder) throws Exception;
	
}
