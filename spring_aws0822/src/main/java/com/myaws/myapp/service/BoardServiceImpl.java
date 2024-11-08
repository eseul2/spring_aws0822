package com.myaws.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.SearchCriteria;
import com.myaws.myapp.persistance.BoardMapper;



@Service
public class BoardServiceImpl implements BoardService {

	// BoardMapper 타입의 객체 생성 (MyBatis와 연동)
	private BoardMapper bm;
	
	
	
	@Autowired  // SqlSession 객체를 주입받는 생성자
	public BoardServiceImpl(SqlSession sqlSession) {
		// 주입받은 sqlSession 객체를 이용하여 BoardMapper를 초기화
		this.bm = sqlSession.getMapper(BoardMapper.class); /* 꼭 뒤에 클래스가 붙어야한다. */ 
	}
	
	
	
	// 게시글 목록을 검색 조건에 따라 조회하는 메서드
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		// HashMap 객체를 생성하여 페이징, 검색 타입, 키워드 등의 조건을 추가. ArrayList처럼 담는다.
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("startPageNum",(scri.getPage()-1)* scri.getPerPageNum()); // 시작 페이지 번호
		hm.put("searchType", scri.getSearchType());  // 검색 유형
		hm.put("keyword", scri.getKeyword()); // 검색 키워드
		hm.put("perPageNum", scri.getPerPageNum()); // 페이지당 게시글 수
		
		// BoardMapper를 사용해서 검색 조건에 맞는 게시글 목록 조회
		ArrayList<BoardVo> blist = bm.boardSelectAll(hm);
		return blist;
	}

	
	// 총 게시글 수를 검색 조건에 따라 가져오는 메서드
	@Override
	public int boardTotalCount(SearchCriteria scri) {
		// 검색 조건을 통해 BoardMapper에서 총 게시글 수를 조회		
		int cnt = bm.boardTotalCount(scri);
		return cnt;
	}



	@Override
	@Transactional
	public int boardInsert(BoardVo bv) {
		
		int value = bm.boardInsert(bv);
		int maxBidx = bv.getBidx();
		int value2 = bm.boardOriginbidxUpdate(maxBidx);
		
	
		return value+value2;
	}



	@Override
	public BoardVo boardSelectOne(int bidx) {
		
		BoardVo bv = bm.boardSelectOne(bidx);
		return bv;
	}



	@Override
	public int boardViewCntUpdate(int bidx) {
		int cnt = bm.boardViewCntUpdate(bidx);
		return cnt;
	}



	@Override
	public int boardRecomUpdate(int bidx) {
		
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		
		int cnt = bm.boardRecomUpdate(bv);
		int recom = bv.getRecom();  //값을 다시 꺼내주기
		return recom;
	}



	@Override
	public int boardDelete(int bidx, int midx, String password) {
		
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("bidx", bidx);
		hm.put("midx", midx);
		hm.put("password", password);
		
		
		int cnt = bm.boardDelete(hm);
		return cnt;
	}

}
