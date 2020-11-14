package com.members.model;

import java.util.List;

public class TestMembersJDBCDAO {

	public static void main(String[] args) {
		
		MembersJDBCDAO dao = new MembersJDBCDAO();
		
//		// 新增
//		MembersVO mem1 = new MembersVO();
//		mem1.setMem_email("11@abc.com");
//		mem1.setMem_pwd("321");
//		mem1.setMem_name("�I���t");
//		mem1.setMem_alias("�T���e���n�n");
//		mem1.setMem_gender("�L");
//		mem1.setMem_birth(java.sql.Date.valueOf("2020-11-11"));
//		mem1.setMem_mobile("0922123456");
//		mem1.setMem_tel("0377654321");
//		mem1.setMem_addr("�s�����F�޶m�ֵا�169��");
//		mem1.setMem_type(1);
//		mem1.setMem_regdate(java.sql.Date.valueOf("2020-11-11"));
//		mem1.setMem_stat(1);
//		dao.add(mem1);
//		
//		// 修改
//		MembersVO mem2 = new MembersVO();
//		mem2.setMem_no("M000000011");
//		mem2.setMem_email("11@abc.com");
//		mem2.setMem_pwd("321");
//		mem2.setMem_name("����t");
//		mem2.setMem_alias("���ߪ��T���e���n�n");
//		mem2.setMem_gender("�L");
//		mem2.setMem_birth(java.sql.Date.valueOf("2020-11-11"));
//		mem2.setMem_mobile("0922123456");
//		mem2.setMem_tel("0377654321");
//		mem2.setMem_addr("�s�����F�޶m�ֵا�169��");
//		mem2.setMem_type(0);
//		mem2.setMem_regdate(java.sql.Date.valueOf("2020-11-11"));
//		mem2.setMem_stat(0);
//		dao.update(mem2);
//		
//		// 刪除
//		dao.delete("M000000023");
//		
		// 查詢
		MembersVO mem3 = dao.findByPK("M000000001");
		System.out.print(mem3.getMem_no() + ",");
		System.out.print(mem3.getMem_email() + ",");
		System.out.print(mem3.getMem_pwd() + ",");
		System.out.print(mem3.getMem_name() + ",");
		System.out.print(mem3.getMem_alias() + ",");
		System.out.print(mem3.getMem_gender() + ",");
		System.out.print(mem3.getMem_birth() + ",");
		System.out.print(mem3.getMem_mobile() + ",");
		System.out.print(mem3.getMem_tel() + ",");
		System.out.print(mem3.getMem_addr() + ",");
		System.out.print(mem3.getMem_type() + ",");
		System.out.print(mem3.getMem_regdate() + ",");
		System.out.print(mem3.getMem_stat());
		System.out.println();
//		
//		// 查詢
//		List<MembersVO> list = dao.getAll();
//		for(MembersVO mem4 : list) {
//		System.out.print(mem4.getMem_no() + ",");
//		System.out.print(mem4.getMem_email() + ",");
//		System.out.print(mem4.getMem_pwd() + ",");
//		System.out.print(mem4.getMem_name() + ",");
//		System.out.print(mem4.getMem_alias() + ",");
//		System.out.print(mem4.getMem_gender() + ",");
//		System.out.print(mem4.getMem_birth() + ",");
//		System.out.print(mem4.getMem_mobile() + ",");
//		System.out.print(mem4.getMem_tel() + ",");
//		System.out.print(mem4.getMem_addr() + ",");
//		System.out.print(mem4.getMem_type() + ",");
//		System.out.print(mem4.getMem_regdate() + ",");
//		System.out.print(mem4.getMem_stat());
//		System.out.println();
//		}
	}
	
}
