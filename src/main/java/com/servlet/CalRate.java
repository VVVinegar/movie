package com.servlet;

import java.io.IOException;
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		int movieid = Integer.parseInt(request.getParameter("movieid"));
		int userid = Integer.parseInt(request.getParameter("userid"));
		String genere = request.getParameter("genere");
		System.out.println(genere);
		Service service = new Service();
		try {
			int rs = service.calRate(userid, movieid);
			if( rs>0 ) {
				System.out.println("点赞成功");
				request.getRequestDispatcher("/showFilms?genere="+genere).forward(request,response);
			}
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
