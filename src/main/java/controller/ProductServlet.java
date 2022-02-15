package controller;

import DAO.ProductImpl;
import Regex.Validate;
import model.Category;
import model.Product;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    ProductImpl productImpl = new ProductImpl();
    Validate validate = new Validate();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            action(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            action(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "createGet":
                creatGet(request, response);
                break;
            case "createPost":
                creatPost(request, response);
                break;
                case "editGet":
                    editGet(request, response);
                    break;
                case "editPost":
                    editPost(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "search":
                    find(request, response);
                    break;
            default:
                displayAll(request, response);
        }
    }

    private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String search = request.getParameter("search");
        List<Product> products = productImpl.selectAllProducts();
        List<Product> productsOfSearch = new ArrayList<>();
        for (Product product : products) {
            if (validate.validateNameProduct(search, product.getName())) {
                productsOfSearch.add(product);
            }
        }
        request.setAttribute("products", productsOfSearch);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("view.jsp");
        requestDispatcher.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id_product = Integer.parseInt(request.getParameter("id"));
        productImpl.deleteProduct(id_product);
        displayAll(request, response);
    }

    private void editPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id_product = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int id_category = Integer.parseInt(request.getParameter("category"));
        Product product = new Product(id_product, name, price, quantity, color, description, id_category);
        boolean check = productImpl.updateProduct(product);
        request.setAttribute("check", check);
        displayAll(request, response);
    }

    private void editGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_product = Integer.parseInt(request.getParameter("id"));
        Product productEdit = productImpl.selectProduct(id_product);
        request.setAttribute("productEdit", productEdit);
        List<Category> categories = productImpl.selectAllCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit.jsp");
        requestDispatcher.forward(request, response);
    }

    private void creatGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = productImpl.selectAllCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create.jsp");
        requestDispatcher.forward(request, response);
    }

    private void creatPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int id_category = Integer.parseInt(request.getParameter("category"));
        Product product = new Product(name, price, quantity, color, description, id_category);
        productImpl.insertProduct(product);
        displayAll(request, response);
    }

    private void displayAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productImpl.selectAllProducts();
        request.setAttribute("products", products);
        List<Category> categories = productImpl.selectAllCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("view.jsp");
        requestDispatcher.forward(request, response);
    }
}
