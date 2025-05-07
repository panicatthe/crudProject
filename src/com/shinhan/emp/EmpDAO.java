package com.shinhan.emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.shinhan.day16.DBUtil;

//Data Access Object
//PreparedStatement: sql 호출
//CallableStatement: procedure 호출
public class EmpDAO {

	// stored procedure (직원 번호를 받아서 이메일과 급여 리턴)
	public EmpDTO execute_sp(int empId) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
		CallableStatement st = null;
		String sql = "{call sp_empinfo(?,?,?)}";
		try {
			emp = new EmpDTO();
			st = conn.prepareCall(sql);
			st.setInt(1, empId);
			st.registerOutParameter(2, Types.VARCHAR);
			st.registerOutParameter(3, Types.DECIMAL);
			st.execute();
			String email = st.getString(2);
			Double salary = st.getDouble(3);
			emp.setEmail(email);
			emp.setSalary(salary);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return emp;
	}

	public int empDeleteById(int id) {
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		int rs = 0;
		String sql = "delete from employees " + "where employee_id = ?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, id);
			rs = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null); // ResultSet은 사용 안 했음
		}

		return rs;
	}

	// 부서 여러개
	public List<EmpDTO> selectByConditions(List<Integer> deptList, String jobid, int salary, String hdate) {
		List<EmpDTO> emplist = new ArrayList<>();
		if (deptList == null || deptList.isEmpty())
			return emplist;

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;

		// 동적 SQL 구성
		StringBuilder sql = new StringBuilder("SELECT * FROM employees WHERE job_id LIKE ? ");
		sql.append("AND department_id IN (");
		for (int i = 0; i < deptList.size(); i++) {
			sql.append("?");
			if (i < deptList.size() - 1)
				sql.append(", ");
		}
		sql.append(") AND salary >= ? AND hire_date >= ?");

		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql.toString());

			int index = 1;
			st.setString(index++, "%" + jobid + "%"); // LIKE 검색
			for (Integer deptId : deptList) {
				st.setInt(index++, deptId);
			}
			st.setInt(index++, salary);
			Date d = DateUtil.converToSQLDATE(DateUtil.coverToDate2(hdate));
			st.setDate(index, d);

			rs = st.executeQuery();
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}

		return emplist;
	}

	public List<EmpDTO> selectByCondition(int deptid, String jobid, int salary, String hdate) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String sql = "select  * " + "from employees " + "where job_id LIKE ? " + "and department_id = ?"
				+ "and salary >= ? " + "and hire_date >= ?";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql); // sql문 준비
			st.setString(1, "%" + jobid + "%"); // 또는 좌우 자유롭게 설정
			st.setInt(2, deptid);
			st.setInt(3, salary);
			Date d = DateUtil.converToSQLDATE(DateUtil.coverToDate2(hdate));
			st.setDate(4, d);
			rs = st.executeQuery();
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 직책으로 조회
	public List<EmpDTO> selectByJobAndDept(String job, int dept) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String sql = "select  * " + "from employees " + "where job_id = ? " + "and department_id = ?";
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql); // sql문 준비
			st.setString(1, job);
			st.setInt(2, dept);
			rs = st.executeQuery();
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 직책으로 조회
	public List<EmpDTO> selectByJob(String job) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String sql = "select  * " + "from employees " // -> 성능을 위해 나중에 바인딩 변수로
				+ "where job_id = ?";
