package aplication;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.DaoSellerJDBC;
import model.entidades.Department;
import model.entidades.Seller;

public class Program {

	public static void main(String[] args) {
		
		DaoSellerJDBC bancoSeller = DaoFactory.createSellerDao();
		
		Seller seller = new Seller(null, "Inserindo dados", "inserindo@gmail.com", new Date(), 3000.00, new Department(2, null));
		bancoSeller.insert(seller);
		System.out.println("Inserido. Seller recebeu o id: " + seller.getId());
	}

}
