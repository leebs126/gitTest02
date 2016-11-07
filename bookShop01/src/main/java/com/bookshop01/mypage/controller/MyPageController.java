package com.bookshop01.mypage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface MyPageController {
	public ModelAndView myPageMain(HttpServletRequest request, HttpServletResponse response)  throws Exception ;
	public ModelAndView myOrderDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView cancelMyOrder(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView listMyOrderHistory(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView myInfoDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void modifyMyInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
