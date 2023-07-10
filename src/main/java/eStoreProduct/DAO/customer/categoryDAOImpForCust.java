package eStoreProduct.DAO.customer;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import eStoreProduct.model.customer.input.Category;
import eStoreProduct.model.customer.input.CategoryRowMapper;

@Component
public class categoryDAOImpForCust implements CategoryDAOForCust{
	
	JdbcTemplate jdbcTemplate;
	private String SQL_INSERT_CATEGORY = "insert into slam_productCAtegories(prct_id,prct_title,prct_desc) values(?,?,?)";
	private String SQL_GET_TOP_CATGID = "select prct_id from slam_productCAtegories order by prct_id desc limit 1";
	private String SQL_GET_CATEGORIES="SELECT prct_title FROM slam_productCategories";

	@Autowired
	public categoryDAOImpForCust(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	// Add a new category to the database
	@Override
	public boolean addNewCategory(Category catg) {
		// Get the top category ID
		int c_id = jdbcTemplate.queryForObject(SQL_GET_TOP_CATGID, int.class);
		c_id = c_id + 1;
		System.out.println(c_id + "Category_id\n");

		// Insert the new category into the database
		return jdbcTemplate.update(SQL_INSERT_CATEGORY, c_id, catg.getPrct_title(), catg.getPrct_desc()) > 0;
	}

	// Get all categories from the database
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<>();

		// Retrieve all categories from the database
		categories = jdbcTemplate.query(SQL_GET_CATEGORIES, new CategoryRowMapper());

		return categories;
	}
}
