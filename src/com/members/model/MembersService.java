package com.members.model;

import java.sql.Date;
import java.util.List;

public class MembersService {

	private MembersDAO_interface dao = null;
	
	public MembersService() {
		dao = new MembersDAO();
		}
	
	public MembersVO signUpMem(String mem_email, String mem_pwd, String mem_name, String mem_alias
			, String mem_gender, Date mem_birth,String mem_mobile,String mem_tel, String mem_addr
			, int mem_type, int mem_stat) {
		
		MembersVO memVO = new MembersVO();

		memVO.setMem_email(mem_email);
		memVO.setMem_pwd(mem_pwd);
		memVO.setMem_name(mem_name);
		memVO.setMem_alias(mem_alias);
		memVO.setMem_gender(mem_gender);
		memVO.setMem_birth(mem_birth);
		memVO.setMem_mobile(mem_mobile);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_addr(mem_addr);
		memVO.setMem_type(mem_type);
		memVO.setMem_stat(mem_stat);
		
		dao.add(memVO);
		return memVO;
	}
	
	public MembersVO updateMem(String mem_no, String mem_email, String mem_pwd, String mem_name, String mem_alias
			, String mem_gender, Date mem_birth,String mem_mobile, String mem_tel, String mem_addr
			, int mem_type, int mem_stat) {
		
		MembersVO memVO = new MembersVO();
		
		memVO.setMem_no(mem_no);
		memVO.setMem_email(mem_email);
		memVO.setMem_pwd(mem_pwd);
		memVO.setMem_name(mem_name);
		memVO.setMem_alias(mem_alias);
		memVO.setMem_gender(mem_gender);
		memVO.setMem_birth(mem_birth);
		memVO.setMem_mobile(mem_mobile);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_addr(mem_addr);
		memVO.setMem_type(mem_type);
		memVO.setMem_stat(mem_stat);
		
		dao.update(memVO);
		return memVO;
	}
	
	public MembersVO pswupdateMem(String mem_no, String mem_pwd) {
		
		MembersVO memVO = new MembersVO();
		
		memVO.setMem_no(mem_no);
		memVO.setMem_pwd(mem_pwd);
		
		dao.pswupdate(memVO);
		return memVO;
	}
	
	public void deleteMem(String mem_no) {
		dao.delete(mem_no);
	}

	public MembersVO getOneMem(String mem_no) {
		return dao.findByPK(mem_no);
	}

	public List<MembersVO> getAllMem() {
		return dao.getAll();
	}
	
	public MembersVO signInMem(String mem_email) {
		return dao.checkByEmail(mem_email);
	}
	
}
