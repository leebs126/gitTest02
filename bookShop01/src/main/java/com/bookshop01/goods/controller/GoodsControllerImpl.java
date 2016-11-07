package com.bookshop01.goods.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.controller.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.goods.vo.ReviewBean;

@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl  extends BaseController implements GoodsController {
	@Autowired
	GoodsService goodsService;
	
	@RequestMapping(value="/goodsDetail.do" ,method = RequestMethod.GET)
	public ModelAndView goodsDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String goods_id = request.getParameter("goods_id");
		
		String _chapter=request.getParameter("chapter");
		String _pageNum=request.getParameter("pageNum");
		int chapter=0,pageNum=0;
		if(_chapter==null){
			chapter=1;
		}else{
			chapter=Integer.parseInt(_chapter);
		}
		
		if(_pageNum==null){
			pageNum=1;
		}else{
			pageNum=Integer.parseInt(_pageNum);
		}
		
		
		String fileName=getFileName(request);
		HttpSession session=request.getSession();
		HashMap goodsMap=goodsService.goodsDetail(goods_id,chapter,pageNum);
		ModelAndView mav = new ModelAndView(fileName);
		mav.addObject("goodsMap", goodsMap);
		mav.addObject("chapter",chapter);
		mav.addObject("pageNum",pageNum);
		GoodsBean goodsBean=(GoodsBean)goodsMap.get("goods");
		add_goods_in_sticky(goods_id,goodsBean,session);
		return mav;
	}
	
	@RequestMapping(value="/keywordSearch.do" ,method = RequestMethod.GET)
	public void keywordSearch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String keyword = request.getParameter("keyword");
		 System.out.println(keyword);
		if(keyword == null || keyword.equals(""))
		   return ;
	
		keyword = keyword.toUpperCase();
	    List keywordList =goodsService.keywordSearch(keyword);
	    
	    
			 // 제시어 개수 출력
	   PrintWriter out=response.getWriter();
	   out.print(keywordList.size());
	   out.print("|");
	   // 제시어 목록을 CSV 양식으로 출력
	   for(int i=0; i<keywordList.size(); i++){
		  String key = (String)keywordList.get(i);
		  out.print(key);
		  if(i<keywordList.size()-1) {
			   out.print(",");
		  }
	  }
		return ;
	}
	
	@RequestMapping(value="/searchGoods.do" ,method = RequestMethod.GET)
	public ModelAndView searchGoods(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String searchWord = request.getParameter("searchWord");
		String fileName=getFileName(request);
		ArrayList search_goods_list=goodsService.searchGoods(searchWord);
		ModelAndView mav = new ModelAndView(fileName);
		mav.addObject("search_goods_list", search_goods_list);
		return mav;
		
	}
	
	@RequestMapping(value="/addReview.do" ,method = RequestMethod.POST)
	public ModelAndView addReview(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileName=getFileName(request);
		ModelAndView mav = new ModelAndView(fileName);
		
		String review_title=request.getParameter("review_title");
		String review_content=request.getParameter("review_content");
		String review_pw=request.getParameter("review_pw");
		String goods_id=request.getParameter("goods_id");
		
		HttpSession session=request.getSession();
		MemberBean memberBean=(MemberBean)session.getAttribute("member_info");
		String member_id=(String)memberBean.getMember_id();
		
		ReviewBean reviewBean=new ReviewBean();
		reviewBean.setReview_title(review_title);
		reviewBean.setReview_content(review_content);
		reviewBean.setReview_pw(review_pw);
		reviewBean.setGoods_id(goods_id);
		reviewBean.setMember_id(member_id);
		
		goodsService.addReview(reviewBean);
		mav.setViewName("redirect:/goods/goodsDetail.do?goods_id="+goods_id);
		return mav;
		
	}
	
	private void add_goods_in_sticky(String goods_id,GoodsBean goodsBean,HttpSession session){
		boolean already_existed=false;
		ArrayList<GoodsBean> sticky_goods_list; //최근 본 상품 저장 ArrayList
		sticky_goods_list=(ArrayList<GoodsBean>)session.getAttribute("sticky_goods_list");
		
		if(sticky_goods_list!=null){
			if(sticky_goods_list.size()<4){ //미리본 상품 리스트에 상품개수가 세개 이하인 경우
				for(int i=0; i<sticky_goods_list.size();i++){
					GoodsBean _goodsBean=(GoodsBean)sticky_goods_list.get(i);
					if(goods_id.equals(_goodsBean.getGoods_id())){
						already_existed=true;
						break;
					}
				}
				if(already_existed==false){
					sticky_goods_list.add(goodsBean);
				}
			}
			
		}else{
			sticky_goods_list =new ArrayList<GoodsBean>();
			sticky_goods_list.add(goodsBean);
			
		}
		session.setAttribute("sticky_goods_list",sticky_goods_list);
		session.setAttribute("sticky_list_num", sticky_goods_list.size());
	}
}
