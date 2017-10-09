package com.example.test.familytree.people;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/webfamily_tracker" )
	private DataSource dataSource;
	private PersonUtils personUtils;
	public PersonServlet(){
		
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		personUtils = new PersonUtils(dataSource);
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String command = req.getParameter("command");
		if (command == null){
			command = "LIST";
		}
		switch (command){
		case "LIST":
			showFamilyList(req, resp);
			break;
		case "ADD":
			addFamilyMem(req,resp);
			showFamilyList(req, resp);
			break;
		case "LOAD":
			loadPerson(req, resp);
			break;
		case "UPDATE":
			updateFamilyMem(req,resp);
			showFamilyList(req, resp);
			break;
		case "DELETE":
			delete(req, resp);
			showFamilyList(req, resp);
			break;
		default:
			showFamilyList(req, resp);
		
		}
		
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("personid"));
		personUtils.deleteById(id);
		
	}

	private void loadPerson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("personid")) ;
		System.out.println("id: "+id);
		Person person = personUtils.getPerson(id);
		
		req.setAttribute("theCurrentPerson", person);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/update-person.jsp");
		dispatcher.forward(req, resp);
		
	}

	private void updateFamilyMem(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("personid"));
		System.out.println("id: "+id);
		Person person = new Person(req.getParameter("firstname"),req.getParameter("lastname"),req.getParameter("email"));
		personUtils.updateFamilyMem(id,person);			
	}

	private void addFamilyMem(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		System.out.println(firstName+lastName+email);
		Person newPerson = new Person(firstName,lastName,email);
		personUtils.addNewPerson(newPerson);
		
	}

	private void showFamilyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Person> familyMem = personUtils.getPersonList();
		req.setAttribute("familyMem", familyMem);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/family-member-list.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	

}
