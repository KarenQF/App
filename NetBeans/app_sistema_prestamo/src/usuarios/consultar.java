package usuarios;

import conexion.conexion; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.logging.Level; 
import java.util.logging.Logger;

// Realizado por: Karen Lizeth Quintero Franco

public class consultar {
    public static void main(String[] args) {
        conexion con = new conexion();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
            return; 
        }

        String sql = "SELECT * FROM usuarios";

        // try-with-resources -> se encarga de cerrar automáticamente recursos
        try (Connection conn = con.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n===== LISTADO ACTUAL DE USUARIOS EN LA BASE DE DATOS =====\n");
            System.out.println("--------------------------------------------------------------");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id_usuario") + " : " +
                    rs.getString("cedula") + " - " +
                    rs.getString("nombres") + " - " +
                    rs.getString("apellidos") + " - " +
                    rs.getString("cargo") + " - " +
                    rs.getString("rol") + " - " +
                    rs.getString("email") + " - " +
                    rs.getString("telefono") + " - " +
                    rs.getString("contraseña")
                );
            }
            System.out.println("--------------------------------------------------------------");

        } catch (SQLException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
   