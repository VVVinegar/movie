package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

/**
 * Servlet implementation class CalRate
 */

public class CalRate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CalRate() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");

        int movieID = Integer.parseInt(request.getParameter("movieID"));
        int useID = Integer.parseInt(request.getParameter("userID"));

        Service service = new Service();
        try {
            int[] rs = service.calRate(useID, movieID);
            int result = rs[0];
            int like = rs[1];
            PrintWriter out = response.getWriter();  // 打开response的输入流
            if (result > 0) {
                out.print(like);  // 写入信息到response中
            } else {
                out.print(999);  // 999代表数据库操作失败
            }
            out.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
