package com.bookshop01.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookshop01.goods.dao.GoodsDaoImpl;
import com.bookshop01.member.dao.MemberDao;
import com.bookshop01.member.vo.MemberBean;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao memberDao;
	public MemberBean login(MemberBean memberBean) throws Exception{
		return memberDao.login(memberBean);
	}
	
	public void addMember(MemberBean memberBean) throws Exception{
		memberDao.addMember(memberBean);
	}
	
	
}
