package application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class app {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter seller code: ");
		int id = sc.nextInt();

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("*** TEST 01 | FindById ***");

		Seller seller = sellerDao.findById(id);

		System.out.println(seller);

	}

}
