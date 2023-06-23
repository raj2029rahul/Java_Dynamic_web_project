package com.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail = request.getParameter("username");
		String upwd = request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher rd=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false","ee212725","password");
			PreparedStatement ps=con.prepareStatement("select * from users where uemail=? and upwd=?");
			ps.setString(1, uemail);
			ps.setString(2, upwd);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				rd=request.getRequestDispatcher("index.jsp");
			}else {
				request.setAttribute("status", "failed");
				rd=request.getRequestDispatcher("login.jsp");
			}
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
