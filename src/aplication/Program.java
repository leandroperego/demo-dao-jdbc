package aplication;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoDepartmentJDBC;
import model.dao.DaoFactory;
import model.entidades.Department;

public class Program {

	public static void main(String[] args) {
		
		DaoDepartmentJDBC acessoDepart = DaoFactory.createDepartmentDao();
		List<Department> lista = new ArrayList<>();
		lista = acessoDepart.findAll();
		
		lista.forEach(System.out::println);
	}

}
