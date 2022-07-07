package model.dao.implem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DaoDepartmentJDBC;
import model.entidades.Department;

public class DepartmentDaoJDBC implements DaoDepartmentJDBC{

	private Connection conn = null;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO department "
					+ "SET Name = ? ", Statement.RETURN_GENERATED_KEYS);
			
			conn.setAutoCommit(false);
			ps.setString(1, obj.getName());
			
			int linha = ps.executeUpdate();
			
			if (linha > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				conn.commit();
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro. Não foi inserido nenhum dado");
			}
			
		}
		catch (SQLException e) {
			rollBack(conn, e);
		}
		finally {
			DB.closeStatement(ps);
		}		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ? ");
			
			conn.setAutoCommit(false);
			ps.setInt(2, obj.getId());
			ps.setString(1, obj.getName());
			
			ps.executeUpdate();
			conn.commit();
			
		}
		catch (SQLException e) {
			rollBack(conn, e);
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			
			ps = conn.prepareStatement("DELETE FROM department "
					+ "WHERE Id = ?");
			conn.setAutoCommit(false);
			ps.setInt(1, id);
			
			int linha = ps.executeUpdate();
			conn.commit();
			if (linha == 0) {
				throw new DbException("Erro. Id não encontrado!");
			}
			
		} catch (SQLException e) {
			rollBack(conn, e);
		}
		finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM department "
									+ "WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Department(rs.getInt("Id"), rs.getString("Name"));
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM department");
			
			rs = ps.executeQuery();
			List<Department> lista = new ArrayList<Department>();
			
			while (rs.next()) {
				lista.add(new Department(rs.getInt("Id"), rs.getString("Name")));
			}
			return lista;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

}
