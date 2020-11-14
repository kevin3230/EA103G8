package com.waterfall.model;

import java.util.List;

import com.waterfall.model.WaterfallVO;

public interface WFPicDAO_interface {
	public void insert(WFPicVO wfpicVO);
	public void delete(String wfp_no);
	public WFPicVO findByPrimaryKey(String wfp_no);
	public List<WFPicVO> getAll(String wfp_no);
	

}
