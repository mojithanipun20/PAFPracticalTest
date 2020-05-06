package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Doctor { 
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/doctor_details?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertdoctor(String id, String name, String gender, String specialist)
		 {
		 String output = "";
		 try
		 {
	 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for inserting."; 
	 }
	 
	 // create a prepared statement
	 String query = " insert into doctor(`doc_num`,`doc_ID`,`name`,`gender`,`specialist`)" + " values (?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, id);
		 preparedStmt.setString(3, name);
		 preparedStmt.setString(4, gender);
		 preparedStmt.setString(5, specialist);
		 
		// execute the statement
		 preparedStmt.execute();
	
		 String newdoctor = readDoctor();    
		 output = "{\"status\":\"success\", \"data\": \"" +  newdoctor + "\"}";
	 }
	 catch (Exception e)
	 {
		 output =  "{\"status\":\"error\", \"data\": \"Error while inserting the doctor.\"}"; 
		 System.err.println(e.getMessage());
	 }
	 return output;
	 }

	public String readDoctor()
	 {
	 String output = "";
	 try
	 {
		Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border=\"1\">"
					+ "<tr>"
					+ "<th>doc_ID</th>"
					+ "<th>name</th>"
					+ "<th>gender</th>"
					+ "<th>specialist</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th>"
					+ "</tr>";
			String query = "select * from doctor";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next())
			{
				 String doc_num = Integer.toString(rs.getInt("doc_num"));
				 String doc_ID = rs.getString("doc_ID");
				 String name = rs.getString("name");
				 String gender = rs.getString("gender");
				 String specialist = rs.getString("specialist");
	 
	 // Add into the html table
	 output += "<tr>";
	 output += "<td><input id='hiddoctorIDUpdate' name='hiddoctorIDUpdate' type='hidden' value='" + doc_num + "'>" + doc_ID + "</td>";
	 output += "<td>" + name + "</td>";
	 output += "<td>" + gender + "</td>";
	 output += "<td>" + specialist + "</td>";
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"+ 
			 "<td><input name='doctorID' type='button' value='Remove' class='btnRemove btn btn-danger' data-doct_num='" + doc_num + "'>" + "</td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
		 output = "Error while reading the doctors.";
		 System.err.println(e.getMessage());
	 }
	 	return output;
	 }
	
	public String updatedoctor(String num,String id, String name, String gender, String specialist)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
		 return "Error while connecting to the database for updating."; 
	 }
	 
	 // create a prepared statement
	 String query = "UPDATE doctor SET doc_ID=?,name=?,gender=?,specialist=? WHERE doc_num=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
		 preparedStmt.setString(1, id);
		 preparedStmt.setString(2, name);
		 preparedStmt.setString(3, gender);
		 preparedStmt.setString(4, specialist);
		 preparedStmt.setInt(5, Integer.parseInt(num));
		 
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 String newItems = readDoctor(); 
	 output =  "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
	 }
	 catch (Exception e)
	 {
		 output = "{\"status\":\"error\", \"data\": \"Error while updating the doctor.\"}";
		 System.err.println(e.getMessage()); 
	 }
	 return output;
	 }
	
	public String deletedoctor(String doc_num)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from doctor where doc_num=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(doc_num));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 String newItems = readDoctor();
	 output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
	 }
	 catch (Exception e)
	 {
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the doctor.\"}"; 
		 System.err.println(e.getMessage());
	 }
	 return output;
	 } 
	
	public static void main(String[] args) {
		new Doctor().insertdoctor("sdf", "safsaf", "123.23", "sdfadsfsadf");
		//new Item().deletedoctor("1");
	}

}
