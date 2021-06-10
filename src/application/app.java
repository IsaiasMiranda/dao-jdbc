package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class app {

	public static void main(String[] args) {

		Department department = new Department(1, "Livros");
		Seller seller = new Seller(34, "Isaias Miranda", "isaias.analise@gmail.com", new Date(), 2500.60, department);

		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println(seller);
	}

}
