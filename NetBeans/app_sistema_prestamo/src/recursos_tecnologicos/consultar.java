package recursos_tecnologicos;

import conexion.conexion; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.logging.Level; 
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco

public class consultar {
    public static void main (String[] args) {
        conexion con = new conexion();

        // Cargar driver MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
            return; // si no carga el driver, salimos del programa
        }

        String sqlSelect = "SELECT * FROM recursos_tecnologicos";

        // try-with-resources -> cierra recursos automáticamente
        try (Connection conn = con.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("LISTADO DE RECURSOS TECNOLÓGICOS: \n");
            System.out.println("--------------------------------------------------------------");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("id_equipo") + " : " +
                    rs.getString("no_inventario") + " - " +
                    rs.getString("descripcion") + " - " +
                    rs.getDate("fecha_adquisicion") + " - " +
                    rs.getInt("valor") + " - " +
                    rs.getString("localizacion") + " - " +
                    rs.getString("estado_equipo")
                );
            }
            System.out.println("--------------------------------------------------------------");

        } catch (SQLException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
   