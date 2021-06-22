package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class app {

	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		/*
		 * System.out.println("*** TEST 01 | FindById ***");
		 * 
		 * Seller seller = sellerDao.findById(2);
		 * 
		 * System.out.println(seller);
		 * 
		 * System.out.println("\n*** TEST 02 | Seller FindByIdDepartment ***");
		 * 
		 * Department department = new Department(2, null);
		 * 
		 * List<Seller> listSeller = sellerDao.findByDepartment(department);
		 * 
		 * for (Seller list : listSeller) { System.out.println(list); }
		 * 
		 * System.out.println("\n*** TEST 03 | Seller FindAll ***");
		 * 
		 * List<Seller> sellerFindAll = sellerDao.findAll();
		 * 
		 * for (Seller seller2 : sellerFindAll) { System.out.println(seller2 + "\n"); }
		 */

		System.out.println("\n*** TEST 04 | Seller inserted ***");

		Department dep = new Department(2, null);

		Seller seller = new Seller(null, "Isaias Santos de Miranda", "isaias.ead@gmail.com", sdf.parse("12/12/1973"), 3600.60, dep);

		sellerDao.insert(seller);

		System.out.println("Inserted!!, ID: " + seller.getId());
	}
}
