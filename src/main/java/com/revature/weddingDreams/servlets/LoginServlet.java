package com.revature.weddingDreams.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.weddingDreams.exceptions.AuthenticationException;
import com.revature.weddingDreams.exceptions.InvalidRequestException;
import com.revature.weddingDreams.models.User;
import com.revature.weddingDreams.services.UserService;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	private final UserService userService;
	private final ObjectMapper mapper;

	public LoginServlet(UserService us, ObjectMapper om) {
		this.userService = us;
		this.mapper = om;	
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("login.html");
		view.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("*** The login doPost was called ***");
		User authenticatedUser;
		authenticatedUser = userService.authenticateUser(req.getParameter("email"), req.getParameter("pword"));
		System.out.println("*** Authenticated User");

		try {			
			HttpSession httpSession = req.getSession(true);
			httpSession.setAttribute("authUser", authenticatedUser);
			
			switch (authenticatedUser.getUserType()) {
			case 1: // user is employee
				resp.sendRedirect("./employee-dash"); break;
			case 2: // user is attendee
				resp.sendRedirect("./attendee-dash"); break;
			case 3: // user is betrothed
				resp.sendRedirect("./betrothed-dash"); break;
			default:
				resp.setStatus(500);
				resp.getWriter().write("Unable to set redirect path destination.");
		}
		} 
		catch (InvalidRequestException e) {
			System.out.println("*** InvaludeRequestException");
			resp.setStatus(400);
		}
		catch (AuthenticationException e) {
			System.out.println("*** AuthenticationException");
			resp.setStatus(401);
		}
		catch (Exception e) {
			System.out.println("*** Other Exception");
			e.printStackTrace();
			resp.setStatus(500);
		}
		
			
	}
}
