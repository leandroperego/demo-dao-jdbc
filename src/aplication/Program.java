package aplication;

import model.dao.Dao;
import model.dao.DaoFactory;
import model.entidades.Seller;

public class Program {

	public static void main(String[] args) {
		
		Dao<Seller> acessoSeller = DaoFactory.createSellerDao();
		
		Seller seller = acessoSeller.findById(6);
		System.out.println(seller);
		
	}

}
