package com.myaws.myapp.service;

import com.myaws.myapp.domain.MemberVo;

public interface MemberService {
	
	public int memberInsert(MemberVo mv);

	public int memberIdCheck(String memberId);
	
	public MemberVo memberLoginCheck(String memberId);

}





