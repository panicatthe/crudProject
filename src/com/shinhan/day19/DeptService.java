package com.shinhan.day19;

import java.util.List;

public class DeptService {

	DeptDAO deptDAO = new DeptDAO();

	public List<DeptDTO> getAllDepts() {
		return deptDAO.selectAll();
	}

	public DeptDTO getDeptById(int deptId) {
		return deptDAO.selectById(deptId);
	}

	public boolean addDept(DeptDTO dept) {
		return deptDAO.insert(dept) > 0;
	}

	public boolean updateDept(DeptDTO dept) {
		return deptDAO.update(dept) > 0;
	}

	public boolean deleteDept(int deptId) {
		return deptDAO.delete(deptId) > 0;
	}
}
