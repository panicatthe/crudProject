package com.shinhan.day16;

import java.util.List;

//Service : business logic 수행
//1)이체업무 : (인출하기, 입금하기)
//2)비밀번호 암호화 
public class EmpService {

	EmpDAO empDAO = new EmpDAO();
	
	public int empInsert(EmpDTO emp) {
		return empDAO.empInsert(emp);
	}
	public int empDeleteById(int empid) {
		return empDAO.empDeleteById(empid);
	}
	public List<EmpDTO> selectByJobAndDept(String job, int dept) {
		return empDAO.selectByJobAndDept(job, dept);
	}
	public List<EmpDTO> selectByJob(String job) {
		return empDAO.selectByJob(job);
	}
	public List<EmpDTO> selectByDept(int deptid) {
		return empDAO.selectByDept(deptid);
	}
	public EmpDTO selectById(int empid) {
		return empDAO.selectById(empid);
	}
	public List<EmpDTO> selectAll() {
		//......
		return empDAO.selectAll();
	}
	public List<EmpDTO> selectByCondition(Integer[] deptid, String jobid, int salary, String hdate) {
		// TODO Auto-generated method stub
		return empDAO.selectByCondition(deptid,jobid,salary,hdate);
	}
	public int empUpdate(EmpDTO emp) {
		// TODO Auto-generated method stub
		return 0;
	}
}












