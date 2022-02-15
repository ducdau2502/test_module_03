package DAO;

import connection.MyConnection;
import model.Category;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductImpl implements IProduct {
    private static final String INSERT_PRODUCT_SQL = "insert into product (name_product, price, quantity, color, description, id_category) value (?,?,?,?,?,?);";
    private static final String DELETE_PRODUCT_SQL = "delete from product where id_product = ?;";
    private static final String UPDATE_PRODUCT_SQL = "update product set name_product = ?, price = ?, quantity = ?, color = ?, description = ?, id_category = ? where id_product = ?;";
    private static final String SELECT_ALL_PRODUCTS = "select id_product, name_product, price, quantity, color, description, id_category from product;";
    private static final String SELECT_ALL_CATEGORIES = "select * from category;";
    private static final String SELECT_PRODUCT_BY_ID = "select id_product, name_product, price, quantity, color, description, id_category from product\n" +
            "where id_product = ?;";
    MyConnection myConnection = new MyConnection();

    @Override
    public void insertProduct(Product product) throws SQLException {
        try {
            Connection connection = myConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getId_category());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product selectProduct(int id) {
        Product product = null;
        try {
            Connection connection = myConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id_product = rs.getInt("id_product");
                String name = rs.getString("name_product");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int id_category = rs.getInt("id_category");
                product = new Product(id_product, name, price, quantity, color, description, id_category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = myConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id_product = rs.getInt("id_product");
                String name = rs.getString("name_product");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                int id_category = rs.getInt("id_category");
                products.add(new Product(id_product, name, price, quantity, color, description, id_category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Category> selectAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            Connection connection = myConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id_category = rs.getInt("id_category");
                String name_category = rs.getString("name_category");
                categories.add(new Category(id_category, name_category));
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            statement.setInt(1, id);

            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getColor());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getId_category());
            statement.setInt(7, product.getId_product());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}
