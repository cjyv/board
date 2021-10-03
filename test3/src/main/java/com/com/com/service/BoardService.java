package com.com.com.service;

import java.util.List;
import java.util.Map;

public interface BoardService {

	List<Map<String, Object>> list(Map<String, Object> map);

	int insert(Map<String, Object> map);

	Map<String, Object> detail(int seq);

	int delete(List<Integer> list);

	Map<String, Object> pagination(Map<String, Object> map);

	int update(Map<String, Object> map);

	int maxSeq();

	int fileInsert(Map<String, Object> fileMap);

	List<Map<String, Object>> files(int seq);

	int filedelete(List<Integer> list);

	int count(int seq);


	
}
