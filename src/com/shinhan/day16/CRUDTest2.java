package com.shinhan.day16;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDTest2 {
	public static void main(String[] args) throws SQLException {
		// 모두 성공 시 commit

		// insert

		// update
		Connection conn = null;
		Statement st = null;
		int result1 = 0;
		String sql1 = """
				INSERT INTO EMP1(EMPLOYEE_ID,FIRST_NAME, LAST_NAME, HIRE_DATE, JOB_ID, EMAIL)
				VALUES(4, 'aa','bb', sysdate, 'IT', 'ZDSD')""";
		String sql2 = """
				UPDATE EMP1 SET SALARY = 2000 WHERE EMPLOYEE_ID = 198
				""";

		conn = DBUtil.getConnection();
		st = conn.createStatement();
		conn.setAutoCommit(false);
		if (st.executeUpdate(sql1) >= 1 && st.executeUpdate(sql2) >= 1) {
			System.out.println("둘다 성공");
			conn.commit();
		}else {
			conn.rollback();
		}

	}

	public static void f_4() throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				delete from EMPl where employee_id < 100""";

		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result);
	}

	public static void f_3() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				SELECT ENAME, SAL, MGR
				FROM EMP
				WHERE MGR = (SELECT EMPNO FROM EMP WHERE ENAME = 'KING')
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()) {
			String ename = rs.getString(1);
			int sal = rs.getInt(2);
			int mgr = rs.getInt(3);

			System.out.println(ename + "\t" + sal + "\t" + mgr);
		}

		DBUtil.dbDisconnect(conn, st, rs);
	}

	public static void f_2() throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				update empl
				set department_id = (select department_id
				                    from employees
				                    where employee_id = 100),
				    salary= (select salary
				             from employees
				             where employee_id = 101)
				where employee_id = 999				""";

		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result);
	}

	public static void f1() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				SELECT ENAME, SAL, MGR
				FROM EMP
				WHERE MGR = (SELECT EMPNO FROM EMP WHERE ENAME = 'KING')
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()) {
			String ename = rs.getString(1);
			int sal = rs.getInt(2);
			int mgr = rs.getInt(3);

			System.out.println(ename + "\t" + sal + "\t" + mgr);
		}

		DBUtil.dbDisconnect(conn, st, rs);
	}

}
