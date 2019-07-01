package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bean.Rate2;
import com.bean.User;
import com.service.Service;

/**
 * Servlet implementation class userInfo
 */
@WebServlet("/userInfo")
public class userInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public userInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Service sv= new Service();
		try {
			List<Rate2> rates = sv.findratesByUserid(user.getUserid());
			request.setAttribute("rateslist", rates);
			request.getRequestDispatcher("/user.jsp").forward(request,response);
			
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
