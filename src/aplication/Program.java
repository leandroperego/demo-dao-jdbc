package aplication;

import model.dao.Dao;
import model.dao.DaoFactory;
import model.entidades.Seller;

public class Program {

	public static void main(String[] args) {
		
		Dao<Seller> conexSeller = DaoFactory.createSellerDao();
		
		Seller seller = conexSeller.findById(3);
		
		System.out.println(seller);
		
	}

}
