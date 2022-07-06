package model.dao.implem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.DaoSellerJDBC;
import model.entidades.Department;
import model.entidades.Seller;

public class SellerDaoJDBC implements DaoSellerJDBC {
	
	private Connection conn = null;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			
			int linhasAfetadas = ps.executeUpdate();
			
			if (linhasAfetadas > 0 ) {
				ResultSet rs = ps.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro. Nenhuma linha foi criada");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE seller "
					+ "SET Name = ? , Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());
			
			ps.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE "
					+ "FROM seller "
					+ "WHERE Id = ?");
			ps.setInt(1, id);
			
			int linha = ps.executeUpdate();
			
			if (linha == 0) {
				throw new DbException("Erro. Esse id não foi encontrado");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//CRIAR O PREPARE STATEMENT DE ACORDO COMO QUER O RESULTADO
			ps = this.conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department "
					+ "ON Seller.DepartmentId = department.Id "
					+ "WHERE Seller.Id = ?");
			
			//INSTANCIAR O VALOR QUE QUER BUSCAR O RESULTADO
			ps.setInt(1, id);
			
			// UM RESULT SET PARA RECEBER O COMANDO ACIMA
			rs = ps.executeQuery();
			
			//PERCORRER O RESULT SET PARA RETORNAR O SELLER
			if (rs.next()) {
				return sellerInstancia(rs, departmentInstancia(rs));
						
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}
	
	private Seller sellerInstancia(ResultSet rs, Department dep) throws SQLException {
		return new Seller(rs.getInt("Id"), rs.getString("Name")
				,rs.getString("Email")
				,rs.getDate("BirthDate")
				,rs.getDouble("BaseSalary")
				,dep);
	}
	
	private Department departmentInstancia(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
								+ "FROM seller "
								+ "INNER JOIN department "
								+ "ON seller.DepartmentId = department.Id ");
			
			rs = ps.executeQuery();
			
			List<Seller> lista = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department depart = map.get(rs.getInt("DepartmentId"));
				
				if (depart == null) {
					depart = departmentInstancia(rs);
					map.put(rs.getInt("DepartmentId"), depart);
				}
				
				Seller seller = sellerInstancia(rs, depart);
				lista.add(seller);
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

	@Override
	public List<Seller> findByDepartment(Department dep) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
								+ "FROM seller "
								+ "INNER JOIN department "
								+ "ON seller.DepartmentId = department.Id "
								+ "WHERE DepartmentId = ? "
								+ "ORDER BY Name");
			
			ps.setInt(1, dep.getId());
			
			rs = ps.executeQuery();
			
			List<Seller> lista = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {
				
				Department depart = map.get(rs.getInt("DepartmentId"));
				
				if (depart == null) {
					depart = departmentInstancia(rs);
					map.put(rs.getInt("DepartmentId"), depart);
				}
				
				Seller seller = sellerInstancia(rs, depart);
				lista.add(seller);
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
