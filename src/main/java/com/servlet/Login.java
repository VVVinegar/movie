package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.Movie;
import com.bean.User;
import com.service.Service;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		Service sv= new Service();
		User user = null;
		try {
			user = sv.login(username, password);
		}catch  (SQLException e){
			e.printStackTrace();
            System.out.println("网络异常！");
		}
		if(user != null) {
			System.out.println("登陆成功");
			request.setAttribute("error", null);
			request.setAttribute("user", user);
			session.setAttribute("user", user);
			try {
				List<Movie> movieslist = sv.findMoviesByGenere("Drama", 1);
				request.setAttribute("movieslist",movieslist);
                Cookie cookie = new Cookie("JSESSIONID",session.getId());
                cookie.setMaxAge(60*30);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
                request.getRequestDispatcher("/index.jsp").forward(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			request.setAttribute("error", 1);
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset = UTF-8");
			request.getRequestDispatcher("/login.jsp").forward(request,response);
			//response.setHeader("refresh","3;url=recommend3_war_exploded/login.jsp");
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
