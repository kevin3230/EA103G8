package com.guide.model;

import java.util.List;

public class GuideService {
	private GuideDAO_interface guideDAO;

	public GuideService() {
		guideDAO = new GuideDAO();
	}

	public String insert(GuideVO guideVO) {
		String guide_no = guideDAO.insert(guideVO);
		return guide_no;
	}

	public void update(GuideVO guideVO) {
		guideDAO.update(guideVO);
	}

	public GuideVO getOneGuide(String guide_no) {
		return guideDAO.findByPrimaryKey(guide_no);
	}

	public List<GuideVO> getAllGuide() {
		return guideDAO.getAll();
	}

	public void delete(String guide_no) {
		guideDAO.delete(guide_no);
	}

	public void delete(GuideVO guideVO) {
		guideDAO.delete(guideVO);
	}

	public void delete(List<GuideVO> guideVOList) {
		guideDAO.delete(guideVOList);
	}
}
