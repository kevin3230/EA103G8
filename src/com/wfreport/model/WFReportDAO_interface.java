package com.wfreport.model;

import java.util.List;

public interface WFReportDAO_interface {
	public void insert(WFReportVO wfreportVO);
	public void update_pass(WFReportVO wfreportVO);
	public void update_unpass(WFReportVO wfreportVO);
	public WFReportVO findByPrimaryKey(String rep_no);
	public List<WFReportVO> getAll();
	
}
