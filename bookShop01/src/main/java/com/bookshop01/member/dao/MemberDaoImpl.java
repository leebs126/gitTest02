package com.bookshop01.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bookshop01.member.vo.MemberBean;

@Repository("memberDao")
public class MemberDaoImpl  implements MemberDao{
	@Autowired
	private SqlSession sqlSession;	
	public MemberBean login(MemberBean memberBean) throws Exception{
		MemberBean member=sqlSession.selectOne("mapper.member.login",memberBean);
	   return member;
	}
	
	public void addMember(MemberBean memberBean) throws Exception{
		sqlSession.insert("mapper.member.addMember",memberBean);
	}

}
