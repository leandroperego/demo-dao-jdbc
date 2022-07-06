package aplication;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.implem.SellerDaoJDBC;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Qual id irá excluir: ");
		int id = sc.nextInt();
		SellerDaoJDBC acessoBDSeller = DaoFactory.createSellerDao();
		acessoBDSeller.deleteById(id);
		
		sc.close();
	}

}
