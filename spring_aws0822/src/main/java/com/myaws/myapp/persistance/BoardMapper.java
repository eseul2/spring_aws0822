package com.myaws.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.SearchCriteria;

public interface BoardMapper {
	
	/* 마이바티스용 메소드. 서비스에 있는 메소드 이름을 동일하게 해서 헷갈리지 않게 한다. */ 
	public ArrayList<BoardVo> boardSelectAll(HashMap<String,Object> hm);
	public int boardTotalCount(SearchCriteria scri);
	
	public int boardInsert(BoardVo bv);
	
	public int boardOriginbidxUpdate(int bidx);
	
	public BoardVo boardSelectOne(int bidx);
	public int boardViewCntUpdate(int bidx);
	public int boardRecomUpdate(BoardVo bv);
	public int boardDelete(HashMap hm);
	public int boardUpdate(BoardVo bv);
	public int boardReply(BoardVo bv);
	public int boardReplyUpdate(BoardVo bv);
	public int boardReplyInsert(BoardVo bv);

}



