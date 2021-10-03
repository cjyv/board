package com.com.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.com.com.dao.BoardDao;
import com.com.com.service.BoardService;

@Service("service")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao dao;
	
	
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.list(map);
	}

	@Override
	public int insert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.insert(map);
	}

	@Override
	public Map<String, Object> detail(int seq) {
		// TODO Auto-generated method stub
		return dao.detail(seq);
	}

	@Override
	public int delete(List<Integer> list) {
		// TODO Auto-generated method stub
		return dao.delete(list);
		
	}

	@Override
	public Map<String, Object> pagination(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		int curPage = Integer.parseInt(map.get("pageNo").toString()); 
		int PAGE_SCALE = Integer.parseInt(map.get("listSize").toString()); ;
		int BLOCK_SCALE = 5;
		
		int count = dao.count(map);
		
		int totPage = (int) Math.ceil(count*1.0 / PAGE_SCALE);
		int totBlock = (int)Math.ceil(totPage / BLOCK_SCALE);
		  // *���� �������� ���° ������ ��Ͽ� ���ϴ��� ���
        int curBlock = (int)Math.ceil((curPage-1) / BLOCK_SCALE)+1;
        // *���� ������ ����� ����, �� ��ȣ ���
        int blockBegin = (curBlock-1)*BLOCK_SCALE+1;
        // ������ ����� ����ȣ
        int blockEnd = blockBegin+BLOCK_SCALE-1;
        // *������ ����� ������ �ʰ����� �ʵ��� ���
        if(blockEnd > totPage) blockEnd = totPage;
        // *������ ������ �� �̵��� ������ ��ȣ
        int prevPage = (curPage == 1)? 1:(curBlock-1)*BLOCK_SCALE;
        // *������ ������ �� �̵��� ������ ��ȣ
        int nextPage = curBlock > totBlock ? (curBlock*BLOCK_SCALE) : (curBlock*BLOCK_SCALE)+1;
        // ������ �������� ������ �ʰ����� �ʵ��� ó��
        if(nextPage >= totPage) nextPage = totPage;
        
       
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("curPage", curPage);
        pageMap.put("PAGE_SCALE", PAGE_SCALE);
        pageMap.put("BLOCK_SCALE", BLOCK_SCALE);
        pageMap.put("count", count);
        pageMap.put("totPage", totPage);
        pageMap.put("totBlock", totBlock);
        pageMap.put("curBlock", curBlock);
        pageMap.put("blockBegin", blockBegin);
        pageMap.put("blockEnd", blockEnd);
        pageMap.put("prevPage", prevPage);
        pageMap.put("nextPage", nextPage);
       
		
		return pageMap;
	}

	@Override
	public int update(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.update(map);
	}

	@Override
	public int maxSeq() {
		// TODO Auto-generated method stub
		return dao.maxSeq();
	}

	@Override
	public int fileInsert(Map<String, Object> fileMap) {
		// TODO Auto-generated method stub
		return dao.fileInsert(fileMap);
	}

	@Override
	public List<Map<String, Object>> files(int seq) {
		// TODO Auto-generated method stub
		return dao.files(seq);
	}

	@Override
	public int filedelete(List<Integer> list) {
		// TODO Auto-generated method stub
		return dao.deletfiles(list);
	}

	@Override
	public int count(int seq) {
		// TODO Auto-generated method stub
		return dao.count2(seq);
	}


	
	

}
