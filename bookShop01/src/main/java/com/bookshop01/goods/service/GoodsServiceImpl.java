package com.bookshop01.goods.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bookshop01.goods.dao.GoodsDao;
import com.bookshop01.goods.dao.GoodsDaoImpl;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ReviewBean;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	@Autowired
	GoodsDao goodsDao;
	
	public HashMap<String,ArrayList<GoodsBean>> listGoods() throws Exception {
		HashMap<String,ArrayList<GoodsBean>> goodsMap=new HashMap<String,ArrayList<GoodsBean>>();
		ArrayList goodsList=goodsDao.listGoods("bestseller");
		goodsMap.put("bestseller",goodsList);
		goodsList=goodsDao.listGoods("newbook");
		goodsMap.put("newbook",goodsList);
		
		goodsList=goodsDao.listGoods("steadyseller");
		goodsMap.put("steadyseller",goodsList);
		
		return goodsMap;
	}
	
	public ArrayList keywordSearch(String keyword) throws Exception {
		ArrayList list=goodsDao.keywordSearch(keyword);
		return list;
	}
	
	public ArrayList searchGoods(String searchWord) throws Exception{
		ArrayList goodsList=goodsDao.searchGoods(searchWord);
		return goodsList;
	}
	
	public HashMap goodsDetail(String goods_id,int chapter,int pageNum) throws Exception {
		HashMap goodsMap=new HashMap();
		GoodsBean goodsBean = goodsDao.goodsDetail(goods_id);
		goodsMap.put("goods", goodsBean);
		ArrayList imageList =goodsDao.goodsDetailImage(goods_id);
		goodsMap.put("imageList", imageList);
		
		ReviewBean reviewBean=new ReviewBean();
		reviewBean.setGoods_id(goods_id);
		reviewBean.setChapter(chapter);
		reviewBean.setPageNum(pageNum);
		
		ArrayList reviewList =goodsDao.reviewList(reviewBean);
		goodsMap.put("reviewList", reviewList);
		return goodsMap;
	}
	
	public void addReview(ReviewBean reviewBean) throws Exception{
		goodsDao.insertReview(reviewBean);
	}
}
