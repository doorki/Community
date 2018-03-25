package com.ktds.community.dao;

import java.util.List;

import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

public interface CommunityDao {
	public int selectCountAll(CommunitySearchVO communitySearchVO);
	
	public List<CommunityVO> selectAll(CommunitySearchVO communitySearchVO);

	public CommunityVO selectOne(int id);
	
	public int selectMyCommunitiesCount(int userId);
	
	public List<CommunityVO> selectMyCommunities(int userId);
	
	public int insertCommunity(CommunityVO communityVO);
	//cud 의 리턴타입은 무조건 int r은 리턴할 객체 타입
	public int incrementViewCount(int id);
	
	public int incrementRecommendCount(int id);
	
	public int deletePage(int id);
	
	public int deleteCommunities(List<Integer> ids , int userId);

	public int deleteMyCommunities(int userId);
	
	public int updateCommunity(CommunityVO communityVO);
	
}
