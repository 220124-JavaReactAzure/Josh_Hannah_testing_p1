package com.revature.weddingDreams.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.revature.weddingDreams.daos.AttendeeDAO;
import com.revature.weddingDreams.daos.BetrothedDAO;
import com.revature.weddingDreams.daos.EmployeeDAO;
import com.revature.weddingDreams.daos.UserDAO;
import com.revature.weddingDreams.services.AttendeeService;
import com.revature.weddingDreams.services.BetrothedService;
import com.revature.weddingDreams.services.EmployeeService;
import com.revature.weddingDreams.services.UserService;
import com.revature.weddingDreams.servlets.AttendeeDash;
import com.revature.weddingDreams.servlets.BetrothedDash;
import com.revature.weddingDreams.servlets.EmployeeDash;
import com.revature.weddingDreams.servlets.LoginServlet;
import com.revature.weddingDreams.servlets.RegisterServlet;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate5Module());	
	
		
		UserDAO userDAO = new UserDAO();
		UserService userService = new UserService(userDAO);
		
		EmployeeDAO employeeDAO = new EmployeeDAO();
		EmployeeService employeeService = new EmployeeService(employeeDAO);
		AttendeeDAO attendeeDAO = new AttendeeDAO();
		AttendeeService attendeeService = new AttendeeService(attendeeDAO);
		BetrothedDAO betrothedDAO = new BetrothedDAO();
		BetrothedService betrothedService = new BetrothedService(betrothedDAO);
		
		LoginServlet loginServlet = new LoginServlet(userService, mapper);
		RegisterServlet registerServlet = new RegisterServlet(userService, mapper);
		EmployeeDash employeeDash = new EmployeeDash(employeeService, mapper);
		AttendeeDash attendeeDash = new AttendeeDash(userService, betrothedService, attendeeService, mapper);
		BetrothedDash betrothedDash = new BetrothedDash(userService, betrothedService, employeeService, mapper);
		
		
		ServletContext context = sce.getServletContext();
		context.addServlet("LoginServlet", loginServlet).addMapping("/login");
		
		context.addServlet("RegisterServlet", registerServlet).addMapping("/register");
		
		context.addServlet("EmployeeDash", employeeDash).addMapping("/employee-dash");
		context.addServlet("AttendeeDash", attendeeDash).addMapping("/attendee-dash");
		context.addServlet("BetrothedDash", betrothedDash).addMapping("/betrothed-dash");
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContextListener.super.contextDestroyed(sce);
	}

}
