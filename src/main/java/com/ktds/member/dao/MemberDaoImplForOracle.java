package com.ktds.member.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.member.vo.MemberVO;
										//마이바티스의 세션을 얻어오는 것
public class MemberDaoImplForOracle extends SqlSessionDaoSupport implements MemberDao {

	@Override
	public int insertMember(MemberVO memberVO) {
			return getSqlSession().insert("MemberDao.insertMember", memberVO);
	}

	@Override
	public MemberVO selectMember(MemberVO memberVO) {
		return getSqlSession().selectOne("MemberDao.selectMember", memberVO);
	}

	@Override
	public int deleteMember(int id) {
		
		return getSqlSession().delete("MemberDao.deleteMember", id);
	}
}
