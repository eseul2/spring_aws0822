package com.myaws.myapp.controller;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.service.CommentService;

@RestController
@RequestMapping(value="/comment")
public class CommentController {
	
	
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value="/{bidx}/commentList.aws")
	public JSONObject commentList(
			@PathVariable("bidx") int bidx //이걸 통해서 그 bidx값을 꺼낼 수 있다.		
			){
		
		JSONObject js = new JSONObject();
		
		ArrayList<CommentVo> clist = commentService.commentSelectAll(bidx);
		js.put("clist", clist);   //제이슨에 담아서 화면에 가지고 갈거에용 
		
		return js;  // 객체가 리턴된다.
	}

}
