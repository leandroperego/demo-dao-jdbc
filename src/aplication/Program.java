package aplication;

import model.dao.Dao;
import model.dao.DaoFactory;
import model.entidades.Department;

public class Program {

	public static void main(String[] args) {
		
		Dao<Department> depart = DaoFactory.createDepartmentDao();
		
		Department dep = depart.findById(5);
		
		System.out.println(dep);
		
	}

}
