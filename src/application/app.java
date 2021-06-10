package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class app {

	public static void main(String[] args) {

		Department department = new Department(1, "Livros");
		Seller seller = new Seller(34, "Isaias Miranda", "isaias.analise@gmail.com", new Date(), 2500.60, department);

		System.out.println(seller);
	}

}
