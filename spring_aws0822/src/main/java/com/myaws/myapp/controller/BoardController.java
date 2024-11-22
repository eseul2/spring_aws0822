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
	
	// BoardService 타입의 객체를 자동으로 주입. 주입 실패 시에도 에러가 발생하지 않고 null로 설정됨
	@Autowired(required = false) 
	private BoardService boardService;
	
	// PageMaker 객체를 자동으로 주입하여 페이지 정보를 설정
	@Autowired(required = false) 
	private PageMaker pm;
	
	@Resource(name="uploadPath") //리소스는 사용할 때 네임값을 빈에 등록된 id 이름으로 해야한다. 이름이 같은 애를 찾아서 주입하는것
	private String uploadPath;
	
	@Autowired(required = false) 
	private UserIp userIp;
	
	
	//게시글 목록을 조회하고 검색 조건에 맞는 게시글을 화면에 표시하는 메서드
	// GET 방식은 데이터가 URL 뒤에 쿼리 파라미터로 노출되는 방식, 
	// POST 방식은 데이터가 요청 본문에 포함되어 URL에 노출되지 않는 방식.
	@RequestMapping(value="boardList.aws", method=RequestMethod.GET)
	public String boardList(SearchCriteria scri, Model model) {
		
		// 총 게시글 수를 검색 조건(scri)을 기준으로 조회
		int cnt = boardService.boardTotalCount(scri);
		// PageMaker 객체에 검색 조건과 총 게시글 수를 설정하여 페이지 네비게이션을 준비
		pm.setScri(scri);
		pm.setTotalCount(cnt);
			
		// boardService에서 주어진 검색 조건(scri)을 기반으로 게시글 목록을 조회
		ArrayList<BoardVo> blist = boardService.boardSelectAll(scri);
		
		// 조회된 게시글 목록을 "blist"라는 이름으로 모델에 추가하여 JSP 뷰 페이지로 전달
		model.addAttribute("blist",blist);
		// PageMaker 객체도 모델에 추가하여 JSP 뷰에서 페이지 정보도 접근할 수 있도록 설정
		model.addAttribute("pm",pm);
			
		//경로에 해당하는 JSP 뷰 페이지를 반환
		return "WEB-INF/board/boardList";
		}
	
	
	// 글쓰기 화면 보여주기 기능
	@RequestMapping(value= "boardWrite.aws", method=RequestMethod.GET)
	public String boardWrite() {
		
		String path = "WEB-INF/board/boardWrite";
		return path;
	}
	
	// 메인페이지
	@RequestMapping(value= "test.aws", method=RequestMethod.GET)
	public String test() {
		
		String path = "WEB-INF/board/test";
		return path;
	}
	
	// 로그인페이지
	@RequestMapping(value= "login.aws", method=RequestMethod.GET)
	public String login() {
		
		String path = "WEB-INF/board/login";
		return path;
	}
	
	
	//게시판에서 글 작성과 파일 업로드 기능을 처리
	@RequestMapping(value= "boardWriteAction.aws", method=RequestMethod.POST)
	public String boardWriteAction(
		BoardVo bv, // 게시글 정보를 담고 있는 BoardVo 객체를 매개변수로 받음
		@RequestParam("attachfile") MultipartFile attachfile, // 업로드된 파일을 받기 위한 MultipartFile 객체
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // 윗단에 보고를 하는것
		MultipartFile file = attachfile; //저장된 파일 이름 꺼내기 
		String uploadedFileName=""; // 파일이 업로드된 후 저장된 파일명을 저장할 변수
		
		if(! file.getOriginalFilename().equals("")) { // 해당 파일이 존재한다면
			 // 파일을 서버에 저장하고 저장된 파일 이름을 반환받음
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = userIp.getUserIp(request);
		
		bv.setUploadedFilename(uploadedFileName);  // vo에 담아서 가져가기 
		bv.setMidx(midx_int);
		bv.setIp(ip);
		
		int value = boardService.boardInsert(bv);
		
		String path="";
		if(value == 2) { //성공하면
			path = "redirect:/board/boardList.aws";
		}else {
			rttr.addFlashAttribute("msg","입력이 잘못되었습니다.");
			path = "redirect:/board/boardWrite.aws"; // 리다이랙트로 넘기는것은 내부가 아니라 외부기 때문에 전체경로를 넘겨줘야 한다(aws)
		}
		return path;
	}
	
	
	
	@ResponseBody
	@PostMapping("/boardWriteActionReact.aws")
	public JSONObject boardWriteActionReact(@RequestBody BoardVo bv) {
		
		JSONObject js = new JSONObject();
		int value = boardService.boardInsert(bv);
		
		if(value==2) { // 성공
			js.put("result", "success");
		}else { // 실패
			js.put("result","fail");
		}
		return js;
	}
	

	
	// 글 내용 보여주기 기능
	@RequestMapping(value= "boardContents.aws", method=RequestMethod.GET)
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {
		
		boardService.boardViewCntUpdate(bidx);
		BoardVo bv = boardService.boardSelectOne(bidx);
		
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardContents";		
		return path;
	}
	
	
	//이미지 같은것들이 다 바이트니까 바이트로 값을 받는다. 가상경로를 하나  만들어서 파일네임을 넘겨주고, 다운이라는 것도 함께 넘겨준다. 
	// 만약 0이라면 넘기지 않는다. 
	//미디어,http는 스프링으로 임포트하기
	// 이 메서드를 통해서 화면에 보여질지, 다운로드를 할 것인지 할 수 있는 기능의 메서드.
	@RequestMapping(value="/displayFile.aws", method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(
			@RequestParam("fileName") String fileName,
			@RequestParam(value="down", defaultValue="0") int down   // 화면에 보여줄 것인가. 다운로드 하게 할 것인가?
			) {
		
		ResponseEntity<byte[]> entity = null;  //객체를 담는 애인데 byte계열을 다 담는다. 
		InputStream in = null;	// 데이터의 수로와 같은데, 처음에 읽어들이는 시작점이 InputStream
		
		try{
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1); // 확장자가 무엇인지 물어본다.
			MediaType mType = MediaUtils.getMediaType(formatName); // 위에서 확장자를 꺼내서 미디어 유틸이라는 곳에 넣는다. 왜냐면 여기서 확장자가 무엇인지 타입을 알기 위해서 (png,jpg)
			
			HttpHeaders headers = new HttpHeaders();		
			 
			 // 파일 경로를 이용해 파일을 읽기 위해 InputStream 객체 생성
			in = new FileInputStream(uploadPath+fileName);  // 위에서 초기화한 애를 객체생성시켜서 해당되는 파일 위치를 읽어들인다.
			
			
			if(mType != null){ // 값이 해당되면 
				if (down==1) { // 다운을 받으라는 얘기
					fileName = fileName.substring(fileName.indexOf("_")+1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition", "attachment; filename=\""+
							new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");	
				}else { // 0이라면 헤더에다가 해당되는 타입을 담아서 이미지를 뿌려준다. 
					headers.setContentType(mType);	
				}
			}else{ // png 같은 것들이 아니면 여기로 이동. 
				fileName = fileName.substring(fileName.indexOf("_")+1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
						new String(fileName.getBytes("UTF-8"),"ISO-8859-1")+"\"");				
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), //배열에 in정보와 헤더, 상태값들을 생성자를 통해서 모두 담아서 리턴할것이다.
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
	
	// 추천하기 기능 구현
	@ResponseBody  //Java 객체를 JSON 형태로 변환해 클라이언트에게 보내는 역할
	@RequestMapping(value="boardRecom.aws", method=RequestMethod.GET)
	public JSONObject boardRecom(@RequestParam("bidx") int bidx) {
		
		int value = boardService.boardRecomUpdate(bidx);
		
		JSONObject js = new JSONObject();
		js.put("recom", value);  // value값을 담아서 json에게 넘겨주기 
		
		return js;
	}
	
	
	
	@RequestMapping(value="boardDelete.aws", method=RequestMethod.GET)
	public String boardDelet(@RequestParam("bidx") int bidx,Model model){
		
		model.addAttribute("bidx",bidx);
		String path="WEB-INF/board/boardDelete";
		return path;
	}
	
	
	
	// 삭제하기 기능
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
	            rttr.addFlashAttribute("msg", "비밀번호가 틀렸습니다.");
	            path = "redirect:/board/boardDelete.aws?bidx="+bidx;
	         }
	      }else {
	         rttr.addFlashAttribute("msg", "글을 쓴 회원이 아닙니다.");
	         path = "redirect:/board/boardDelete.aws?bidx="+bidx;
	      }

	      return path;  // .jsp는 WEB-INF/spring/appServlet/servlet-context.xml > 에서 붙어짐
	}
	
	
	
	// 수정하기 화면 
	@RequestMapping(value="boardModify.aws", method=RequestMethod.GET)
	public String boardModify(@RequestParam("bidx") int bidx,Model model){
		
		// 게시물 내용을 조회하는 메서드
		BoardVo bv = boardService.boardSelectOne(bidx);
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardModify";
		return path;
	}
	
	
	
	
	//게시판에서 글 수정과 파일 업로드 기능을 처리
	@RequestMapping(value= "boardModifyAction.aws", method=RequestMethod.POST)
	public String boardModifyAction(
		BoardVo bv, // 게시글 정보를 담고 있는 BoardVo 객체를 매개변수로 받음
		@RequestParam("attachfile") MultipartFile attachfile, // 업로드된 파일을 받기 위한 MultipartFile 객체
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // 윗단에 보고를 하는것
		
		MultipartFile file = attachfile; //저장된 파일 이름 꺼내기 
		String uploadedFileName=""; // 파일이 업로드된 후 저장된 파일명을 저장할 변수
			
		if(! file.getOriginalFilename().equals("")) { // 해당 파일이 존재한다면
			 // 파일을 서버에 저장하고 저장된 파일 이름을 반환받음
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = userIp.getUserIp(request);
			
		bv.setUploadedFilename(uploadedFileName);  // vo에 담아서 가져가기 
		bv.setIp(ip);
		
		//수정 처리
		int value = boardService.boardUpdate(bv); // 서비스에서 만든 메서드 호출하기
			
		String path="";
		//게시글 작성자와 현재 로그인한 사용자의 midx를 비교하는 부분
		if(bv.getMidx() == midx_int) {
	        if(value == 1) {
	           path = "redirect:/board/boardContents.aws?bidx=" + bv.getBidx();
	        }else {
	             rttr.addFlashAttribute("msg", "비밀번호가 틀렸습니다.");
	             path = "redirect:/board/boardModify.aws?bidx=" + bv.getBidx();
	         }
	     }else {
	         rttr.addFlashAttribute("msg", "자신의 게시글만 수정 할 수 있습니다.");
	         path = "redirect:/board/boardModify.aws?bidx=" + bv.getBidx();
	     }
	            
	     return path; 
}		    
	
	
	
	// 답변하기 화면
	@RequestMapping(value="boardReply.aws" , method=RequestMethod.GET)
	public String boardReply(@RequestParam("bidx") int bidx,Model model){
		
		// 게시물 내용을 조회하는 메서드
		BoardVo bv = boardService.boardSelectOne(bidx);
			
		model.addAttribute("bv", bv);
		
		String path="WEB-INF/board/boardReply";
		return path;
	}

	
	// 글 답변하기 기능
	@RequestMapping(value= "boardReplyAction.aws", method=RequestMethod.POST)
	public String boardReplyAction(
		BoardVo bv, // 게시글 정보를 담고 있는 BoardVo 객체를 매개변수로 받음
		@RequestParam("attachfile") MultipartFile attachfile, // 업로드된 파일을 받기 위한 MultipartFile 객체
		HttpServletRequest request,
		RedirectAttributes rttr
		) throws Exception { // 윗단에 보고를 하는것
			
		
		MultipartFile file = attachfile; //저장된 파일 이름 꺼내기 
		String uploadedFileName=""; // 파일이 업로드된 후 저장된 파일명을 저장할 변수
				
		if(! file.getOriginalFilename().equals("")) { // 해당 파일이 존재한다면
			 // 파일을 서버에 저장하고 저장된 파일 이름을 반환받음
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
			
		String midx = request.getSession().getAttribute("midx").toString();
		int midx_int = Integer.parseInt(midx); 
		String ip = userIp.getUserIp(request);
				
		bv.setUploadedFilename(uploadedFileName);  // vo에 담아서 가져가기 
		bv.setMidx(midx_int);
		bv.setIp(ip);
		
		int maxBidx = 0; // 최신 게시물 
		maxBidx = boardService.boardReply(bv);
		
		String path="";
		
		if (maxBidx != 0) {  //성공 했을 경우 
			path = "redirect:/board/boardContents.aws?bidx="+ maxBidx;
		    } else {
		    rttr.addFlashAttribute("msg", "답변이 등록되지 않았습니다.");
		    path = "redirect:/board/boardReply.aws?bidx=" + bv.getBidx();	
		}
		    return path;
	}		    
	
	


}
