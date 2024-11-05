package com.myaws.myapp.persistance;

import java.util.ArrayList;

import com.myaws.myapp.domain.MemberVo;

public interface MemberMapper {
	
	/* 마이바티스용 메소드. 서비스에 있는 메소드 이름을 동일하게 해서 헷갈리지 않게 한다. */ 
	public int memberInsert(MemberVo mv);
	public int memberIdCheck(String memberId);
	public MemberVo memberLoginCheck(String memberId);
	public ArrayList<MemberVo> memberSelectAll();

}





