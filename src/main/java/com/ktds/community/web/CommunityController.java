package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.vo.ActionHistory;
import com.ktds.actionhistory.vo.ActionHistoryVO;
import com.ktds.community.constants.Member;
import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.DownloadUtil;

import io.github.seccoding.web.pager.explorer.PageExplorer;

@Controller
public class CommunityController {

	private CommunityService communityService;

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	@RequestMapping("/reset")
	public String viewInitListPage(HttpSession session) {
		session.removeAttribute("__SEARCH__");
		return "redirect:/";
		
	}
	@RequestMapping("/")
	public ModelAndView viewListPage(CommunitySearchVO communitySearchVO, HttpSession session) {
			//데이터가 안넘어 왔을 경우
		// 1. 리스트페이지에서 처음 접근했을 떄 
		//2. 글 내용을 보고 , 목록보기 링크를 클릭했을 때 
		if( communitySearchVO.getPageNo() < 0) {
			//session에 저장된 CommunitSeacrchVO 를 가져옴. 
			
			communitySearchVO = (CommunitySearchVO) session.getAttribute("__SEARCH__");
			//session에 저장된 CommunitSeacrchVO 가 없을경우 pageNo=0으로 초기화 
			if (communitySearchVO == null) {
				communitySearchVO = new CommunitySearchVO();
				communitySearchVO.setPageNo(0);
			}
			
		}
		session.setAttribute("__SEARCH__", communitySearchVO);
		
		
		ModelAndView view = new ModelAndView();

		// if (session.getAttribute(Member.USER) == null) {
		// // /WEB-INF/view/community/list.jsp
		// return new ModelAndView("redirect:/login");
		// }
		
		
		view.setViewName("community/list");
		view.addObject("search", communitySearchVO); // 내가 검색을 무엇으로 했는지 알려줌 .
		PageExplorer pageExplorer = communityService.getAll(communitySearchVO);

		view.addObject("pageExplorer", pageExplorer);
	
		return view;
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET) // get
	// @GetMapping("/write")
	public String viewWritePage(HttpSession session) { // 데이터를 주면 ModelAndView 아니면 String도 상관 없음
		// if (session.getAttribute(Member.USER) == null) {
		// return "redirect:/login";
		// }
		return "community/write";

	}

	@RequestMapping(value = "/write", method = RequestMethod.POST) // post
	// @PostMapping("/write")
	public ModelAndView doWrite(@ModelAttribute("writeForm") @Valid // valid 다음 errors 순서 중요.(뒤에 와야함)
	CommunityVO communityVO, Errors errors, HttpSession session, HttpServletRequest request) {

		// if (session.getAttribute(Member.USER) == null) {
		// return new ModelAndView("redirect:/");
		// // HttpServletRequest request
		// // String title = request.getParameter("title");
		// }

		ModelAndView view = new ModelAndView();
		if (errors.hasErrors()) {
			view.setViewName("community/write");
			view.addObject("communityVO", communityVO);
			return view;
		}
		// 작성자의 IP를 얻어오는 코드
		String requestIp = request.getRemoteAddr();
		communityVO.setRequestIp(requestIp);
		// if (communityVO.getTitle() == null || communityVO.getTitle().length() == 0) {
		// session.setAttribute("status", "emptyTitle");
		// return "redirect:/write";
		// }else {
		// if (communityVO.getBody() == null || communityVO.getBody().length() == 0 ) {
		// session.setAttribute("status", "emptyBody");
		// return "redirect:/write";
		// }
		// else {
		// }
		// if (communityVO.getWriteDate() == null || communityVO.getWriteDate().length()
		// == 0 ) {
		// session.setAttribute("status", "emptywriteDate");
		// return "redirect:/write";
		// }
		// }
		communityVO.save();
		boolean isSuccess = communityService.createCommunity(communityVO); // 이것좀 만들어봐

		if (isSuccess) {
			return new ModelAndView("redirect:/reset");
		}
		return new ModelAndView("redirect:/write");
	}

	// session.removeAttribute("status");
	// return "redirect:/";
	//
	// }
	//
	// return "redirect:/write";
	// }
	@RequestMapping("/delete/{id}")
	public String doDeleteAction(HttpSession session, @PathVariable int id,
								@RequestAttribute ActionHistoryVO actionHistory) {
		CommunityVO community = communityService.getOne(id);
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		actionHistory.setReqType(ActionHistory.ReqType.COMMUNITY);
		String log = String.format(ActionHistory.Log.DELETE, member.getId(), community.getTitle(),community.getBody());
		actionHistory.setLog(log);
		// session object타입
		// 리턴타입이 달라서 MemberVO타입으로명시적 형변환
		// 상속 구현관계일때만 캐스팅이가능
		boolean isMyCommunity = member.getId() == community.getUserId();
		if (isMyCommunity && communityService.deleteCommunity(id)) {
			return "redirect:/";
		}
		return "error/404";
	}

