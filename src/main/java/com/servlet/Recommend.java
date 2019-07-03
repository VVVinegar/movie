package com.servlet;

import com.bean.Movie;
import com.bean.UserScore;
import com.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class Recommend extends HttpServlet {
    public Recommend() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");

        String userID = request.getParameter("userID");

        if ("" == userID) {
            // todo 没登录先推荐userID=1的
            userID = "1";
        }

        int userIDInt = Integer.parseInt(userID);

        Service service = new Service();

        try {
            List<UserScore> userScores = service.getRecommendFromCalculate(userIDInt);

            request.setAttribute("userScores", userScores);
            request.getRequestDispatcher("/recommend.jsp").forward(request, response);
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
