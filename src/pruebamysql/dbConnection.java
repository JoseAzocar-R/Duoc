/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebamysql;
import java.sql.*;

public class dbConnection {
   static String url="jdbc:mysql://localhost:3306/WestBank";
   static String user="root";
   static String pass="Plsqldeveloper9i.";
    
    public static Connection conectar()
    {
        Connection con=null;
        try
        {
         con=DriverManager.getConnection(url,user,pass);
         System.out.println("Conexión exitosa");
        }catch (SQLException e)
  
        {
             System.out.println("Error en la conexión...");
          e.printStackTrace();
        }
                 
        return con;
    }
}
