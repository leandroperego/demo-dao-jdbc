package aplication;

import model.dao.DaoDepartmentJDBC;
import model.dao.DaoFactory;
import model.entidades.Department;

public class Program {

	public static void main(String[] args) {
		
		DaoDepartmentJDBC acessoDepart = DaoFactory.createDepartmentDao();
		
		Department dep = new Department(null, "Teste");
		acessoDepart.insert(dep);
		System.out.println(dep);
	
	}

}
