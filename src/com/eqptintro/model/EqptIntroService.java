package com.eqptintro.model;

import java.util.List;

public class EqptIntroService {

	private EqptIntroDAO_interface dao;

	public EqptIntroService() {
		dao = new EqptIntroDAO();
	}

	public EqptIntroVO addEqptIntro(String ei_adminisno, String ei_title, String ei_content) {

		EqptIntroVO eqptintroVO = new EqptIntroVO();

		eqptintroVO.setEi_adminisno(ei_adminisno);
		eqptintroVO.setEi_title(ei_title);
		eqptintroVO.setEi_content(ei_content);
		dao.insert(eqptintroVO);

		return eqptintroVO;
	}

	public EqptIntroVO updateEqptIntro(String ei_adminisno, String ei_title, String ei_content, Integer ei_stat, String ei_no) {

		EqptIntroVO eqptintroVO = new EqptIntroVO();

		eqptintroVO.setEi_adminisno(ei_adminisno);
		eqptintroVO.setEi_title(ei_title);
		eqptintroVO.setEi_content(ei_content);
		eqptintroVO.setEi_stat(ei_stat);
		eqptintroVO.setEi_no(ei_no);
		dao.update(eqptintroVO);

		return eqptintroVO;
	}

	public void deleteEqptIntro(String ei_no) {
		dao.delete(ei_no);
	}

	public EqptIntroVO getOneEqptIntro(String ei_no) {
		return dao.findByPrimaryKey(ei_no);
	}

	public List<EqptIntroVO> getAll() {
		return dao.getAll();
	}
}
