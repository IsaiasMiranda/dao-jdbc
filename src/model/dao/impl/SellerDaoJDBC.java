package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private static Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {

			String sql = "SELECT seller.*,department.Name as DepName\n" 
							+ "	FROM seller INNER JOIN department\n"
							+ "		ON seller.DepartmentId = department.Id\n" 
							+ "			WHERE seller.Id = ?";

			pst = conn.prepareStatement(sql);

			pst.setInt(1, id);

			rs = pst.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);

				Seller seller = instantiateSeller(rs, dep);

				return seller;
			}

		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}

		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getNString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("baseSalary"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		String sql = "SELECT seller.*,department.Name as DepName\n"
						+ "FROM seller INNER JOIN department\n"
						+ "ON seller.DepartmentId = department.Id\n"
						+ "WHERE DepartmentId = ?\n"
						+ "ORDER BY Name";
		
		try {
			pst = conn.prepareStatement(sql);
			
			pst.setInt(1, department.getId());
			
			rs = pst.executeQuery();
			
			List<Seller> listSeller = new ArrayList<>(); 
			
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) {

				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(rs, dep);
				listSeller.add(seller);
			}

			return listSeller;

		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeStatement(pst);
		}
	}

}
