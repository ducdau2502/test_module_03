<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <title>View Products</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
          integrity="sha512-Fo3rlrZj/k7ujTnHg4CGR2D7kSs0v4LLanw2qksYuRlEzO+tcaEPQogQ0KaoGN26/zrn20ImR1DfuLWnOo7aBA=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<h1>All City</h1>

<button type="button" class="btn btn-outline-primary" style="margin: 20px">
    <a href="${pageContext.request.contextPath}/product?action=createGet">
        <i class="fa-solid fa-plus"></i> Create new product</a>
</button>
<form class="nav-link d-flex" style="margin: 0" action="/product?action=search" method="post">
<input class="form-control mr-2" type="text" size="25" placeholder="Search" name="search">
<button class="btn btn-light ml-2" type="submit">
    <i class="fa-solid fa-magnifying-glass"></i> Search
</button>
</form>
<c:if test="${requestScope['products'].isEmpty()}">
    <h2 style="color: red">Không có product nào</h2>
</c:if>
<c:if test="${requestScope['products'].isEmpty() == false}">
<table>
    <tr>
        <td>ID</td>
        <td>Name</td>
        <td>Price</td>
        <td>Quantity</td>
        <td>Color</td>
        <td>Category</td>
        <td colspan="2">Action</td>
    </tr>
    <c:forEach items="${products}" var="product">
        <tr>
            <td>${product.getId_product()}</td>
            <td>${product.getName()}</td>
            <td>${product.getPrice()}</td>
            <td>${product.getQuantity()}</td>
            <td>${product.getColor()}</td>
            <td>
                <c:forEach items="${categories}" var="categories">
                    <c:if test="$${product.getId_category() == categories.getId_category()}">
                        ${categories.getName_category()}
                    </c:if>
                </c:forEach>
            </td>
            <td>
                <button type="button" class="btn btn-info">
                    <a style="color: white; text-decoration: none"
                       href="/product?action=editGet&id=${product.getId_product()}"><i
                            class="fa-solid fa-pencil"></i></a>
                </button>
            </td>
            <td>
                <button type="button" class="btn btn-danger">
                    <a style="color: white; text-decoration: none"
                       href="/product?action=delete&id=${product.getId_product()}"><i
                            class="fa-solid fa-trash"></i></a>
                </button>
            </td>
        </tr>
    </c:forEach>
</table>
</c:if>
</body>
</html>