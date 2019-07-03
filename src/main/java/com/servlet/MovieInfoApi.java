package com.servlet;

import com.bean.Movie;
import com.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class MovieInfoApi extends HttpServlet {
    public MovieInfoApi() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset = UTF-8");

        int movieID = Integer.parseInt(request.getParameter("movieID"));

        Service service = new Service();

        try {
            Movie movie = service.findMoviesByID(movieID);

            String jsonStr = "{\"id\":\""+movie.getMovieid() +"\",\"name\":\""+movie.getName()+"\"}";

            PrintWriter out = response.getWriter();  // 打开response的输入流
            out.print(jsonStr);  // 写入信息到response中
            out.close();
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
