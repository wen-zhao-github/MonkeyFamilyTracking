package com.example.test.familytree.people;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class PersonUtils {
	@Resource(name="jdbc/webfamily_tracker" )
	private DataSource dataSource;

	public PersonUtils(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	public ArrayList<Person> getPersonList() {
		Connection conn = null;
		Statement stat = null;
		ResultSet result = null;
		String query = "SELECT * FROM webfamily_tracker.family;";
		ArrayList<Person> familyMem = new ArrayList<>();
		
		try{
			conn = dataSource.getConnection();
			stat = conn.createStatement();
			result = stat.executeQuery(query);			
			while(result.next()){
				familyMem.add(new Person(result.getString("first_name"),result.getString("last_name"),result.getString("email")));
			}	
			for(Person temp:familyMem){
				System.out.println(temp.toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			cleanupjdbc(conn,stat,result);
		}
		return familyMem; 
	}
	private void cleanupjdbc(Connection conn, Statement stat, ResultSet result)  {
		try{
			conn.close();
			stat.close();
			result.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void addNewPerson(Person newPerson) {
		Connection conn = null;
		PreparedStatement stat = null;
		//ResultSet result = null;
		String query = "INSERT INTO webfamily_tracker.family (first_name,last_name,email) VALUES(?,?,?)";
		
		try {
			conn = dataSource.getConnection();
			stat = conn.prepareStatement(query);
			stat.setString(1, newPerson.getFirstName());
			stat.setString(2, newPerson.getLastName());
			stat.setString(3, newPerson.getEmail());
			stat.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
