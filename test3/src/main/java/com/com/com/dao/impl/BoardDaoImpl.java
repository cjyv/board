package com.com.com.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.com.com.dao.BoardDao;





@Repository("dao")
public class BoardDaoImpl implements BoardDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ppppp.boardList", map);
	}

	@Override
	public int insert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.insert("ppppp.insert", map);
	}

	@Override
	public Map<String, Object> detail(int seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ppppp.detail", seq);
	}

	@Override
	public int delete(List<Integer> list) {
		// TODO Auto-generated method stub
		return sqlSession.delete("ppppp.delete", list);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ppppp.total", map);
	}

	@Override
	public int update(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.update("ppppp.update",map);
	}

	@Override
	public int maxSeq() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ppppp.maxSeq");
	}

	@Override
	public int fileInsert(Map<String, Object> fileMap) {
		// TODO Auto-generated method stub
		return sqlSession.insert("ppppp.fileInsert",fileMap);
	}

	@Override
	public List<Map<String, Object>> files(int seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("ppppp.files",seq);
	}

	@Override
	public int deletfiles(List<Integer> list) {
		// TODO Auto-generated method stub
		return sqlSession.delete("ppppp.filedelete",list);
	}

	@Override
	public int count2(int seq) {
		// TODO Auto-generated method stub
		return sqlSession.update("ppppp.count2",seq);
	}




}