//		Statement st = null // 바인딩 변수 지원 x -> preparedStat으로 
//		String sql = "select  * " 
//		+ "from employees "  // -> 성능을 위해 나중에 바인딩 변수로
//		+ "where job_id = '" + job + "' "; 
		try {
			conn = DBUtil.getConnection();
			st = conn.prepareStatement(sql); // sql문 준비
			st.setString(1, job);
			rs = st.executeQuery();
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 모든 직원 조회
	public List<EmpDTO> selectByDept(int deptid) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * " + "from employees " + "where department_id = " + deptid;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 직원번호로 직원정보 상세보기
	public EmpDTO selectById(int empid) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		EmpDTO emp = null;
		String sql = "select * " + "from employees " + "where employee_id = " + empid;
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emp;
	}

	// 모든 직원 조회
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees";
		try {
			conn = DBUtil.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		return EmpDTO.builder().commission_pct(rs.getDouble("commission_pct")).department_id(rs.getInt("department_id"))
				.email(rs.getString("email")).employee_id(rs.getInt("employee_id"))
				.first_name(rs.getString("first_name")).last_name(rs.getString("last_name"))
				.hire_date(rs.getDate("hire_date")).job_id(rs.getString("job_id")).manager_id(rs.getInt("manager_id"))
				.phone_number(rs.getString("phone_number")).salary(rs.getDouble("salary")).build();
	}

	public int insertEmp(EmpDTO emp) {
		int result = 0;
		String sql = """
				    INSERT INTO employees (
				        EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE,
				        JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID
				    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";

		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, emp.getEmployee_id());
			st.setString(2, emp.getFirst_name());
			st.setString(3, emp.getLast_name());
			st.setString(4, emp.getEmail());
			st.setString(5, emp.getPhone_number());

			java.sql.Date sqlHireDate = DateUtil.converToSQLDATE(emp.getHire_date());
			st.setDate(6, sqlHireDate);

			st.setString(7, emp.getJob_id());
			st.setDouble(8, emp.getSalary());
			st.setDouble(9, emp.getCommission_pct());
			st.setInt(10, emp.getManager_id());
			st.setInt(11, emp.getDepartment_id());

			result = st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null); // ResultSet은 사용 안 했음
		}
		return result;
	}

	public int updateEmpById(EmpDTO emp) {
		int result = 0;
		Map<String, Object> updateMap = new LinkedHashMap<>();

		if (emp.getFirst_name() != null)
			updateMap.put("first_name", emp.getFirst_name());
		if (emp.getLast_name() != null)
			updateMap.put("last_name", emp.getLast_name());
		if (emp.getEmail() != null)
			updateMap.put("email", emp.getEmail());
		if (emp.getPhone_number() != null)
			updateMap.put("phone_number", emp.getPhone_number());
		if (emp.getHire_date() != null)
			updateMap.put("hire_date", DateUtil.converToSQLDATE(emp.getHire_date()));
		if (emp.getJob_id() != null)
			updateMap.put("job_id", emp.getJob_id());
		if (emp.getSalary() != 0)
			updateMap.put("salary", emp.getSalary());
		if (emp.getCommission_pct() != 0)
			updateMap.put("commission_pct", emp.getCommission_pct());
		if (emp.getManager_id() != 0)
			updateMap.put("manager_id", emp.getManager_id());
		if (emp.getDepartment_id() != 0)
			updateMap.put("department_id", emp.getDepartment_id());

		if (updateMap.isEmpty()) {
			System.out.println("⚠️ 업데이트할 필드가 없습니다.");
			return 0;
		}

		StringBuilder sql = new StringBuilder("UPDATE employees SET ");
		updateMap.keySet().forEach(col -> sql.append(col).append(" = ?, "));
		sql.setLength(sql.length() - 2); // 마지막 콤마 제거
		sql.append(" WHERE employee_id = ?");

		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql.toString());
			int idx = 1;
			for (Object val : updateMap.values()) {
				if (val instanceof String s)
					st.setString(idx++, s);
				else if (val instanceof Integer i)
					st.setInt(idx++, i);
				else if (val instanceof Double d)
					st.setDouble(idx++, d);
				else if (val instanceof java.sql.Date d)
					st.setDate(idx++, d);
			}

			st.setInt(idx, emp.getEmployee_id()); // 마지막 WHERE절 PK 바인딩

			result = st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

	public int updateEmpById2(EmpDTO emp) {
		int result = 0;
		String sql = """
				    UPDATE employees SET
				        first_name = ?,
				        last_name = ?,
				        email = ?,
				        phone_number = ?,
				        hire_date = ?,
				        job_id = ?,
				        salary = ?,
				        commission_pct = ?,
				        manager_id = ?,
				        department_id = ?
				    WHERE employee_id = ?
				""";

		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(sql);
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());

			java.sql.Date sqlHireDate = DateUtil.converToSQLDATE(emp.getHire_date());
			st.setDate(5, sqlHireDate);

			st.setString(6, emp.getJob_id());
			st.setDouble(7, emp.getSalary());
			st.setDouble(8, emp.getCommission_pct());
			st.setInt(9, emp.getManager_id());
			st.setInt(10, emp.getDepartment_id());

			// WHERE 절 바인딩 - PK로 해당 직원 식별
			st.setInt(11, emp.getEmployee_id());

			result = st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}

		return result;
	}

}
