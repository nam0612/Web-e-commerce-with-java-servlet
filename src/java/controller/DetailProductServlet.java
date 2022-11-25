/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.FeedbackDAO;
import dal.ProductDAO;
import entity.Feedback;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Southern Alula
 */
@WebServlet(name = "DetailProductServlet", urlPatterns = {"/detail"})
public class DetailProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DetailProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailProductServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductDAO dao = new ProductDAO();
        FeedbackDAO fd = new FeedbackDAO();
        Product pro = dao.getProductById(id);
        
        List<Feedback> listF = fd.getFeedbackById(id);    
        List<Product> list = dao.getProductByBrandId(pro.getBrand_id());
        request.setAttribute("products", list);
        request.setAttribute("detail", pro);
        request.setAttribute("listfb", listF);
        request.setAttribute("star", fd.getAvgRating(id));
        request.getRequestDispatcher("detailProduct.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String review = request.getParameter("review");
        String rating_raw = request.getParameter("rating");
        String id_raw = request.getParameter("id");
        int id_pro = Integer.parseInt(request.getParameter("id"));
        User account = (User) session.getAttribute("account");
        FeedbackDAO fd = new FeedbackDAO();
        int rating, id;
        try {
            rating = Integer.parseInt(rating_raw);
            id = Integer.parseInt(id_raw);
            fd.insertFeedback(account, review, rating, id);
        } catch (Exception e) {
            System.out.println(e);
        }
        response.sendRedirect("detail?id=" + id_pro);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
