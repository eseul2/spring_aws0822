package com.myaws.myapp.service;

import java.util.ArrayList;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.CommentVo;
import com.myaws.myapp.persistance.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentMapper cm;
	
	@Autowired
	public CommentServiceImpl(SqlSession sqlSession) {
		this.cm = sqlSession.getMapper(CommentMapper.class);
	}
	
	
	@Override
	public ArrayList<CommentVo> commentSelectAll(int bidx,int block) {
				
		block= block*15;
		ArrayList<CommentVo> clist =  cm.commentSelectAll(bidx,block);		
		return clist;
	}

	
	
	
	@Override
	public int commentInsert(CommentVo cv) {
	
		int value = cm.commentInsert(cv);

		return value;
	}


	@Override
	public int commentDelete(CommentVo cv) {
		
		int value = cm.commentDelete(cv);
		return value;
	}


	@Override
	public int commentTotalCnt(int bidx) {
		int cnt = cm.commentTotalCnt(bidx);
		return cnt;
	}
	
	
	
	
}