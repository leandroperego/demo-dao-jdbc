package aplication;

import java.util.List;

import model.dao.Dao;
import model.dao.DaoFactory;
import model.entidades.Department;
import model.entidades.Seller;

public class Program {

	public static void main(String[] args) {
		
		Dao<Seller> acessoSeller = DaoFactory.createSellerDao();
		
		List<Seller> lista = acessoSeller.findBy(new Department(1, null));
		
		lista.forEach(s -> System.out.println(s));
		
		
		
	}

}
