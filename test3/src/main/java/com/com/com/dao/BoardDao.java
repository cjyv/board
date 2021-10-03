package com.com.com.dao;

import java.util.List;
import java.util.Map;

public interface BoardDao {

	public List<Map<String, Object>> list(Map<String, Object> map);

	public int insert(Map<String, Object> map);

	public Map<String, Object> detail(int seq);

	public int delete(List<Integer> list);

	public int count(Map<String, Object> map);

	public int update(Map<String, Object> map);

	public int maxSeq();

	public int fileInsert(Map<String, Object> fileMap);

	public List<Map<String, Object>> files(int seq);


	public int deletfiles(List<Integer> list);

	public int count2(int seq);



	
		
	

}
