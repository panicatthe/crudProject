package com.shinhan.emp;

import java.util.List;

//이체업무 : (인출하기, 입금하기)
//비밀번호 암호화
public class EmpService {
	
	EmpDAO empDAO = new EmpDAO();

	public List<EmpDTO> selectAll(){
		return empDAO.selectAll();
	}
	
	public EmpDTO selectById(int empid) {
		return empDAO.selectById(empid);
	}
	
	public List<EmpDTO> selectByDept(int deptid){
		return empDAO.selectByDept(deptid);
	}
	
	public List<EmpDTO> selectByJob(String job){
		return empDAO.selectByJob(job);
	}
	
	public List<EmpDTO> selectByJobAndDept(String job, int dept) {
		return empDAO.selectByJobAndDept(job, dept);
	}

	public List<EmpDTO> selectByCondition(int deptid, String jobid, int salary, String hdate) {
		return empDAO.selectByCondition(deptid, jobid, salary, hdate);
	}
	
	public List<EmpDTO> selectByCondition(List<Integer> deptList, String jobid, int salary, String hdate) {
	    return empDAO.selectByConditions(deptList, jobid, salary, hdate);
	}
	
	public int empDeleteById(int id) {
		return empDAO.empDeleteById(id);
	}
	
	public int empInsert(EmpDTO emp) {
		return empDAO.insertEmp(emp);
	}

	public int empUpdate(EmpDTO emp) {
		return empDAO.updateEmpById(emp);
	}
	
	public EmpDTO execute_sp(int empId) {
		return empDAO.execute_sp(empId);
	}

}
