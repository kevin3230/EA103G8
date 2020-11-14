package com.vnotice.model;

import java.util.List;

public interface VNoticeDAO_interface {

	void add(VNoticeVO vnotice);
	void update(VNoticeVO vnotice);
	void updateStat(VNoticeVO vnotice);
	VNoticeVO findByPK(String vn_no);
	List<VNoticeVO> getAll();
	List<VNoticeVO> getAllNew();
}
