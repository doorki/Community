package com.ktds.reply.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktds.community.constants.Member;
import com.ktds.member.vo.MemberVO;
import com.ktds.reply.service.ReplyService;
import com.ktds.reply.vo.ReplyVO;

@Controller
public class ReplyController {
	private ReplyService replyService;
	
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
	
		@RequestMapping("/api/reply/{communityId}")
		@ResponseBody
		public List<ReplyVO> getAllReplies(@PathVariable int communityId){
			return replyService.readAllReplies(communityId);
}
		@RequestMapping(value="/api/reply/{communityId}", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> createReply(@PathVariable int communityId,
				HttpSession session,
				ReplyVO replyVO){
			MemberVO member = (MemberVO) session.getAttribute(Member.USER);
			replyVO.setUserId(member.getId());
			replyVO.setCommunityId(communityId);
			
			boolean isSuccess = replyService.createReply(replyVO);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", isSuccess);
			result.put("reply", replyVO);
			return result;
		}
}
