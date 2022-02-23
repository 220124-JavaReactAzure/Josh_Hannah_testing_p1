package com.revature.weddingDreams.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.revature.weddingDreams.models.Wedding;
import com.revature.weddingDreams.services.AttendeeService;
import com.revature.weddingDreams.services.BetrothedService;
import com.revature.weddingDreams.services.UserService;

public class AttendeeDash extends HttpServlet {

	private final UserService userService;
	private final BetrothedService betrothedService;
	private final AttendeeService attendeeService;
	private final ObjectMapper mapper;
	
	public AttendeeDash(UserService us, BetrothedService bs, AttendeeService as, ObjectMapper os) {
		this.userService = us;
		this.betrothedService = bs;
		this.attendeeService = as;
		this.mapper = os;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("attendee-dash.jsp");
		view.forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 try {
			HttpSession httpSession = req.getSession(true);
			User user = (User) httpSession.getAttribute("authUser");
			
			// get wedding ID, want to leave room open to allow for more than one wedding per day if we have time
			List<Wedding> weddingList = betrothedService.getAllWeddings();
			boolean weddingExists = false;
			for(Wedding wedding:weddingList) {
				if(wedding.getWeddingDate().trim().equals(req.getParameter("wedding-date").replace("-0", "-").trim())) {	
					user.setWedding(wedding.getWeddingID());
					weddingExists = true;
				}
			}
			if(!weddingExists) {
				System.out.println("*** It did not work! ***");
				// throw wedding not found exception
			}
			
			if(req.getParameter("attendee-meal") != null) {
				user.setMealOptionsAttendee( req.getParameter("attendee-meal"));
			}
			if(req.getParameter( "plus-one-choice") != null) {
				user.setPlusOne(true);
				user.setMealOptionsPlusOne( req.getParameter("plus-one-meal"));
			}
			if(req.getParameter("remove-plus-one") != null) {
				user.setPlusOne(false);
				user.setMealOptionsPlusOne(null);
			}
			
			boolean res = userService.updateUser(user); 
			if(res) {
				resp.setStatus(201);
				resp.getWriter().write("<p>Attendee meal choice(s) added to database.</p>");
			}
			else {
				resp.setStatus(500);
				resp.getWriter().write("Unable to update attendee meal choice in database");
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
		resp.sendRedirect("./attendee-dash");

		
	}
}
