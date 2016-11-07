package com.bookshop01.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookshop01.goods.vo.GoodsBean;
import com.bookshop01.goods.vo.ImageFileBean;
import com.bookshop01.member.vo.MemberBean;
import com.bookshop01.order.vo.OrderBean;

@Repository("adminMemberDao")
public class AdminMemberDaoImpl  implements AdminMemberDao{
	@Autowired
	private SqlSession sqlSession;
	
	
	public ArrayList<MemberBean> listMember(HashMap condMap) throws Exception{
		ArrayList<MemberBean>  memberList=(ArrayList)sqlSession.selectList("mapper.admin.member.listMember",condMap);
		return memberList;
	}
	
	public MemberBean memberDetail(String member_id) throws Exception{
		MemberBean memberBean=(MemberBean)sqlSession.selectOne("mapper.admin.member.memberDetail",member_id);
		return memberBean;
	}
	
	public void modifyMemberInfo(HashMap memberMap) throws Exception{
		sqlSession.update("mapper.admin.member.modifyMemberInfo",memberMap);
	}
	
	

}
