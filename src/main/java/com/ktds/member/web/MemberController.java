package com.ktds.member.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.constants.Member;
import com.ktds.community.service.CommunityService;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {
	
	private MemberService memberService;
	private CommunityService communityService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
		
	}
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) { //세션을 바로 가지고 올 수 있음
		
		if( session.getAttribute(Member.USER) != null ) {
			return "redirect:/";
		}//로그인이 필요한 페이지에 모두 넣어주면됨
		
		return "member/login";
	}
	@RequestMapping(value= "/regist", method= RequestMethod.GET)
	public String viewRegistPage() {
		return "member/regist";
	}
	@RequestMapping(value ="/regist", method= RequestMethod.POST)
	public ModelAndView doRegistPage(@ModelAttribute("registForm") @Valid MemberVO memberVO,Errors errors)
		{
		if (errors.hasErrors()) {
			return new ModelAndView("member/regist");
			
		}
		if( memberService.createMember(memberVO) ) {
			
			return new ModelAndView("redirect:/login");
			
		}
			return new ModelAndView("member/regist");
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String doLoginAction(@ModelAttribute("loginForm") @Valid MemberVO memberVO, 
					Errors errors, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		MemberVO loginMember= memberService.readMember(memberVO);
		if (loginMember != null) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
		}
		return "redirect:/login";
		}
	/*	if( memberVO.getId() == null || memberVO.getId().length() == 0 ) {
			session.setAttribute("status", "emptyId");
			return new ModelAndView("redirect:/login");
		}
		
		if( memberVO.getPassword() == null || memberVO.getPassword().length() == 0 ) {
			session.setAttribute("status", "emptyPassword");
			return new ModelAndView("redirect:/login");
		}*/
		
		
		
			
//		if( memberVO.getEmail().equals("admin") &&
//			memberVO.getPassword().equals("1234")) {
//			
//			memberVO.setNickname("관리자");
//			
//			session.setAttribute(Member.USER, memberVO);//인터페이스 만들어준거
//			//세션에 로그인 저장 상태유지
//			
//			session.removeAttribute("status");
//			//로그인에 성공하면 세션을 지워줌 
//			
//			return new ModelAndView("redirect:/");
//		}
//		
//		
//		if( error.hasErrors() ) {
//			ModelAndView view = new ModelAndView();
//			view.setViewName("member/login");
//			return view;
//		}
//
//	
//		//로그인이 실패했을때
//		session.setAttribute("status", "fail");
//		
////		return new ModelAndView("redirect:/login");
//		return new ModelAndView("redirect:/login");
//		
//		
//
	
	@RequestMapping("/logout")
	public String doLogoutAction(MemberVO id, HttpSession session) {
		//삭제는 하나의 키만 지우는 것
		//세션 소멸 - 세션자체를 날림
			session.invalidate();
			
		return "redirect:/login";
		
	}
	@RequestMapping("/account/delete/process1")
	public String viewVerifyPage() {
		
		return "member/delete/process1";
	}
	@RequestMapping("/account/delete/process2")				//파라미터를 받는기위해 쓰는 어노테이션 파라미가 있어야 작동 URl에서 치고 넘어오면 공백으로 넘어옴
	public ModelAndView viewDeleteMyCommunitiesPage(	//required = false -> 반드시 필요한 파라미터가 아니야 라고 알려주는 것 
			@RequestParam(required=false, defaultValue = "") String password, HttpSession session) {
		
		if ( password.length() == 0 ) {
			return new ModelAndView("error/404");
		}
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		member.setPassword(password);
		
		MemberVO verifyMember = memberService.readMember(member);
		if( verifyMember == null) {
			return new ModelAndView ("redirect:/account/delete/process1");
		}
		//TODO 내가 작성한 게시글의 개수 가져오기.
		int myCommunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getId());
		
		ModelAndView view = new ModelAndView();
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount",myCommunitiesCount);
		
		String uuid = UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token",uuid);
		//	UUID 절대중복이 되지않는 난수. 시간을 베이스로 난수를 만듬.
		return view;
		
	}
	
	
	@RequestMapping("/account/delete/{deleteFlag}")
	public String doDeleteAction(HttpSession session ,
						@RequestParam(required=false, defaultValue="") String token,
						@PathVariable String deleteFlag) {
		String sessionToken = (String) session.getAttribute("__TOKEN__");
		if ( sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		if ( member == null) {
			return "redirect:/login";
		}
	
		int id =member.getId();
		if(memberService.leaveMember(id, deleteFlag)) {
			session.invalidate();
		}
		return "member/delete/delete";
	}
}
