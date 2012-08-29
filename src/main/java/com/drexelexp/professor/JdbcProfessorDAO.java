package com.drexelexp.professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.drexelexp.baseDAO.BaseDAO;
import com.drexelexp.baseDAO.JdbcDAO;

/**
 * Implementation of ProfessorDAO in JDBC style.
 * @author
 *
 */
public class JdbcProfessorDAO extends JdbcDAO implements BaseDAO<Professor>{

	@Override
	public void insert(Professor instance) {
		String sql = "INSERT INTO PROFESSORS " + "(NAME) VALUES (?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, instance.getName());
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public ArrayList<Professor> getAll() {
		ArrayList<Professor> professors = new ArrayList<Professor>();
		String sql = "SELECT * FROM PROFESSORS";
		Connection conn = null;
		 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
	
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				Professor professor = null;
				professor = new Professor(
					rs.getInt("PROF_ID"),
					rs.getString("NAME")
				);
				professors.add(professor);
			}
			rs.close();
			ps.close();
			return professors;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
		
	}
	@Override
	public Professor getById(int id) {
		String sql = "SELECT * FROM PROFESSORS WHERE PROF_ID = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			Professor professor = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				professor = new Professor(
					rs.getInt("PROF_ID"),
					rs.getString("NAME")
				);
			}
			rs.close();
			ps.close();
			return professor;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

}
