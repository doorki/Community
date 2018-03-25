package com.ktds.reply.dao;

import java.util.List;

import com.ktds.reply.vo.ReplyVO;

public interface ReplyDao {

	public List<ReplyVO> selectAllReplies(int communityId);
	public int insertReply(ReplyVO replyVO);
	public ReplyVO selectOneReply(int replyId);
	
}
