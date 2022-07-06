package model.dao.implem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department dep) {
		return null;
	}

}
