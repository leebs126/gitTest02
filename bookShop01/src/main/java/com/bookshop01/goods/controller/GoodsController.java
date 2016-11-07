package com.bookshop01.goods.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface GoodsController  {
	public ModelAndView goodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void keywordSearch(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView searchGoods(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
