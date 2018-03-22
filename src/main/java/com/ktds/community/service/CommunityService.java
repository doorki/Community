package com.ktds.community.service;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityService {

	public List<CommunityVO> getAll();

	public boolean createCommunity(CommunityVO communityVO);  
	
	public CommunityVO getOne(int id);

	public boolean incrementVC(int id);

	public int readMyCommunitiesCount(int userId);

	public List<CommunityVO> readMyCommunities(int userId);

	public void incrementRC(int id);


	public boolean deleteCommunity(int id);
	
	public boolean deleteCommunities(List<Integer> ids, int userId);

	public boolean updateCommunity(CommunityVO CommunityVO);
}
