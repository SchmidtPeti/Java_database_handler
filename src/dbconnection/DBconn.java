/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBconn {
    final String LOCAL_JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String LOCAL_URL = "jdbc:derby:sampleDB;create=true";
    final String REMOTE_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private Connection conn = null;
    private Statement create_statment = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public DBconn(String name_of_the_localDatabase){
        try{
        Class.forName(LOCAL_JDBC_DRIVER);
        conn = DriverManager.getConnection(LOCAL_URL);
        create_statment = conn.createStatement();
        }
        catch(Exception ex){
            System.out.println("Something went wrong during the create Databse");
        }
    }
    public DBconn(String server,String username,String password){
        
    }
    public void add_data(User user){
        String sql = "insert into users values(?,?,?,?)"; 
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.get_username());
            preparedStatement.setString(2, user.get_password());
            preparedStatement.setString(3, user.get_email());
            preparedStatement.setString(4, user.get_aboutthisuser());
            //date making ...
        } catch (SQLException ex) {
            Logger.getLogger(DBconn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
