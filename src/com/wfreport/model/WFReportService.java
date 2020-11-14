package com.wfreport.model;

import java.util.List;

public class WFReportService {

	private WFReportDAO_interface dao;

	public WFReportService() {
		dao = new WFReportDAO();
	}

	public WFReportVO addWFReport(String rep_wfno, String rep_wfrno, String rep_memno, String rep_content) {

		WFReportVO wfreportVO = new WFReportVO();

		wfreportVO.setRep_wfno(rep_wfno);
		wfreportVO.setRep_wfrno(rep_wfrno);
		wfreportVO.setRep_memno(rep_memno);
		wfreportVO.setRep_content(rep_content);
		dao.insert(wfreportVO);

		return wfreportVO;
	}
	
	public WFReportVO updatePassWFReport(String rep_adminisno, String rep_no) {

		WFReportVO wfreportVO = new WFReportVO();

		wfreportVO.setRep_adminisno(rep_adminisno);
		wfreportVO.setRep_no(rep_no);
		dao.update_pass(wfreportVO);

		return wfreportVO;
	}	

	public WFReportVO updateUnpassWFReport(String rep_adminisno, String rep_no) {

		WFReportVO wfreportVO = new WFReportVO();

		wfreportVO.setRep_adminisno(rep_adminisno);
		wfreportVO.setRep_no(rep_no);
		dao.update_unpass(wfreportVO);

		return wfreportVO;
	}

	public WFReportVO getOneWFReport(String rep_no) {
		return dao.findByPrimaryKey(rep_no);
	}

	public List<WFReportVO> getAll() {
		return dao.getAll();
	}
}
