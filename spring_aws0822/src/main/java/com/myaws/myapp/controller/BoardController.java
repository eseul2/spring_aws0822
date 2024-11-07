package com.myaws.myapp.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.service.MemberService;
import com.myaws.myapp.util.UploadFileUtiles;

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
	
	@Resource(name="uploadPath") //���ҽ��� ����� �� ���Ӱ��� �� ��ϵ� id �̸����� �ؾ��Ѵ�. �̸��� ���� �ָ� ã�Ƽ� �����ϴ°�
	private String uploadPath;
	
	
	//�Խñ� ����� ��ȸ�ϰ� �˻� ���ǿ� �´� �Խñ��� ȭ�鿡 ǥ���ϴ� �޼���
	// GET ����� �����Ͱ� URL �ڿ� ���� �Ķ���ͷ� ����Ǵ� ���, 
	// POST ����� �����Ͱ� ��û ������ ���ԵǾ� URL�� ������� �ʴ� ���.
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
	
	
	// �۾��� ȭ�� �����ֱ� ���
	@RequestMapping(value= "boardWrite.aws")
	public String boardWrite() {
		
		String path = "WEB-INF/board/boardWrite";
		return path;
	}
	
	
	//�Խ��ǿ��� �� �ۼ��� ���� ���ε� ����� ó��
	@RequestMapping(value= "boardWriteAction.aws")
	public String boardWriteAction(
		BoardVo bv, // �Խñ� ������ ��� �ִ� BoardVo ��ü�� �Ű������� ����
		@RequestParam("filename") MultipartFile filename, // ���ε�� ������ �ޱ� ���� MultipartFile ��ü
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // ���ܿ� ���� �ϴ°�
		MultipartFile file = filename; //����� ���� �̸� ������ 
		String uploadedFileName=""; // ������ ���ε�� �� ����� ���ϸ��� ������ ����
		
		if(! file.getOriginalFilename().equals("")) { // �ش� ������ �����Ѵٸ�
			 // ������ ������ �����ϰ� ����� ���� �̸��� ��ȯ����
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = getUserIp(request);
		
		bv.setUploadedFilename(uploadedFileName);  // vo�� ��Ƽ� �������� 
		bv.setMidx(midx_int);
		bv.setIp(ip);
		
		int value = boardService.boardInsert(bv);
		
		String path="";
		if(value == 2) { //�����ϸ�
			path = "redirect:/board/boardList.aws";
		}else {
			rttr.addFlashAttribute("msg","�Է��� �߸��Ǿ����ϴ�.");
			path = "redirect:/board/boardWrite.aws"; // �����̷�Ʈ�� �ѱ�°��� ���ΰ� �ƴ϶� �ܺα� ������ ��ü��θ� �Ѱ���� �Ѵ�(aws)
		}
		return path;
	}
	

	
	// �� ���� �����ֱ� ���
	@RequestMapping(value= "boardContents.aws")
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {
		
		BoardVo bv = boardService.boardSelectOne(bidx);
		
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardContents";		
		return path;
	}
	
	
	
	
	
	// ���� ip �����ϴ� �޼ҵ� 
	public String getUserIp(HttpServletRequest request) throws Exception {
			
		String ip = null;
	     //  HttpServletRequest request = 
	     // ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		ip = request.getHeader("X-Forwarded-For");
	        
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getHeader("Proxy-Client-IP"); 
	    	} 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	         ip = request.getHeader("WL-Proxy-Client-IP"); 
	         } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    	ip = request.getHeader("HTTP_CLIENT_IP"); 
	        } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    	ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	        }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    	ip = request.getHeader("X-Real-IP"); 
	        }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    	ip = request.getHeader("X-RealIP"); 
	    	}
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getHeader("REMOTE_ADDR");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	            ip = request.getRemoteAddr(); 
	        }
	        
	        
	        if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
	        	InetAddress address = InetAddress.getLocalHost();
	        	ip = address.getHostAddress();
	        }
			return ip;
		}
		
		
	
	

}
