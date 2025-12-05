/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import models.Product;

/**
 *
 * @author luliou
 */
@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("username") == null) {
            response.sendRedirect("auth");
            return;
        }

        String menu = request.getParameter("menu");

        if(menu == null) { //view menu
            //get all data from database
            request.setAttribute("title", "Daftar Produk");
            
            ArrayList<Product> prods = new Product().get();
            request.setAttribute("list", prods);
            
            request.getRequestDispatcher("view_product.jsp").forward(request, response);
        } else if ("add".equals(menu)) {
            request.setAttribute("title", "Tambah Produk");
            request.getRequestDispatcher("form_product.jsp").forward(request, response);
        } else if ("edit".equals(menu)) {
            request.setAttribute("title", "Edit Produk");
            request.setAttribute("action", "?id=" + request.getParameter("id"));
            Product p = new Product().find(request.getParameter("id"));
            if (p == null) {
                response.sendRedirect("product");
                return;
            }
            request.setAttribute("product", p);

            request.getRequestDispatcher("form_product.jsp").forward(request, response);
        } else if ("custom".equals(menu)) {
            request.setAttribute("title", "Daftar Produk (custom query)");
            ArrayList<ArrayList<Object>> prods = new Product().query("SELECT * FROM product WHERE name='iphone'");
            request.setAttribute("list", prods);
            
            request.getRequestDispatcher("view_rdbms.jsp").forward(request, response);
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (id == null) { //insert data
            
            Product p = new Product();
            p.setName(request.getParameter("name"));
            p.setPrice(Double.parseDouble(request.getParameter("price")));
            p.insert();
            request.getSession().setAttribute("msg", "Produk berhasil ditambah");
            
        } else if (action == null) { //update data
            
            
            Product p = new Product ();
            p.setId(Integer.parseInt(id));
            p.setName(request.getParameter("name"));
            p.setPrice (Double.parseDouble(request.getParameter("price"))); p.update();
            request.getSession().setAttribute("msg", "Produk berhasil diubah");

        } else if ("del".equals(action)) { //delete data
            
            Product p = new Product().find(request.getParameter("id")); if (p != null) p.delete();
            request.getSession().setAttribute("msg", "Produk berhasil dihapus") ;
        }
        response.sendRedirect("product");
       
    }

 

}
