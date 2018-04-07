/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;


public class DB {
    //local database
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
    //remote database 
    final String REMOTE_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String REMOTE_URL = "jdbc:mysql://localhost/test?"+"user=root&password=";
    //final String USERNAME = "";
    //final String PASSWORD = "";
    //remote database
    
    //These things for every db 
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    //private void essential_info(String tablename,DatabaseMetaData dbmd)
    public DB(String table_name,Boolean remote) {
        if(!remote){
        try {
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }
        }
        else{
            try {
                Class.forName(REMOTE_JDBC_DRIVER);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                conn = DriverManager.getConnection(REMOTE_URL);
            } catch (SQLException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println(""+ex);
            }
        }
        
        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {           
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "USERS", null);
            if(!rs.next())
            {
             createStatement.execute("create table "+table_name+ "(username varchar(100), password varchar(50),email varchar(200),aboutthisuser varchar(200))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }       
    }
    
    public void addUser(User user){
        try {
//            String sql = "insert into users values ('" + name + "','" + address + "')";
//            createStatement.execute(sql);
              String sql = "insert into users values (?,?,?,?)";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, user.get_username());
              preparedStatement.setString(2, user.get_password());
              preparedStatement.setString(3, user.get_email());
              preparedStatement.setString(4, user.get_aboutthisuser());
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a user hozzáadásakor");
            System.out.println(""+ex);
        }
    }
    
    
    public void showAllUsers(){
        String sql = "select * from users";
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            //DatabaseMetaData metadb = rs.getMetaData();
           // metadb.getmax
            while (rs.next()){
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println(name + " | " + address);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
    }
    
    public void showUsersMeta(){
        String sql = "select * from users";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int x = 1; x <= columnCount; x++){
                System.out.print(rsmd.getColumnName(x) + " | ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<User> getAllUsers(){
        String sql = "select * from users";
        ArrayList<User> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()){
            //    User actualUser = new User(rs.getString("name"),rs.getString("name"));
                //users.add(actualUser);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
      return users;
    }   
    public void write_file(String table_name){
       // The name of the file to open.
        String fileName = "db.ini";

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write("TABLE NAME:"+table_name);
          //  bufferedWriter.write("DRIVER:"+);
            /*bufferedWriter.write(" here is some text.");
            bufferedWriter.newLine();
            bufferedWriter.write("We are writing");
            bufferedWriter.write(" the text to the file.");*/

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
    public void exits_db_file(String table_name){
        // The name of the file to open.
        String fileName = "temp.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        //return true;
    }
}
