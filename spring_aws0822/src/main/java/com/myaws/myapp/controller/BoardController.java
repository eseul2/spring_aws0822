package com.myaws.myapp.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.service.MemberService;

@Controller
@RequestMapping(value="/board/")
public class BoardController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// BoardService Ÿ���� ��ü�� �ڵ����� ����. ���� ���� �ÿ��� ������ �߻����� �ʰ� null�� ������
	@Autowired(required = false) 
	private BoardService boardService;
	
	// PageMaker ��ü�� �ڵ����� �����Ͽ� ������ ������ ����
	@Autowired(required = false) 
	private PageMaker pm;
	
	
	//�Խñ� ����� ��ȸ�ϰ� �˻� ���ǿ� �´� �Խñ��� ȭ�鿡 ǥ���ϴ� �޼���
	@RequestMapping(value="boardList.aws", method=RequestMethod.GET)
	public String boardList(SearchCriteria scri, Model model) {
		
		// �� �Խñ� ���� �˻� ����(scri)�� �������� ��ȸ
		int cnt = boardService.boardTotalCount(scri);
		// PageMaker ��ü�� �˻� ���ǰ� �� �Խñ� ���� �����Ͽ� ������ �׺���̼��� �غ�
		pm.setScri(scri);
		pm.setTotalCount(cnt);
			
		// boardService���� �־��� �˻� ����(scri)�� ������� �Խñ� ����� ��ȸ
		ArrayList<BoardVo> blist = boardService.boardSelectAll(scri);
		
		// ��ȸ�� �Խñ� ����� "blist"��� �̸����� �𵨿� �߰��Ͽ� JSP �� �������� ����
		model.addAttribute("blist",blist);
		// PageMaker ��ü�� �𵨿� �߰��Ͽ� JSP �信�� ������ ������ ������ �� �ֵ��� ����
		model.addAttribute("pm",pm);
			
		//��ο� �ش��ϴ� JSP �� �������� ��ȯ
		return "WEB-INF/board/boardList";
		}

}
