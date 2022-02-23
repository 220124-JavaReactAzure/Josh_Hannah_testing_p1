package com.revature.weddingDreams.servlets;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.weddingDreams.models.Asset;
import com.revature.weddingDreams.models.User;
import com.revature.weddingDreams.models.Wedding;
import com.revature.weddingDreams.services.BetrothedService;
import com.revature.weddingDreams.services.EmployeeService;
import com.revature.weddingDreams.services.UserService;

public class BetrothedDash extends HttpServlet{

	private final BetrothedService betrothedService;
	private final UserService userService;
	private final EmployeeService employeeService;
	private final ObjectMapper mapper;	
	
	public BetrothedDash(UserService us, BetrothedService bs, EmployeeService es, ObjectMapper om) {
		this.userService = us;
		this.betrothedService = bs;
		this.employeeService = es;
		this.mapper = om; 
	}	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("betrothed-dash.jsp");
		view.forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String weddingID = UUID.randomUUID().toString();
		Wedding newWedding = new Wedding(weddingID, req.getParameter("wedding-date"), Double.parseDouble(req.getParameter("wedding-budget")),req.getParameter("venue"),req.getParameter("caterer"),req.getParameter("florist"),req.getParameter("photographer"),req.getParameter("musician"));	  
			
		// save wedding object
		try {
			boolean res = betrothedService.addWedding(newWedding); 
			if(res) {
				resp.setStatus(201);
				resp.getWriter().write("<p>Wedding added to database.</p>");
			}
			else {
				resp.setStatus(500);
				resp.getWriter().write("Unable to persist wedding to database");
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
		
		// update user object
		try {
			HttpSession httpSession = req.getSession(true);
			User user = (User) httpSession.getAttribute("authUser");
			user.setWedding(weddingID);

			boolean res = userService.updateUser(user); 
			if(res) {
				resp.setStatus(201);
				resp.getWriter().write("<p>Wedding added to database.</p>");
			}
			else {
				resp.setStatus(500);
				resp.getWriter().write("Unable to persist wedding to database");
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
		resp.sendRedirect("./betrothed-dash");
		
									  
									  
				
				
		
	}
	
}