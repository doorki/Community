package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDao {
						//	전달된 파라미터를 넣는다 
	public int insertMember(MemberVO memberVO);  
	
	public MemberVO selectMember(MemberVO memberVO);
	
	public int deleteMember(int id);
}
