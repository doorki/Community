//package com.ktds.community.dao;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.ktds.community.vo.CommunityVO;
//
//public class CommunityDaoImpl implements CommunityDao {
//
//	private List<CommunityVO> communityList;
//	//private final Logger logger = 
//	public CommunityDaoImpl() {
//		communityList = new ArrayList<CommunityVO>();
//	}
//
//	@Override
//	public List<CommunityVO> selectAll() {
//		return communityList;
//	}
//
//	@Override
//	public int insertCommunity(CommunityVO communityVO) {
//		communityVO.setId(communityList.size() + 1);
//		communityList.add(communityVO);
//		return 1;
//	}
//
//	@Override
//	public CommunityVO selectOne(int id) {
//
//		for (CommunityVO community : communityList) {
//			if (community.getId() == id) {
//				return community;
//			}
//
//		}
//		return null;
//
//	}
//
//	@Override
//	public int incrementViewCount(int id) {
//		for( CommunityVO community : communityList ) {
//			if( community.getId()==id ) {
//				community.setViewCount( community.getViewCount() +1);
//				//get viewCount 한후에 setViewcount해라.라고하는 메소드.
//				
//				return 1;
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public int incrementRecommendCount(int id) {
//		for( CommunityVO community : communityList ) {
//			if( community.getId() == id) {
//				community.setRecommendCount( community.getRecommendCount()+1);
//			}
//				return 1;
//		}
//		return 0;
//	}
//
//	@Override
//	public int deletePage(int id) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int deleteMyCommunities(int userId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int updateCommunity(CommunityVO communityVO) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int selectMyCommunitiesCount(int userId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	
//}
