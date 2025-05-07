package com.shinhan.day19;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.day16.DBUtil;

public class DeptDAO {

	public List<DeptDTO> selectAll() {
		List<DeptDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM departments";
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				DeptDTO dept = DeptDTO.builder().department_id(rs.getInt("department_id"))
						.department_name(rs.getString("department_name")).manager_id(rs.getInt("manager_id"))
						.location_id(rs.getInt("location_id")).build();
				list.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return list;
	}

	public DeptDTO selectById(int deptId) {
		DeptDTO dept = null;
		String sql = "SELECT * FROM departments WHERE department_id = ?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptId);
			rs = pst.executeQuery();
			if (rs.next()) {
				dept = DeptDTO.builder().department_id(rs.getInt("department_id"))
						.department_name(rs.getString("department_name")).manager_id(rs.getInt("manager_id"))
						.location_id(rs.getInt("location_id")).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return dept;
	}

	public int insert(DeptDTO dept) {
		String sql = "INSERT INTO departments(department_id, department_name, manager_id, location_id) VALUES (?, ?, ?, ?)";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		int result = 0;

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, dept.getDepartment_id());
			pst.setString(2, dept.getDepartment_name());
			pst.setInt(3, dept.getManager_id());
			pst.setInt(4, dept.getLocation_id());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		return result;
	}

	public int update(DeptDTO dept) {
		String sql = "UPDATE departments SET department_name=?, manager_id=?, location_id=? WHERE department_id=?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		int result = 0;

		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, dept.getDepartment_name());
			pst.setInt(2, dept.getManager_id());
			pst.setInt(3, dept.getLocation_id());
			pst.setInt(4, dept.getDepartment_id());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		return result;
	}

	public int delete(int deptId) {
		String sql = "DELETE FROM departments WHERE department_id=?";
		Connection conn = DBUtil.getConnection();
		PreparedStatement pst = null;
		int result = 0;

		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptId);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		return result;
	}
}
