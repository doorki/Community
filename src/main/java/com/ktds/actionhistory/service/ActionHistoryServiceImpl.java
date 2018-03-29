package com.ktds.actionhistory.service;

import java.util.List;

import com.ktds.actionhistory.dao.ActionHistoryDao;
import com.ktds.actionhistory.vo.ActionHistorySearchVO;
import com.ktds.actionhistory.vo.ActionHistoryVO;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.PagerFactory;
import io.github.seccoding.web.pager.explorer.ListPageExplorer;
import io.github.seccoding.web.pager.explorer.PageExplorer;

public class ActionHistoryServiceImpl implements ActionHistoryService {
		
		ActionHistoryDao actionhistoryDao;


		public void setActionhistoryDao(ActionHistoryDao actionhistoryDao) {
			this.actionhistoryDao = actionhistoryDao;
		}

		@Override
		public boolean createCommunity(ActionHistoryVO actionHistoryVO) {
			return actionhistoryDao.insertActionHistory(actionHistoryVO) >0;
		}

		@Override
		public PageExplorer readAllActionHistory(ActionHistorySearchVO actionHistorySearchVO) {
		
			Pager pager = PagerFactory.getPager(Pager.OTHER, 
						actionHistorySearchVO.getPageNo() + "", 
						actionhistoryDao.selectAllActionHistoryCount(actionHistorySearchVO));
			
			PageExplorer pageExplorer = pager.makePageExplorer(
						ListPageExplorer.class, actionHistorySearchVO);
			
			List<ActionHistoryVO> list = actionhistoryDao.selectAllActionHistory(actionHistorySearchVO);
			pageExplorer.setList(list);
			
					
			return pageExplorer; 
		}

		
		
}
