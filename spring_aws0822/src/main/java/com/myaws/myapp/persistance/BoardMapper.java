package com.myaws.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myaws.myapp.domain.BoardVo;
import com.myaws.myapp.domain.SearchCriteria;

public interface BoardMapper {
	
	/* ���̹�Ƽ���� �޼ҵ�. ���񽺿� �ִ� �޼ҵ� �̸��� �����ϰ� �ؼ� �򰥸��� �ʰ� �Ѵ�. */ 
	public ArrayList<BoardVo> boardSelectAll(HashMap<String,Object> hm);
	public int boardTotalCount(SearchCriteria scri);

}



