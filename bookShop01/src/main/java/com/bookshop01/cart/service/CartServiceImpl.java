package com.bookshop01.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.bookshop01.cart.dao.CartDao;
import com.bookshop01.cart.vo.CartBean;
import com.bookshop01.goods.vo.GoodsBean;

@Service("cartService")
public class CartServiceImpl  implements CartService{
	@Autowired
	CartDao cartDao;
	public HashMap<String ,ArrayList> myCartList(CartBean cartBean) throws Exception{
		HashMap<String,ArrayList> cartHash=new HashMap<String,ArrayList>();
		ArrayList<CartBean> my_cart_list=cartDao.myCartList(cartBean);
		if(my_cart_list.size()==0){ //카트에 저장된 상품이없는 경우
			return null;
		}
		ArrayList<GoodsBean> my_goods_list=cartDao.myGoodsList(my_cart_list);
		cartHash.put("my_cart_list", my_cart_list);
		cartHash.put("my_goods_list",my_goods_list);
		return cartHash;
	}
	
	public boolean searchCart(CartBean cartBean) throws Exception{
		int res=0;
		res=cartDao.searchCart(cartBean);
		if(res==0)
			return false;
		else
			return true;
		
	}	
	public void addCart(CartBean cartBean) throws Exception{
		cartDao.addCart(cartBean);
	}
	
	public boolean modifyCartQty(CartBean cartBean) throws Exception{
		boolean result=true;
		cartDao.modifyCartQty(cartBean);
		return result;
	}
	public void deleteCartGoods(int cart_id) throws Exception{
		cartDao.deleteCartGoods(cart_id);
	}
	
}
