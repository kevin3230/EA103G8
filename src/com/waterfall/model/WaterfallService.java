package com.waterfall.model;

import java.util.List;

public class WaterfallService {

	private WaterfallDAO_interface dao;

	public WaterfallService() {
		dao = new WaterfallDAO();
	}

	public WaterfallVO addWaterfall(String wf_memno, String wf_title, String wf_content) {

		WaterfallVO waterfallVO = new WaterfallVO();

		waterfallVO.setWf_memno(wf_memno);
		waterfallVO.setWf_title(wf_title);
		waterfallVO.setWf_content(wf_content);
		String wf_no = dao.insert(waterfallVO);
		waterfallVO.setWf_no(wf_no);
		
		return waterfallVO;
	}

	public WaterfallVO updateWaterfall(String wf_title, String wf_content, String wf_no) {

		WaterfallVO waterfallVO = new WaterfallVO();

		waterfallVO.setWf_title(wf_title);
		waterfallVO.setWf_content(wf_content);
		waterfallVO.setWf_no(wf_no);
		dao.update(waterfallVO);

		return waterfallVO;
	}

	public void deleteWaterfall(String wf_no) {
		dao.delete(wf_no);
	}

	public WaterfallVO getOneWaterfall(String wf_no) {
		return dao.findByPrimaryKey(wf_no);
	}

	public List<WaterfallVO> getAll() {
		return dao.getAll();
	}
	public List<WFReplyVO> joinWFReply(String wfr_wfno) {
		return dao.joinWFReply(wfr_wfno);
	}
	public void fakeDeleteWaterfall(String wf_no) {
		dao.fake_delete(wf_no);
	}
	
}
