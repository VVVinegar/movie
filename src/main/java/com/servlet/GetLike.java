package com.servlet;

import com.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class GetLike extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public GetLike() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");

        int movieID = Integer.parseInt(request.getParameter("movieID"));
        int useID = Integer.parseInt(request.getParameter("userID"));

        Service service = new Service();
        try {
            int like = service.getLike(useID, movieID);
            PrintWriter out = response.getWriter();  // 打开response的输入流
            out.print(like);  // 写入信息到response中
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
