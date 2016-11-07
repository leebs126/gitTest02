package com.bookshop01.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface OrderController {
	public ModelAndView myOrderGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
}
