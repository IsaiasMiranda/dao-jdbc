package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement pst = null;
		
		String sql = "INSERT INTO ismtech.seller (Name, Email, BirthDate, BaseSalary, DepartmentId)\n"
				+ "	VALUES (?, ?, ?, ?, ?)";	

		try {

			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, seller.getName());
			pst.setString(2, seller.getEmail());
			pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pst.setDouble(4, seller.getBaseSalary());
			pst.setInt(5, seller.getDepartment().getId());

			int linesAffected = pst.executeUpdate();

			if (linesAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error, no lines affected!!");
			}

		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement pst = null;
		
		String sql = "UPDATE seller\n"
						+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n"
						+ "WHERE Id = ?";
		try {
			pst = conn.prepareStatement(sql);

			pst.setString(1, seller.getName());
			pst.setString(2, seller.getEmail());
			pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pst.setDouble(4, seller.getBaseSalary());
			pst.setInt(5, seller.getDepartment().getId());
			pst.setInt(6, seller.getId());

			pst.executeUpdate();
		

		} catch (SQLException e) {
			throw new DbException("Error: " + e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}

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
		PreparedStatement pst = null;
		ResultSet rs = null;
				
		String sql = "SELECT seller.*,department.Name as DepName\n"
						+ "FROM seller INNER JOIN department\n"
						+ "ON seller.DepartmentId = department.Id\n"
						+ "ORDER BY Name";
		
		try {
			pst = conn.prepareStatement(sql);
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
			throw new DbException("Error " + e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);
		}	
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
