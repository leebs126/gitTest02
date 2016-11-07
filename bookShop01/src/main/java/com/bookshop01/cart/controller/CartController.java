package com.bookshop01.cart.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface CartController {
	public ModelAndView myCartMain(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void addCart(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void modifyCartQty(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView deleteCartGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	

}
