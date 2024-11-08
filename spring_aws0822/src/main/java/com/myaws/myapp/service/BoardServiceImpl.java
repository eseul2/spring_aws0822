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

	// BoardMapper Ÿ���� ��ü ���� (MyBatis�� ����)
	private BoardMapper bm;
	
	
	
	@Autowired  // SqlSession ��ü�� ���Թ޴� ������
	public BoardServiceImpl(SqlSession sqlSession) {
		// ���Թ��� sqlSession ��ü�� �̿��Ͽ� BoardMapper�� �ʱ�ȭ
		this.bm = sqlSession.getMapper(BoardMapper.class); /* �� �ڿ� Ŭ������ �پ���Ѵ�. */ 
	}
	
	
	
	// �Խñ� ����� �˻� ���ǿ� ���� ��ȸ�ϴ� �޼���
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		// HashMap ��ü�� �����Ͽ� ����¡, �˻� Ÿ��, Ű���� ���� ������ �߰�. ArrayListó�� ��´�.
		HashMap<String,Object> hm = new HashMap<String,Object>();
		hm.put("startPageNum",(scri.getPage()-1)* scri.getPerPageNum()); // ���� ������ ��ȣ
		hm.put("searchType", scri.getSearchType());  // �˻� ����
		hm.put("keyword", scri.getKeyword()); // �˻� Ű����
		hm.put("perPageNum", scri.getPerPageNum()); // �������� �Խñ� ��
		
		// BoardMapper�� ����ؼ� �˻� ���ǿ� �´� �Խñ� ��� ��ȸ
		ArrayList<BoardVo> blist = bm.boardSelectAll(hm);
		return blist;
	}

	
	// �� �Խñ� ���� �˻� ���ǿ� ���� �������� �޼���
	@Override
	public int boardTotalCount(SearchCriteria scri) {
		// �˻� ������ ���� BoardMapper���� �� �Խñ� ���� ��ȸ		
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
		int recom = bv.getRecom();  //���� �ٽ� �����ֱ�
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
