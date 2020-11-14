package com.authority.model;

import java.util.List;

public class AuthorityService {

	private AuthorityDAO_interface dao;

	public AuthorityService() {
		dao = new AuthorityDAO();
	}

	public AuthorityVO addAuthority( String auth_name, Integer auth_no ) {

		AuthorityVO authorityVO = new AuthorityVO();

		authorityVO.setAuth_name(auth_name);
		authorityVO.setAuth_no(auth_no);


		dao.add(authorityVO);

		return authorityVO;
	}

	public AuthorityVO updateAuthority(Integer auth_no, String auth_name) {

		AuthorityVO authorityVO = new AuthorityVO();
		
		authorityVO.setAuth_no(auth_no);
		authorityVO.setAuth_name(auth_name);

		

		dao.updata(authorityVO);

		return authorityVO;
	}

	public void deleteAuthority(Integer auth_no) {
		dao.delete(auth_no);
	}

	public AuthorityVO getOneEmp(Integer auth_no) {
		return dao.findByPrimaryKey(auth_no);
	}

	public List<AuthorityVO> getAll() {
		return dao.getAll();
	}
}
