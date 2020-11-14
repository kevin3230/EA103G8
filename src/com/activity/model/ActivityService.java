package com.activity.model;

import java.util.List;

public class ActivityService {

	private ActivityDAO_interface dao;

	public ActivityService() {
		dao = new ActivityDAO();
	}

	public ActivityVO addActivity( String act_title, String act_content,String act_adminisno
								  ,String act_no) {

		ActivityVO activityVO = new ActivityVO();

		activityVO.setAct_title(act_title);
		activityVO.setAct_content(act_content);
		activityVO.setAct_adminisno(act_adminisno);
		activityVO.setAct_no(act_no);

		dao.add(activityVO);

		return activityVO;
	}

	public ActivityVO updateActivity(String act_no,String act_title, String act_content, 
								Integer act_stat,String act_adminisno) {

		ActivityVO activityVO = new ActivityVO();
		
		activityVO.setAct_no(act_no);
		activityVO.setAct_title(act_title);
		activityVO.setAct_content(act_content);
		activityVO.setAct_content(act_content);
		activityVO.setAct_adminisno(act_adminisno);
		

		dao.updata(activityVO);

		return activityVO;
	}

	public void deleteActivity(String act_no) {
		dao.delete(act_no);
	}

	public ActivityVO getOneEmp(String act_no) {
		return dao.findByPrimaryKey(act_no);
	}

	public List<ActivityVO> getAll() {
		return dao.getAll();
	}
}
