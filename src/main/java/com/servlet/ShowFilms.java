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
    private static final int PAGE_SIZE = 3;

    public ShowFilms() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");

        String genere = request.getParameter("genere");
        int page = Integer.parseInt(request.getParameter("page"));

        // 页码最小为1
        if (page <= 1) {
            page =1;
        }

        Service service = new Service();
        try {
            List<Movie> movies = service.findMoviesByGenere(genere, page, PAGE_SIZE);
            // 好蠢的办法：如果下一页没数据，就找上一页的数据
            if (movies.isEmpty()) {

                System.out.println("null");
                page--;
                movies = service.findMoviesByGenere(genere, page, PAGE_SIZE);
            }
            request.setAttribute("movieslist", movies);
            request.setAttribute("page", page);
            request.setAttribute("genere", genere);
            request.setAttribute("pageSize", PAGE_SIZE);
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