	@RequestMapping("/modify/{id}")
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session,
										@RequestAttribute ActionHistoryVO actionHistory) {
		CommunityVO community = communityService.getOne(id);
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		actionHistory.setReqType(ActionHistory.ReqType.COMMUNITY);
		
		String log = String.format(ActionHistory.Log.UPDATE,  member.getId(), community.getTitle(), community.getBody());
		
		actionHistory.setLog(log);

		int userId = member.getId();

		if (userId != community.getUserId()) {
			return new ModelAndView("WEB-INF/view/error/404");
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", community);
		view.addObject("mode", "modify");
		return view;
	}

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session, HttpServletRequest request,
			@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors, @RequestAttribute ActionHistoryVO actionHistory) {
		// 해야할것
		// 0.ip변경확인
		// 1.제목변경확인
		// 2. 내용변경확인
		// 3.파일변경확인.
		// 4. 변경 유무 확인.
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id);
		if (member.getId() != originalVO.getUserId()) {
			return "error/404";
		}
		if (errors.hasErrors()) {
			return "redirect:/modify/" + id;
		}
	

		CommunityVO newCommunity = new CommunityVO();
		newCommunity.setId(originalVO.getId());
		newCommunity.setUserId(member.getId());
		boolean isModify = false;

		String asIs="";
		String toBe="";
		
		// 1. IP변경확인
		String ip = request.getRemoteAddr();
		if (!ip.equals(originalVO.getrequestIp())) {
			newCommunity.setRequestIp(ip);
			isModify = true;
			asIs+="ip :"+originalVO.getrequestIp()+"</br>";
			toBe+="ip :"+communityVO.getrequestIp()+"</br>";
		}
		// 2.제목 변경확인
		if (!originalVO.getTitle().equals(communityVO.getTitle())) {
			newCommunity.setTitle(communityVO.getTitle());
			isModify = true;
			asIs+="Title :"+originalVO.getTitle()+"</br>";
			toBe+="Title :"+communityVO.getTitle()+"</br>";
		}
		// 3.내용 변경확인
		if (!originalVO.getBody().equals(communityVO.getBody())) {
			newCommunity.setBody(communityVO.getBody());
			isModify = true;
			asIs+="Body :"+originalVO.getBody()+"</br>";
			toBe+="Body :"+communityVO.getBody()+"</br>";
		}
		// 4. 파일 변경확인
		if (communityVO.getDisplayFilename().length() > 0) {
			File file = new File("/Users/KimMoonki/Documents/workspace/uploadFiles" + communityVO.getDisplayFilename());
			file.delete();
			communityVO.setDisplayFilename("");
			asIs+="File :"+originalVO.getDisplayFilename()+"</br>";
			toBe+="File :"+communityVO.getDisplayFilename()+"</br>";
		} else {
			communityVO.setDisplayFilename(originalVO.getDisplayFilename());
		}

		// if( !originalVO.getDisplayFilename().equals(
		// communityVO.getDisplayFilename())) {
		// newCommunity.setDisplayFilename( communityVO.getDisplayFilename());
		// isModify = true;
		// }

		// 5. 변경 유무 확인
		// boolean isModify = false;생성후
		// isModify = true; 작성해줌
		if (isModify) {
			// 6.UPdate 하는 serviceCode;
			communityService.updateCommunity(newCommunity);
		}
		return "redirect:/view/" + id;
	}

	@RequestMapping("/view/{id}") // id 이름 같아야함
	public ModelAndView viewViewPage(HttpSession session, @PathVariable int id) {
		// 로그인한 회원만 들어올 수 있다 아이디값 변수
		// if (session.getAttribute(Member.USER) == null) {
		// // /WEB-INF/view/community/list.jsp
		// return new ModelAndView("redirect:/login");
		// }
		ModelAndView view = new ModelAndView();

		view.setViewName("community/view");

		// TODO id 로 게시글 얻어오기
		CommunityVO community = communityService.getOne(id);
		view.addObject("community", community);
		return view;

	}

	@RequestMapping("/read/{id}")
	public String incrementViewCountFunc(@PathVariable int id, HttpSession session,
										@RequestAttribute ActionHistoryVO actionHistory) {
		
		CommunityVO community = communityService.getOne(id);
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		actionHistory.setReqType(ActionHistory.ReqType.COMMUNITY);
		
		String log = String.format(ActionHistory.Log.READ,  member.getId(), community.getTitle(), community.getBody());
		
		actionHistory.setLog(log);
		
		
		if (communityService.incrementVC(id)) {
			return "redirect:/view/" + id;
		}
		return "redircet:/";
	}

	@RequestMapping("/recommend/{id}")
	public String recommendCount(@PathVariable int id,
									@RequestAttribute ActionHistoryVO actionHistory) {
		
		actionHistory.setReqType(ActionHistory.ReqType.COMMUNITY);
		String log = String.format(ActionHistory.Log.RECOMMEND, id);
		actionHistory.setLog(log);
		
		communityService.incrementRC(id);

		return "redirect:/view/" + id;
	}

	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();

		DownloadUtil download = new DownloadUtil("/Users/KimMoonki/Documents/workspace/uploadFiles/" + filename);

		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

}
