package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class app {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("*** TEST 01 | FindById ***");

		Seller seller = sellerDao.findById(2);

		System.out.println(seller);

		System.out.println("\n*** TEST 02 | Seller FindByIdDepartment ***");

		Department department = new Department(2, null);

		List<Seller> listSeller = sellerDao.findByDepartment(department);

		for (Seller list : listSeller) {
			System.out.println(list);
		}
		
		System.out.println("\n*** TEST 03 | Seller FindAll ***");

		List<Seller> sellerFindAll = sellerDao.findAll();
		
		for (Seller seller2 : sellerFindAll) {
			System.out.println(seller2 + "\n");
		}
	}

}
