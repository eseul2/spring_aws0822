package com.myaws.myapp.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myaws.myapp.domain.MemberVo;
import com.myaws.myapp.persistance.MemberMapper;


@Service
public class MemberServiceImpl implements MemberService{

	private MemberMapper mm;
	
	
	@Autowired /* 주입시킨다 */  
	public MemberServiceImpl(SqlSession sqlSession) {
		this.mm = sqlSession.getMapper(MemberMapper.class); /* 꼭 뒤에 클래스가 붙어야한다. */ 
	}
	
	
	@Override 
	public int memberInsert(MemberVo mv) {
		int value = mm.memberInsert(mv);
		return value;
	}

}


