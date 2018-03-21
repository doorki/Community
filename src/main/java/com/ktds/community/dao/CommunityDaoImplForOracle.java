package com.ktds.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community.vo.CommunityVO;

public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDao {

	/**
	 * SqlSessionDaoSupport
	 * sqlSessionTemplate Bean 객체를 가지고 있음.
	 *
	 */
	@Override
	public List<CommunityVO> selectAll() {
		return getSqlSession().selectList("CommunityDao.selectAll");
	}

	@Override
	public CommunityVO selectOne(int id) {
		return getSqlSession().selectOne("CommunityDao.selectOne", id);
	}									//인터페이스 명   //메소드명 붙여 넣기
	
	@Override
	public int selectMyCommunitiesCount(int userId) {

		return getSqlSession().selectOne("CommunityDao.selectMyCommunitiesCount", userId);
	}

	@Override
	public int insertCommunity(CommunityVO communityVO) {
		return getSqlSession().insert("CommunityDao.insertCommunity", communityVO);
	}

	@Override
	public int incrementViewCount(int id) {
		return getSqlSession().update("CommunityDao.incrementViewCount", id);
	}

	@Override
	public int incrementRecommendCount(int id) {
		return getSqlSession().update("CommunityDao.incrementRecommendCount", id);		
	}

	@Override
	public int deletePage(int id) {
		return getSqlSession().delete("CommunityDao.deleteList", id);
	}

	@Override
	public int deleteMyCommunities(int userId) {
		// TODO Auto-generated method stub
		return getSqlSession().delete("CommunityDao.deleteMyCommunities", userId);
	}

	@Override
	public int deleteCommunities(List<Integer> ids, int userId) {
		//파라미터를 두개 보내고 싶을때 맵으로 묶어서 두개를 보내준다!
		//원래는 하나씩 밖에못보냄!
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids",ids);
		params.put("userId", userId);
		return getSqlSession().delete("CommunityDao.deleteCommunities", params );
	}
	@Override
	public int updateCommunity(CommunityVO communityVO) {
		return getSqlSession().update("CommunityDao.updateCommunity", communityVO);
	}

	@Override
	public List<CommunityVO> selectMyCommunities(int userId) {
		return getSqlSession().selectList("CommunityDao.selectMyCommunities" , userId);
	}


	
	
}
