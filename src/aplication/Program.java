package aplication;

import model.dao.DaoFactory;
import model.dao.DaoSellerJDBC;
import model.entidades.Seller;

public class Program {

	public static void main(String[] args) {
		
		DaoSellerJDBC bancoSeller = DaoFactory.createSellerDao();
		
		Seller seller = bancoSeller.findById(1);
		seller.setName("Mudado Teste");
		bancoSeller.update(seller);
		
	}

}
