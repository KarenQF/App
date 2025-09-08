package conexion; 

// Realizdo por: Karen Lizeth Quintero Franco

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    Connection con;

    public conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_prestamo", "root", "Kquintero92*");
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println("No Conectado");
        }
    }
    public Connection getConection(){
        return con;
    }
}   
