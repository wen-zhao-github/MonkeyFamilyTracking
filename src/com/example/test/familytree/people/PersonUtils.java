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
				familyMem.add(new Person(result.getInt("id"),result.getString("first_name"),result.getString("last_name"),result.getString("email")));
			}	
			/*for(Person temp:familyMem){
				System.out.println(temp.toString());
			}*/
			
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
		jDBCcleanup(conn,stat,null);
		
	}

	public Person getPerson(int id) {
		Connection conn = null;
		/*PreparedStatement stat = null;
		String query = "UPDATE webfamily_tracker.family SET first_name = ?, last_name = ?, email = ? WHERE id = ?";*/
		PreparedStatement stat = null;
		ResultSet result = null;
		Person person = null;
		String query = "SELECT * FROM webfamily_tracker.family WHERE id = ?";
		try {
			conn = dataSource.getConnection();
			stat = conn.prepareStatement(query);
			stat.setInt(1, id);
			result = stat.executeQuery();
			while (result.next()){
				person = new Person(result.getInt("id"),result.getString("first_name"),result.getString("last_name"),result.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jDBCcleanup(conn, stat, result);
		return person;
	}

	private void jDBCcleanup(Connection conn, PreparedStatement stat, ResultSet result) {
		if (result != null){
			try {
				conn.close();
				stat.close();
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void updateFamilyMem(int id, Person person) {
		Connection conn = null;
		PreparedStatement stat = null;
		String query = "UPDATE webfamily_tracker.family SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
		
		try {
			conn = dataSource.getConnection();
			stat = conn.prepareStatement(query);
			stat.setString(1, person.getFirstName());
			stat.setString(2, person.getLastName());
			stat.setString(3, person.getEmail());
			stat.setInt(4, id);
			stat.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jDBCcleanup(conn, stat, null);
	}

	public void deleteById(int id) {
		Connection conn = null;
		PreparedStatement stat = null;
		String query = "DELETE FROM webfamily_tracker.family WHERE id = ?";
		
		try {
			conn = dataSource.getConnection();
			stat = conn.prepareStatement(query);
			stat.setInt(1, id);
			stat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jDBCcleanup(conn, stat, null);
		
	}

}
