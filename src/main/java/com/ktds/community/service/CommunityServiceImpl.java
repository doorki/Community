package com.ktds.community.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.PagerFactory;
import io.github.seccoding.web.pager.explorer.ClassicPageExplorer;
import io.github.seccoding.web.pager.explorer.PageExplorer;

public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;
	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public PageExplorer getAll(CommunitySearchVO communitySearchVO){
//	public List<CommunityVO> getAll(CommunitySearchVO communitySearchVO){
										//오라클 페이저를 쓰겠다.
		Pager pager = PagerFactory.getPager(Pager.ORACLE, communitySearchVO.getPageNo()+"",communityDao.selectCountAll(communitySearchVO));

		PageExplorer pageExplorer = pager.makePageExplorer(ClassicPageExplorer.class, communitySearchVO);

		pageExplorer.setList(communityDao.selectAll(communitySearchVO));

		return pageExplorer;
	}

	@Override
	public boolean createCommunity(CommunityVO communityVO) {
		//html 띄어쓰기 바꾸기 \n-> br/
		String body = communityVO.getBody();

		body = body.replace("\n", "<br/>");
		//body다시 넣어주기
		communityVO.setBody(body);

//		if( filter(body) || filter(communityVO.getTitle())) {
//
//			return false;
//
//		}

		int insertCount = communityDao.insertCommunity(communityVO);
		return insertCount > 0;

	}

	@Override
	public CommunityVO getOne(int id) {
//		if( communityDao.incrementViewCount(id) > 0 ) {
				//업데이트를 잘 수행했다면. select One을 수행해라.
			return communityDao.selectOne(id);
//		}
//		return null;
	}

	@Override
	public boolean incrementVC(int id) {

		return communityDao.incrementViewCount(id) > 0 ;
	}

	@Override
	public void incrementRC(int id) {
		// TODO Auto-generated method stub
		communityDao.incrementRecommendCount(id);
	}

	public boolean filter(String str) {
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕");
		blackList.add("씨");
		blackList.add("개");
		blackList.add("2식");
		blackList.add("1식");

		//str ==> 남편은 2식이에요
		String[] splitString = str.split(" ");

		for( String word : splitString ) {

			for(String blackString : blackList) {
				if( word.contains(blackString)) {
					return true;
				}
			}
		}
			return true;
	}

	@Override
	public boolean deleteCommunity(int id) {
		if(communityDao.deletePage(id) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO)>0 ;

	}

	@Override
	public int readMyCommunitiesCount(int userId) {
		return communityDao.selectMyCommunitiesCount(userId);
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userId) {
		// TODO Auto-generated method stub
		return communityDao.selectMyCommunities(userId);
	}

	@Override
	public boolean deleteCommunities(List<Integer> ids, int userId) {
		return communityDao.deleteCommunities(ids, userId) >0;
	}

}
