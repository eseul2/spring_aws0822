package com.myaws.myapp.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.service.MemberService;
import com.myaws.myapp.service.Test;

@Controller
@RequestMapping(value="/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired /* 객체 생셩시켰으니 주입시킨다. 업캐스팅 */
	private MemberService memberService;
	
	/* 비밀번호를 인코드해서 암호화 하는 작업? */
	@Autowired(required=false)
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	
	// 해당되는 가상경로와 이 메소드를 매핑시켜주는 클래스. 넘겨오는 방식은 get방식으로 받겠다.
	@RequestMapping(value="memberJoin.aws",method=RequestMethod.GET)
	public String memberJoin() {
	
		logger.info("memberJoin들어옴");
		
		return "WEB-INF/member/memberJoin";  //핸들러 어뎁터가 해당되는 주소를 찾아서 
 	}
	
	
	@RequestMapping(value="memberJoinAction.aws",method=RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction들어옴");
		logger.info("bCryptPasswordEncoder==> " + bCryptPasswordEncoder );
		
		// 넘어온 비밀번호를 비밀번호 암호화 시키기
		String memberpw_enc = bCryptPasswordEncoder.encode(mv.getMemberpw());
		// 암호화 시킨 비밀번호를 다시 넘기기
		mv.setMemberpw(memberpw_enc);
		
		int value = memberService.memberInsert(mv);
		logger.info("value: " + value);  
		
		String path="";
		if (value == 1) { /* 성공하면 */
			path ="redirect:/";
		} else if (value == 0) { /* 실패하면 */
			path ="redirect:/member/memberJoin.aws";
		}
		return path;   
 	}
	
	
	
	@RequestMapping(value="memberLogin.aws", method=RequestMethod.GET)
	public String memberLogin() {
		
		return "WEB-INF/member/memberLogin";
	}
	

	
	@RequestMapping(value="memberLoginAction.aws", method=RequestMethod.POST)
	public String memberLoginAction(
			@RequestParam("memberid") String memberid, 
			@RequestParam("memberpw") String memberpw,
			RedirectAttributes rttr 
			) {
		
		MemberVo mv = memberService.memberLoginCheck(memberid);
		//저장된 비밀번호를 가져온다. 
		
		String path = "";
		if(mv != null) { // 객체값이 있으면
		String reservedPw = mv.getMemberpw();
		
		 if(bCryptPasswordEncoder.matches(memberpw, reservedPw)) { //저장된 값을 꺼낸다.
			 //System.out.println("비밀번호 일치");
			 rttr.addAttribute("midx",mv.getMidx());
			 rttr.addAttribute("memberId",mv.getMemberid());
			 rttr.addAttribute("memberName",mv.getMembername());
			 
			 path ="redirect:/";
		  }else {
			  //rttr.addAttribute("midx","");
			  //rttr.addAttribute("memberId","");
			  //rttr.addAttribute("memberName","");
			  rttr.addFlashAttribute("msg","아이디/비밀번호를 확인해주세요");
			  path = "redirect:/member/memberLogin.aws";
		  }
		}else {
			  //rttr.addAttribute("midx","");
			  //rttr.addAttribute("memberId","");
			  //rttr.addAttribute("memberName","");
			  rttr.addFlashAttribute("msg","해당하는 아이디가 없습니다."); //일회성 메소드 메세지가 한 번만 나온다
			path = "redirect:/member/memberLogin.aws";
		}
		//회원정보를 세션에 담는다.
		
		return path;
	}
	
	//로그인이 안되면 다시 로그인 페이지로 가고
	//로그인이 되면 메인으로 가라
	
	
	@ResponseBody
	@RequestMapping(value="memberIdCheck.aws", method=RequestMethod.POST)
	public JSONObject memberIdCheck(@RequestParam("memberId") String memberId) {
		
		int cnt = memberService.memberIdCheck(memberId);
		
		JSONObject obj = new JSONObject();
		obj.put("cnt", cnt);
		
		return obj;
	}
	
	
	
	
	@RequestMapping(value="memberList.aws", method=RequestMethod.GET)
	public String memberList(Model model) {
		
		// 멤버 서비스 안에다가 memberSelectAll 메소드를 만든다. 
		ArrayList<MemberVo> alist = memberService.memberSelectAll();
		
		model.addAttribute("alist",alist);
		
		return "WEB-INF/member/memberList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
