package com.bookshop01.admin.goods.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface AdminGoodsController {
	public ModelAndView adminGoodsMain(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView saveNewGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView addNewGoods(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView modifyGoodsForm(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void modifyGoodsInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView modifyGoodsImageForm(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView  modifyGoodsImage(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void  deleteImageFile(HttpServletRequest request, HttpServletResponse response)  throws Exception;
//	public  ModelAndView adminGoodsForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	
}
