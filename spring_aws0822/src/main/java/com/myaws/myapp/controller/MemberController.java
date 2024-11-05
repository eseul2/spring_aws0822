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
	
	@Autowired /* ��ü ���ͽ������� ���Խ�Ų��. ��ĳ���� */
	private MemberService memberService;
	
	/* ��й�ȣ�� ���ڵ��ؼ� ��ȣȭ �ϴ� �۾�? */
	@Autowired(required=false)
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	
	// �ش�Ǵ� �����ο� �� �޼ҵ带 ���ν����ִ� Ŭ����. �Ѱܿ��� ����� get������� �ްڴ�.
	@RequestMapping(value="memberJoin.aws",method=RequestMethod.GET)
	public String memberJoin() {
	
		logger.info("memberJoin����");
		
		return "WEB-INF/member/memberJoin";  //�ڵ鷯 ��Ͱ� �ش�Ǵ� �ּҸ� ã�Ƽ� 
 	}
	
	
	@RequestMapping(value="memberJoinAction.aws",method=RequestMethod.POST)
	public String memberJoinAction(MemberVo mv) {
		logger.info("memberJoinAction����");
		logger.info("bCryptPasswordEncoder==> " + bCryptPasswordEncoder );
		
		// �Ѿ�� ��й�ȣ�� ��й�ȣ ��ȣȭ ��Ű��
		String memberpw_enc = bCryptPasswordEncoder.encode(mv.getMemberpw());
		// ��ȣȭ ��Ų ��й�ȣ�� �ٽ� �ѱ��
		mv.setMemberpw(memberpw_enc);
		
		int value = memberService.memberInsert(mv);
		logger.info("value: " + value);  
		
		String path="";
		if (value == 1) { /* �����ϸ� */
			path ="redirect:/";
		} else if (value == 0) { /* �����ϸ� */
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
		//����� ��й�ȣ�� �����´�. 
		
		String path = "";
		if(mv != null) { // ��ü���� ������
		String reservedPw = mv.getMemberpw();
		
		 if(bCryptPasswordEncoder.matches(memberpw, reservedPw)) { //����� ���� ������.
			 //System.out.println("��й�ȣ ��ġ");
			 rttr.addAttribute("midx",mv.getMidx());
			 rttr.addAttribute("memberId",mv.getMemberid());
			 rttr.addAttribute("memberName",mv.getMembername());
			 
			 path ="redirect:/";
		  }else {
			  //rttr.addAttribute("midx","");
			  //rttr.addAttribute("memberId","");
			  //rttr.addAttribute("memberName","");
			  rttr.addFlashAttribute("msg","���̵�/��й�ȣ�� Ȯ�����ּ���");
			  path = "redirect:/member/memberLogin.aws";
		  }
		}else {
			  //rttr.addAttribute("midx","");
			  //rttr.addAttribute("memberId","");
			  //rttr.addAttribute("memberName","");
			  rttr.addFlashAttribute("msg","�ش��ϴ� ���̵� �����ϴ�."); //��ȸ�� �޼ҵ� �޼����� �� ���� ���´�
			path = "redirect:/member/memberLogin.aws";
		}
		//ȸ�������� ���ǿ� ��´�.
		
		return path;
	}
	
	//�α����� �ȵǸ� �ٽ� �α��� �������� ����
	//�α����� �Ǹ� �������� ����
	
	
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
		
		// ��� ���� �ȿ��ٰ� memberSelectAll �޼ҵ带 �����. 
		ArrayList<MemberVo> alist = memberService.memberSelectAll();
		
		model.addAttribute("alist",alist);
		
		return "WEB-INF/member/memberList";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
