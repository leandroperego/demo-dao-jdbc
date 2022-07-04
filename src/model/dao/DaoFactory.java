package model.dao;

import java.sql.Connection;

import model.dao.implem.DepartmentDaoJDBC;
import model.dao.implem.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDaoJDBC createSellerDao(Connection conn) {
		return new SellerDaoJDBC(conn);
	}
	
	public static DepartmentDaoJDBC createDepartmentDao(Connection conn) {
		return new DepartmentDaoJDBC(conn);
	}
}
