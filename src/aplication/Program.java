package aplication;

import java.util.ArrayList;
import java.util.List;

import model.dao.Dao;
import model.dao.DaoFactory;
import model.dao.DaoSellerJDBC;
import model.entidades.Department;
import model.entidades.Seller;

public class Program {

	public static void main(String[] args) {
		
		DaoSellerJDBC bancoSeller = DaoFactory.createSellerDao();
		
		List<Seller> lista = new ArrayList<Seller>();
		lista = bancoSeller.findAll();
		
		lista.forEach(System.out::println);
		
	}

}
