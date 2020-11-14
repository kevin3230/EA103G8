package com.waterfall.model;

import java.util.List;

public class WFReplyService {

	private WFReplyDAO_interface dao;

	public WFReplyService() {
		dao = new WFReplyDAO();
	}

	public WFReplyVO addWFReply(String wfr_wfno, String wfr_memno, String wfr_content) {

		WFReplyVO wfreplyVO = new WFReplyVO();

		wfreplyVO.setWfr_wfno(wfr_wfno);
		wfreplyVO.setWfr_memno(wfr_memno);
		wfreplyVO.setWfr_content(wfr_content);
		dao.insert(wfreplyVO);

		return wfreplyVO;
	}

	public WFReplyVO updateWFReply(String wfr_content, String wfr_no) {

		WFReplyVO wfreplyVO = new WFReplyVO();

		wfreplyVO.setWfr_content(wfr_content);
		wfreplyVO.setWfr_no(wfr_no);
		dao.update(wfreplyVO);

		return wfreplyVO;
	}

	public void deleteWFReply(String wfr_no) {
		dao.delete(wfr_no);
	}

	public WFReplyVO getOneWFReply(String wfr_no) {
		return dao.findByPrimaryKey(wfr_no);
	}

	public List<WFReplyVO> getAll() {
		return dao.getAll();
	}
	
	public void fakeDeleteWFReply(String wfr_no) {
		dao.fake_delete(wfr_no);
	}
}
