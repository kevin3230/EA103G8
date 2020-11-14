package com.members.model;

import java.util.List;

public interface MembersDAO_interface {

	void add(MembersVO members);
	void update(MembersVO members);
	void pswupdate(MembersVO members);
	void delete(String mem_no);
	MembersVO findByPK(String mem_no);
	List<MembersVO> getAll();
	MembersVO checkByEmail(String mem_email);
}
