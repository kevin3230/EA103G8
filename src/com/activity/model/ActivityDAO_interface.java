package com.activity.model;

import java.util.List;

public interface ActivityDAO_interface {
	void add(ActivityVO activityVO); 
	void updata(ActivityVO activityVO);
	void delete(String act_no);
	ActivityVO findByPrimaryKey(String act_no);//使用表個資料型別來取得一筆資料
	List<ActivityVO> getAll(); //使用List來儲存所有表格資料
}
