package com.revature.weddingDreams.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.weddingDreams.models.Asset;
import com.revature.weddingDreams.services.EmployeeService;

public class EmployeeDash extends HttpServlet {

	private final EmployeeService employeeService;
	private final ObjectMapper mapper;
	
	
	public EmployeeDash(EmployeeService es, ObjectMapper om) {
	  this.employeeService = es;
	  this.mapper = om; 
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("employee-dash.jsp");
		view.forward(req, resp);
	}
	@Override // Adds asset to data base
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String assetID = UUID.randomUUID().toString();
		Asset newAsset = new Asset(assetID, req.getParameter("name"), req.getParameter("address"), Double.parseDouble(req.getParameter("price")), Integer.parseInt(req.getParameter("type")));
		
		try {
			boolean res = employeeService.addAsset(newAsset);
			if(res) {
				resp.setStatus(201);
				resp.getWriter().write("<p>Asset added to database. </p><a href=\"employee-dash\">Click to add another asset</a>");
				
				// add link to logout
			}
			else {
				resp.setStatus(500);
				resp.getWriter().write("Unable to persist asset to database");
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