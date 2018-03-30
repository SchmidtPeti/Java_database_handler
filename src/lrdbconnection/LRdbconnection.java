
package lrdbconnection;

import java.sql.*;
public class LRdbconnection {
    final static String URL = "jdbc:derby:sampleDB;create=true";
    public static void main(String[] args) {
        Connection c = null;
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            c = DriverManager.getConnection(URL);          
        }
        catch(Exception e){
             System.err.println( e.getClass().getName() + ": " + e.getMessage() );
             System.exit(0);
      }
      System.out.println("Opened database successfully");
    }
    
}
