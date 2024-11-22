package com.myaws.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.domain.PageMaker;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.service.BoardService;
import com.myaws.myapp.service.MemberService;
import com.myaws.myapp.util.MediaUtils;
import com.myaws.myapp.util.UploadFileUtiles;
import com.myaws.myapp.util.UserIp;


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
	
	@Autowired(required = false) 
	private UserIp userIp;
	
	
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
	@RequestMapping(value= "boardWrite.aws", method=RequestMethod.GET)
	public String boardWrite() {
		
		String path = "WEB-INF/board/boardWrite";
		return path;
	}
	
	// ����������
	@RequestMapping(value= "test.aws", method=RequestMethod.GET)
	public String test() {
		
		String path = "WEB-INF/board/test";
		return path;
	}
	
	// �α���������
	@RequestMapping(value= "login.aws", method=RequestMethod.GET)
	public String login() {
		
		String path = "WEB-INF/board/login";
		return path;
	}
	
	
	//�Խ��ǿ��� �� �ۼ��� ���� ���ε� ����� ó��
	@RequestMapping(value= "boardWriteAction.aws", method=RequestMethod.POST)
	public String boardWriteAction(
		BoardVo bv, // �Խñ� ������ ��� �ִ� BoardVo ��ü�� �Ű������� ����
		@RequestParam("attachfile") MultipartFile attachfile, // ���ε�� ������ �ޱ� ���� MultipartFile ��ü
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // ���ܿ� ���� �ϴ°�
		MultipartFile file = attachfile; //����� ���� �̸� ������ 
		String uploadedFileName=""; // ������ ���ε�� �� ����� ���ϸ��� ������ ����
		
		if(! file.getOriginalFilename().equals("")) { // �ش� ������ �����Ѵٸ�
			 // ������ ������ �����ϰ� ����� ���� �̸��� ��ȯ����
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = userIp.getUserIp(request);
		
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
	
	
	
	@ResponseBody
	@PostMapping("/boardWriteActionReact.aws")
	public JSONObject boardWriteActionReact(@RequestBody BoardVo bv) {
		
		JSONObject js = new JSONObject();
		int value = boardService.boardInsert(bv);
		
		if(value==2) { // ����
			js.put("result", "success");
		}else { // ����
			js.put("result","fail");
		}
		return js;
	}
	

	
	// �� ���� �����ֱ� ���
	@RequestMapping(value= "boardContents.aws", method=RequestMethod.GET)
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {
		
		boardService.boardViewCntUpdate(bidx);
		BoardVo bv = boardService.boardSelectOne(bidx);
		
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardContents";		
		return path;
	}
	
	
	//�̹��� �����͵��� �� ����Ʈ�ϱ� ����Ʈ�� ���� �޴´�. �����θ� �ϳ�  ���� ���ϳ����� �Ѱ��ְ�, �ٿ��̶�� �͵� �Բ� �Ѱ��ش�. 
	// ���� 0�̶�� �ѱ��� �ʴ´�. 
	//�̵��,http�� ���������� ����Ʈ�ϱ�
	// �� �޼��带 ���ؼ� ȭ�鿡 ��������, �ٿ�ε带 �� ������ �� �� �ִ� ����� �޼���.
	@RequestMapping(value="/displayFile.aws", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(
			@RequestParam("fileName") String fileName,
			@RequestParam(value="down", defaultValue="0") int down   // ȭ�鿡 ������ ���ΰ�. �ٿ�ε� �ϰ� �� ���ΰ�?
			) {
		
		ResponseEntity<byte[]> entity = null;  //��ü�� ��� ���ε� byte�迭�� �� ��´�. 
		InputStream in = null;	// �������� ���ο� ������, ó���� �о���̴� �������� InputStream
		
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1); // Ȯ���ڰ� �������� �����.
			MediaType mType = MediaUtils.getMediaType(formatName); // ������ Ȯ���ڸ� ������ �̵�� ��ƿ�̶�� ���� �ִ´�. �ֳĸ� ���⼭ Ȯ���ڰ� �������� Ÿ���� �˱� ���ؼ� (png,jpg)
			
			HttpHeaders headers = new HttpHeaders();		
			 
			 // ���� ��θ� �̿��� ������ �б� ���� InputStream ��ü ����
			in = new FileInputStream(uploadPath+fileName);  // ������ �ʱ�ȭ�� �ָ� ��ü�������Ѽ� �ش�Ǵ� ���� ��ġ�� �о���δ�.
			
			
			if(mType != null){ // ���� �ش�Ǹ� 
				if (down==1) { // �ٿ��� ������� ���
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
				}else { // 0�̶�� ������ٰ� �ش�Ǵ� Ÿ���� ��Ƽ� �̹����� �ѷ��ش�. 
					headers.setContentType(mType);	
				}
			}else{ // png ���� �͵��� �ƴϸ� ����� �̵�. 
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), //�迭�� in������ ���, ���°����� �����ڸ� ���ؼ� ��� ��Ƽ� �����Ұ��̴�.
					headers,
					HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	// ��õ�ϱ� ��� ����
	@ResponseBody  //Java ��ü�� JSON ���·� ��ȯ�� Ŭ���̾�Ʈ���� ������ ����
	@RequestMapping(value="boardRecom.aws", method=RequestMethod.GET)
	public JSONObject boardRecom(@RequestParam("bidx") int bidx) {
		
		int value = boardService.boardRecomUpdate(bidx);
		
		JSONObject js = new JSONObject();
		js.put("recom", value);  // value���� ��Ƽ� json���� �Ѱ��ֱ� 
		
		return js;
	}
	
	
	
	@RequestMapping(value="boardDelete.aws", method=RequestMethod.GET)
	public String boardDelet(@RequestParam("bidx") int bidx,Model model){
		
		model.addAttribute("bidx",bidx);
		String path="WEB-INF/board/boardDelete";
		return path;
	}
	
	
	
	// �����ϱ� ���
	@RequestMapping(value="boardDeleteAction.aws", method=RequestMethod.POST)
	public String boardDeleteAction(
			@RequestParam("bidx") int bidx, 
			@RequestParam("password") String password,
			HttpSession session,
			RedirectAttributes rttr
			){

		BoardVo bv = boardService.boardSelectOne(bidx);
	      int midx = Integer.parseInt(session.getAttribute("midx").toString());
	      int value = boardService.boardDelete(bidx,midx,password);
	      
	      String path = "";
	      if(bv.getMidx()==midx) {
	         if(value==1) {
	            path = "redirect:/board/boardList.aws";
	         }else {
	            rttr.addFlashAttribute("msg", "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
	            path = "redirect:/board/boardDelete.aws?bidx="+bidx;
	         }
	      }else {
	         rttr.addFlashAttribute("msg", "���� �� ȸ���� �ƴմϴ�.");
	         path = "redirect:/board/boardDelete.aws?bidx="+bidx;
	      }

	      return path;  // .jsp�� WEB-INF/spring/appServlet/servlet-context.xml > ���� �پ���
	}
	
	
	
	// �����ϱ� ȭ�� 
	@RequestMapping(value="boardModify.aws", method=RequestMethod.GET)
	public String boardModify(@RequestParam("bidx") int bidx,Model model){
		
		// �Խù� ������ ��ȸ�ϴ� �޼���
		BoardVo bv = boardService.boardSelectOne(bidx);
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardModify";
		return path;
	}
	
	
	
	
	//�Խ��ǿ��� �� ������ ���� ���ε� ����� ó��
	@RequestMapping(value= "boardModifyAction.aws", method=RequestMethod.POST)
	public String boardModifyAction(
		BoardVo bv, // �Խñ� ������ ��� �ִ� BoardVo ��ü�� �Ű������� ����
		@RequestParam("attachfile") MultipartFile attachfile, // ���ε�� ������ �ޱ� ���� MultipartFile ��ü
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // ���ܿ� ���� �ϴ°�
		
		MultipartFile file = attachfile; //����� ���� �̸� ������ 
		String uploadedFileName=""; // ������ ���ε�� �� ����� ���ϸ��� ������ ����
			
		if(! file.getOriginalFilename().equals("")) { // �ش� ������ �����Ѵٸ�
			 // ������ ������ �����ϰ� ����� ���� �̸��� ��ȯ����
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = userIp.getUserIp(request);
			
		bv.setUploadedFilename(uploadedFileName);  // vo�� ��Ƽ� �������� 
		bv.setIp(ip);
		
		//���� ó��
		int value = boardService.boardUpdate(bv); // ���񽺿��� ���� �޼��� ȣ���ϱ�
			
		String path="";
		//�Խñ� �ۼ��ڿ� ���� �α����� ������� midx�� ���ϴ� �κ�
		if(bv.getMidx() == midx_int) {
	        if(value == 1) {
	           path = "redirect:/board/boardContents.aws?bidx=" + bv.getBidx();
	        }else {
	             rttr.addFlashAttribute("msg", "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
	             path = "redirect:/board/boardModify.aws?bidx=" + bv.getBidx();
	         }
	     }else {
	         rttr.addFlashAttribute("msg", "�ڽ��� �Խñ۸� ���� �� �� �ֽ��ϴ�.");
	         path = "redirect:/board/boardModify.aws?bidx=" + bv.getBidx();
	     }
	            
	     return path; 
}		    
	
	
	
	// �亯�ϱ� ȭ��
	@RequestMapping(value="boardReply.aws" , method=RequestMethod.GET)
	public String boardReply(@RequestParam("bidx") int bidx,Model model){
		
		// �Խù� ������ ��ȸ�ϴ� �޼���
		BoardVo bv = boardService.boardSelectOne(bidx);
			
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardReply";
		return path;
	}

	
	// �� �亯�ϱ� ���
	@RequestMapping(value= "boardReplyAction.aws", method=RequestMethod.POST)
	public String boardReplyAction(
		BoardVo bv, // �Խñ� ������ ��� �ִ� BoardVo ��ü�� �Ű������� ����
		@RequestParam("attachfile") MultipartFile attachfile, // ���ε�� ������ �ޱ� ���� MultipartFile ��ü
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // ���ܿ� ���� �ϴ°�
			
		
		MultipartFile file = attachfile; //����� ���� �̸� ������ 
		String uploadedFileName=""; // ������ ���ε�� �� ����� ���ϸ��� ������ ����
				
		if(! file.getOriginalFilename().equals("")) { // �ش� ������ �����Ѵٸ�
			 // ������ ������ �����ϰ� ����� ���� �̸��� ��ȯ����
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
			
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = userIp.getUserIp(request);
				
		bv.setUploadedFilename(uploadedFileName);  // vo�� ��Ƽ� �������� 
		bv.setMidx(midx_int);
		bv.setIp(ip);
		
		int maxBidx = 0; // �ֽ� �Խù� 
		maxBidx = boardService.boardReply(bv);
		
		String path="";
		
		if (maxBidx != 0) {  //���� ���� ��� 
			path = "redirect:/board/boardContents.aws?bidx="+ maxBidx;
		    } else {
		    rttr.addFlashAttribute("msg", "�亯�� ��ϵ��� �ʾҽ��ϴ�.");
		    path = "redirect:/board/boardReply.aws?bidx=" + bv.getBidx();	
		}
		    return path;
	}		    
	
	


}
