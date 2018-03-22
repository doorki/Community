package com.ktds.member.service;

import com.ktds.community.dao.CommunityDao;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.SHA256Util;

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
		String salt = SHA256Util.generateSalt();
		memberVO.setSalt(salt);
		
		String password = memberVO.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		memberVO.setPassword(password);
		
		return memberDao.insertMember(memberVO) > 0;
	}
	@Override
	public MemberVO readMember(MemberVO memberVO) {
		
		//1. 	사용자의 id 로 salt 가져오기
		String salt = memberDao.selectSalt(memberVO.getEmail());
		if (salt == null) {
			salt="";
		}
		
		// 2. 솔트로 암호화하기 
		String password = memberVO.getPassword();
		password = SHA256Util.getEncrypt(password, salt);
		memberVO.setPassword(password);
		
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

	@Override
	public boolean readCountMemberEmail(String email) {
		return memberDao.selectCountMemberEmail(email) > 0;
	}
	@Override
	public boolean readCountMemberNickname(String nickname) {
		return memberDao.selectCountMemberNickname(nickname) >0;
	}
}

//로그인절차
