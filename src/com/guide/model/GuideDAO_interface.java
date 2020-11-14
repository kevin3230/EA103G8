package com.guide.model;

import java.util.List;

public interface GuideDAO_interface {

	public String insert(GuideVO guideVO);

	public void update(GuideVO guideVO);

	public GuideVO findByPrimaryKey(String guide_no);

	public List<GuideVO> getAll();

	public void delete(String guide_no);

	public void delete(GuideVO guideVO);

	public void delete(List<GuideVO> guideVOList);
}
