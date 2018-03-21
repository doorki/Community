package com.ktds.member.service;

import com.ktds.community.dao.CommunityDao;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	
	private MemberDao memberDao;
	private CommunityDao communityDao;

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public boolean createMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return memberDao.insertMember(memberVO) > 0;
	}
	@Override
	public MemberVO readMember(MemberVO memberVO) {
		return memberDao.selectMember(memberVO);
	}
	@Override
	public boolean deleteMember(int id) {
		
		communityDao.deleteMyCommunities(id);
		
		return memberDao.deleteMember(id) > 0;
}

	
	@Override
	public boolean leaveMember(int id, String deleteFlag) {
		if(deleteFlag.equals("y")) {
			
			communityDao.deleteMyCommunities(id);
			}
		if (memberDao.deleteMember(id)>0) {
			return true;
		}
		return false;
	}
}

//로그인절차
