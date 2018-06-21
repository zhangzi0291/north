package com.north.base.dao;


import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BatchDao {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@SuppressWarnings("rawtypes")
	public boolean batchInsert(String str, List objs,boolean success){
		// 批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		// 批量处理数量
		int batchNum = 500;
		try {
			if (objs != null) {
				for (int i = 0; i < objs.size(); i++) {
					sqlSession.insert(str, objs.get(i));
					if ((i != 0 && i % batchNum == 0) || i == objs.size() - 1) {
						sqlSession.commit();
						sqlSession.flushStatements();
						sqlSession.clearCache();
					}
				}
				success=true;
			}
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return success;
	}
}
