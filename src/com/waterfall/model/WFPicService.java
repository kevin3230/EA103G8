package com.waterfall.model;

import java.sql.Blob;
import java.util.List;

public class WFPicService {

	private WFPicDAO_interface dao;

	public WFPicService() {
		dao = new WFPicDAO();
	}

	public WFPicVO addWFPic(String wfp_wfno, byte[] wfp_pic) {

		WFPicVO wfpicVO = new WFPicVO();

		wfpicVO.setWfp_wfno(wfp_wfno);
		wfpicVO.setWfp_pic(wfp_pic);
		dao.insert(wfpicVO);

		return wfpicVO;
	}

	public void deleteWFPic(String wfp_no) {
		dao.delete(wfp_no);
	}

	public WFPicVO getOneWFPic(String wfp_no) {
		return dao.findByPrimaryKey(wfp_no);
	}

	public List<WFPicVO> getAll(String wfp_no) {
		return dao.getAll(wfp_no);
	}
}
