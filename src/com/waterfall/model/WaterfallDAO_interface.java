package com.waterfall.model;

import java.util.List;

public interface WaterfallDAO_interface {
	
	public String insert(WaterfallVO waterfallVO);
	public void update(WaterfallVO waterfallVO);
	public void delete(String wf_no);
	public WaterfallVO findByPrimaryKey(String wf_no);
	public List<WaterfallVO> getAll();
	public List<WFReplyVO> joinWFReply(String wf_no);
	public void fake_delete(String wf_no);
	
}
