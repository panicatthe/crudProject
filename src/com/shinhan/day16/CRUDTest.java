package com.shinhan.day16;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDTest {

	public static void main(String[] args) {
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				select department_id, max(salary), min(salary)
				from employees
				group by department_id
				having max(salary) != min(salary)
				order by department_id
				""";
		Connection conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int deptId = rs.getInt(1);
				int max = rs.getInt(2);
				int min = rs.getInt(3);
				System.out.println(deptId + "\t" + max + "\t" + min);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}

	}

	public static void f2() {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "hr";
		String userpass = "hr";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				select department_id, count(*)
				from employees
				group by department_id
				having count(*) > 4
				order by department_id
				""";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, userid, userpass);
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				int deptid = rs.getInt("department_id");
				int count = rs.getInt(2);
				System.out.println(deptid + "--" + count);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void f1() throws ClassNotFoundException, SQLException {
		// 1 JDBC Driver 준비 - build path에 class path 추가

		// 2 JDBC Driver 메모리에 load
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC load 성공");

		// 3 Connection
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "hr";
		String userpass = "hr";
		Connection conn = DriverManager.getConnection(url, userid, userpass);
		System.out.println("connection 성공");

		// 4 SQL문 보낼 통로 열기
		Statement st = conn.createStatement();
		System.out.println("통로 열기 성공");

		// 5 SQL문 만들기
		String sql = """
				select *
				from employees
				where department_id = 80
				""";
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			int empid = rs.getInt("employee_id");
			String fname = rs.getString("first_name");
			Date date = rs.getDate("hire_date");
			double comm = rs.getDouble("commission_pct");
			System.out.printf("직원 번호: %d, 이름: %s, 입사일: %s, 커미션:%3.1f\n", empid, fname, date, comm);
		}

		rs.close();
		st.close();
		conn.close();

	}

}
