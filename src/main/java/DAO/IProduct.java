package DAO;

import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProduct {
    public void insertProduct(Product product) throws SQLException;

    public Product selectProduct(int id);

    public List<Product> selectAllProducts();

    public List<Category> selectAllCategories();

    public boolean deleteProduct(int id) throws SQLException;

    public boolean updateProduct(Product product) throws SQLException;
}
