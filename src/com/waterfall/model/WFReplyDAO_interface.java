package com.waterfall.model;

import java.util.List;

public interface WFReplyDAO_interface {
	
	public void insert(WFReplyVO wfreplyVO);
	public void update(WFReplyVO wfreplyVO);
	public void delete(String wfr_no);
	public WFReplyVO findByPrimaryKey(String wfr_no);
	public List<WFReplyVO> getAll();
	public void fake_delete(String wfr_no);
	
}
