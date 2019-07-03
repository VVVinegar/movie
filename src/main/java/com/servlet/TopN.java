package com.servlet;

import com.alibaba.fastjson.JSON;
import com.bean.Movie;
import com.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopN extends HttpServlet {
    public TopN() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");

        Service service = new Service();

        try {
            List<Integer> movieIDs = service.getTopNMoviesID();

            List<Movie> movies = new ArrayList<Movie>();

            for (int i = 0; i < movieIDs.size(); i++) {
                int movieID = movieIDs.get(i);
                Movie movie = service.findMoviesByID(movieID);
                movies.add(movie);
            }

            String jsonStr = JSON.toJSON(movies).toString();

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
