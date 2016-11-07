package com.bookshop01.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.vo.OrderBean;

public interface AdminMemberDao {
	public ArrayList<MemberBean> listMember(HashMap condMap) throws Exception;
	public MemberBean memberDetail(String member_id) throws Exception;
	public void modifyMemberInfo(HashMap memberMap) throws Exception;
}
