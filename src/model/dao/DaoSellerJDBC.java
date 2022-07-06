package model.dao;

import java.util.List;

import model.entidades.Department;
import model.entidades.Seller;

public interface DaoSellerJDBC extends Dao<Seller>{

	List<Seller> findByDepartment(Department dep);
}
