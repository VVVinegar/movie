package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Movie;
import com.service.Service;

public class ShowFilms extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ShowFilms() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");
        String genere = request.getParameter("genere");
        int page = Integer.parseInt(request.getParameter("page"));

        Service service = new Service();
        try {
            List<Movie> movies = service.findMoviesByGenere(genere, page);
            request.setAttribute("movieslist", movies);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
