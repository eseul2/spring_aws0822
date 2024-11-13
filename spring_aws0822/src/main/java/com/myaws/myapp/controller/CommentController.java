package com.myaws.myapp.controller;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.service.CommentService;
import com.myaws.myapp.util.UserIp;

@RestController
@RequestMapping(value="/comment")
public class CommentController {
	
	
	
	@Autowired
	CommentService commentService;
	
	@Autowired(required = false) 
	private UserIp userIp;
	
	
	
	
	@RequestMapping(value="/{bidx}/{block}/commentList.aws")
	public JSONObject commentList(
			@PathVariable("bidx") int bidx, //이걸 통해서 그 bidx값을 꺼낼 수 있다.	
			@PathVariable("block") int block
			){
		
		String moreView="";
		int nextBlock=0; // 다음블럭
		int cnt = commentService.commentTotalCnt(bidx);
		
		if(cnt > block*15) {
			moreView = "Y";
			nextBlock = block+1; // 하나씩 증가해주기	
		}else {
			moreView = "N";
			nextBlock = block;
		}
	
		ArrayList<CommentVo> clist = commentService.commentSelectAll(bidx,block);
		
		JSONObject js = new JSONObject();
		js.put("clist", clist);   //제이슨에 담아서 화면에 가지고 갈거에용 
		js.put("moreView", moreView);
		js.put("nextBlock", nextBlock); 
		
		return js;  // 객체가 리턴된다.
	}
	
	
	@RequestMapping(value="/commentWriteAction.aws", method=RequestMethod.POST)
	public JSONObject commentWriteAction(
			CommentVo cv, // cv안에 midx,bidx값이 다 들어있다. 
			HttpServletRequest request // 클라이언트 요청 정보를 담고 있는 객체
			) throws Exception{
		
		JSONObject js = new JSONObject();
		
		String cip = userIp.getUserIp(request);// getUserIp() 메서드를 통해 클라이언트의 IP 주소를 가져옴
		cv.setCip(cip);  // cv에 IP를 설정
		
		int value = commentService.commentInsert(cv);
		js.put("value", value);
		
		return js;
	}
	
	
	@RequestMapping(value="/{cidx}/commentDeleteAction.aws", method=RequestMethod.GET)
	public JSONObject commentDeleteAction(
			CommentVo cv, 
			@PathVariable("cidx") int cidx, //이걸 통해서 그 bidx값을 꺼낼 수 있다.
			HttpServletRequest request
			) throws Exception{
		
		JSONObject js = new JSONObject();
		
		int midx = Integer.parseInt(request.getSession().getAttribute("midx").toString());
		cv.setMidx(midx);
		cv.setCidx(cidx);
		cv.setCip(userIp.getUserIp(request));
		
		int value = commentService.commentDelete(cv);
		
		js.put("value", value);
	    return js;
	}
	

	

}
