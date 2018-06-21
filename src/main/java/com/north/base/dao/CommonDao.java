package com.north.base.dao;

public interface CommonDao {

	Integer selectSeq(String seqName);
	
	Integer selectTaskIdSeq(String seqName);
}
