package com.bookshop01.member.dao;

import com.bookshop01.member.vo.MemberBean;

public interface MemberDao {
	public MemberBean login(MemberBean memberBean) throws Exception;
	public void addMember(MemberBean memberBean) throws Exception;
}
