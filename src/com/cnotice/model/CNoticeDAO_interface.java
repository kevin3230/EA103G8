package com.cnotice.model;

import java.util.List;

public interface CNoticeDAO_interface {

	void add(CNoticeVO cnotice);
	void update(CNoticeVO cnotice);
	void updateStat(CNoticeVO cnotice);
	CNoticeVO findByPK(String cn_no);
	List<CNoticeVO> getAll();
	List<CNoticeVO> getAllNew();
	
}
