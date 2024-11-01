package com.myaws.myapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.service.MemberService;
import com.myaws.myapp.service.Test;

@Controller
@RequestMapping(value="/member/")
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	//@Autowired // 생성하지 않고 주입을 시켰다.
	//private Test tt;
	
	@Autowired /* 객체 생셩시켰으니 주입시킨다. 업캐스팅 */
	MemberService memberService;
	
	
	// 해당되는 가상경로와 이 메소드를 매핑시켜주는 클래스. 넘겨오는 방식은 get방식으로 받겠다.
	@RequestMapping(value="memberJoin.aws",method=RequestMethod.GET)
	public String memberJoin() {
	
		logger.info("memberJoin들어옴");
		
		//logger.info("tt값은?" + tt.test());
	
		return "WEB-INF/member/memberJoin";  //핸들러 어뎁터가 해당되는 주소를 찾아서 
 	}
	
	
	@RequestMapping(value="memberJoinAction.aws",method=RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction들어옴");
		
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
	
	
	
	
	

}
