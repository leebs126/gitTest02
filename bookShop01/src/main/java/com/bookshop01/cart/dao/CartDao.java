package com.bookshop01.cart.dao;

import java.util.ArrayList;

import com.bookshop01.cart.vo.CartBean;
import com.bookshop01.goods.vo.GoodsBean;

public interface CartDao {
	public ArrayList<CartBean> myCartList(CartBean cartBean) throws Exception;
	public ArrayList<GoodsBean> myGoodsList(ArrayList<CartBean> cartList) throws Exception;
	public int searchCart(CartBean cartbean) throws Exception;
	public void addCart(CartBean cartBean) throws Exception;
	public void modifyCartQty(CartBean cartBean) throws Exception;
	public void deleteCartGoods(int cart_id) throws Exception;
	
	

}
