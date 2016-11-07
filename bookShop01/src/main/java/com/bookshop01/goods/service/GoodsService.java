package com.bookshop01.goods.service;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ReviewBean;

public interface GoodsService {
	
	public HashMap<String,ArrayList<GoodsBean>> listGoods() throws Exception;
	public HashMap goodsDetail(String _goods_id,int chapter,int pageNum) throws Exception;
	public ArrayList keywordSearch(String keyword) throws Exception;
	public ArrayList searchGoods(String searchWord) throws Exception;
	
	public void addReview(ReviewBean reviewBean) throws Exception;
	
}
