package usuarios;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Realizado por: Karen Lizeth Quintero Franco

public class eliminar {
    public static void main(String[] args) {
        conexion con = new conexion();

        // Cédula del Usuario a Eliminar
        String cedula = "152";

        // SQL parametrizado
        String sql = "DELETE FROM usuarios WHERE cedula = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection conn = con.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cedula);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Registro Eliminado Correctamente.");
            } else {
                System.out.println("No se Encontró Usuario con la Cédula: " + cedula);
            }

        } catch (SQLException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}