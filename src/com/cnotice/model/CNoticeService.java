package com.cnotice.model;

import java.util.List;

public class CNoticeService {

	private CNoticeDAO_interface dao = null;
	
	public CNoticeService() {
		dao = new CNoticeDAO();
		}
	
	public CNoticeVO addCNotice(String cn_memno, String cn_omno, String cn_content, Integer cn_stat) {
		CNoticeVO cnVO = new CNoticeVO();
		
		cnVO.setCn_memno(cn_memno);
		cnVO.setCn_omno(cn_omno);
		cnVO.setCn_content(cn_content);
		cnVO.setCn_stat(cn_stat);
		
		dao.add(cnVO);
		return cnVO;
	}
	
	public CNoticeVO updateCNotice(String cn_no, String cn_memno, String cn_omno, String cn_content, Integer cn_stat) {
		CNoticeVO cnVO = new CNoticeVO();
		
		cnVO.setCn_no(cn_no);
		cnVO.setCn_memno(cn_memno);
		cnVO.setCn_omno(cn_omno);
		cnVO.setCn_content(cn_content);
		cnVO.setCn_stat(cn_stat);
		
		dao.update(cnVO);
		return cnVO;
	}
	
	public CNoticeVO getOneCN(String cn_no) {
		return dao.findByPK(cn_no);
	}

	public List<CNoticeVO> getAllCN() {
		return dao.getAll();
	}
	
	public List<CNoticeVO> getAllNewCN() {
		return dao.getAllNew();
	}
	
}
