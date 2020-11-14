package com.vnotice.model;

import java.util.List;

public class VNoticeService {
	private VNoticeDAO_interface dao = null;
	
	public VNoticeService() {
		dao = new VNoticeDAO();
		}
	
	public VNoticeVO addVNotice(String vn_vdno, String vn_omno, String vn_content, Integer vn_stat) {
		VNoticeVO vnVO = new VNoticeVO();
		
		vnVO.setVn_vdno(vn_vdno);
		vnVO.setVn_omno(vn_omno);
		vnVO.setVn_content(vn_content);
		vnVO.setVn_stat(vn_stat);
		
		dao.add(vnVO);
		return vnVO;
	}
	
	public VNoticeVO updateVNotice(String vn_no, String vn_vdno, String vn_omno, String vn_content, Integer vn_stat) {
		VNoticeVO vnVO = new VNoticeVO();
		
		vnVO.setVn_no(vn_no);
		vnVO.setVn_vdno(vn_vdno);
		vnVO.setVn_omno(vn_omno);
		vnVO.setVn_content(vn_content);
		vnVO.setVn_stat(vn_stat);
		
		dao.update(vnVO);
		return vnVO;
	}
	
	public VNoticeVO getOneVN(String vn_no) {
		return dao.findByPK(vn_no);
	}

	public List<VNoticeVO> getAllVN() {
		return dao.getAll();
	}
	
	public List<VNoticeVO> getAllNewVN() {
		return dao.getAllNew();
	}
	
}
