package com.revature.weddingDreams.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.weddingDreams.models.User;
import com.revature.weddingDreams.services.UserService;

public class RegisterServlet extends HttpServlet {

  private final UserService userService;
  private final ObjectMapper mapper;


  public RegisterServlet(UserService us, ObjectMapper om) {
    this.userService = us;
    this.mapper = om; 
  }
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("register.html");
		view.forward(req, resp);
	
	}

  
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  int userTypeAsInt = Integer.parseInt(req.getParameter("type"));
	  String redirectDestination = new String();
	  
	  User newUser = new User(req.getParameter("email"), req.getParameter("pword"), userTypeAsInt, null, false, null, null);	
	  HttpSession httpSession = req.getSession(true);
	  httpSession.setAttribute("authUser", newUser);
		
	  try {
		  	boolean res = userService.addUser(newUser);
			if(res) {
				 switch (userTypeAsInt) {
					case 1: // user is employee
						redirectDestination = "./employee-dash"; break;
					case 2: // user is attendee
						redirectDestination = "./attendee-dash"; break;
					case 3: // user is betrothed
						redirectDestination = "./betrothed-dash";break;	
					default:
						resp.setStatus(500);
						resp.getWriter().write("Unable to set redirect path.");
				  }
				
			  	resp.setStatus(201);			  				
			  	resp.sendRedirect(redirectDestination);
			} 	
		  	else {
				resp.setStatus(500);
				resp.getWriter().write("Unable to persist user to database.");
			}
	  }
	  catch (StreamReadException | DatabindException e) {
		  resp.setStatus(400);
		  resp.getWriter().write("JSON threw an exception");
		  e.printStackTrace();
	  }
	  catch (Exception e) {
		  resp.setStatus(500);
		  resp.getWriter().write("Some other exception");
		  e.printStackTrace();
	  }
	  	
  }
}