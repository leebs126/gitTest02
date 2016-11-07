package com.bookshop01.member.service;

import com.bookshop01.member.vo.MemberBean;

public interface MemberService {
	public MemberBean login(MemberBean memberBean) throws Exception;
	public void addMember(MemberBean memberBean) throws Exception;
}
