package com.bookshop01.admin.goods.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.order.vo.OrderBean;

@Repository("adminGoodsDao")
public class AdminGoodsDaoImpl  implements AdminGoodsDao{
	@Autowired
	private SqlSession sqlSession;
	
	public String addNewGoods(GoodsBean newGoodsBean) throws Exception {
		sqlSession.insert("mapper.admin.goods.addNewGoods",newGoodsBean);
		return newGoodsBean.getGoods_id();
	}
	
	public void addImageFile(ArrayList fileList){
		for(int i=0; i<fileList.size();i++){
			ImageFileBean fileBean=(ImageFileBean)fileList.get(i);
			sqlSession.insert("mapper.admin.goods.addImageFile",fileBean);
		}
	}
		
	
	public ArrayList<GoodsBean>listNewGoods(HashMap condMap) throws Exception{
		ArrayList<GoodsBean>  goodsList=(ArrayList)sqlSession.selectList("mapper.admin.goods.list_new_goods",condMap);
		return goodsList;
	}
	
	public GoodsBean goodsDetail(String goods_id) throws Exception{
		GoodsBean goodsBean = new GoodsBean();
		goodsBean=(GoodsBean)sqlSession.selectOne("mapper.admin.goods.goodsDetail",goods_id);
		return goodsBean;
	}
	
	public ArrayList goodsImageFile(String goods_id) throws Exception {
		ArrayList imageList=new ArrayList();
		imageList=(ArrayList)sqlSession.selectList("mapper.admin.goods.goodsImageFile",goods_id);
		return imageList;
	}
	
	
	
	public void modifyGoodsInfo(HashMap goodsMap) throws Exception{
		sqlSession.update("mapper.admin.goods.modifyGoodsInfo",goodsMap);
		
	}
	
	
	public void deleteImageInfo(String image_id) {
		sqlSession.delete("mapper.admin.goods.deleteImageInfo",image_id);
	}
	
	public void deleteImageInfo(ArrayList fileList) throws Exception{
		String image_id=null;
		for(int i=0; i<fileList.size();i++){
			ImageFileBean bean=(ImageFileBean) fileList.get(i);
			image_id=bean.getImage_id();
			sqlSession.delete("mapper.admin.goods.deleteImageInfo",image_id);	
		}
		
	}
	
	public ArrayList<OrderBean>listOrderGoods(HashMap condMap) throws Exception{
		ArrayList<OrderBean>  orderGoodsList=(ArrayList)sqlSession.selectList("mapper.admin.list_order_goods",condMap);
		return orderGoodsList;
	}	
	
	public void modifyOrderGoods(HashMap hashOrder) throws Exception{
		sqlSession.update("mapper.admin.goods.modifyOrderGoods",hashOrder);
		
	}

	

}
