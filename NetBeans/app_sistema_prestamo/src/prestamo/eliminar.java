package prestamo;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco

public class eliminar {
    public static void main(String[] args) {
        conexion con = new conexion();
        Connection conn;
        PreparedStatement pstmt;

        //ID de la reserva a eliminar
        int id_prestamo = 14; // <-- cambia por el ID que se quiere borrar

        //SQL para eliminar la reserva
        String sqlDelete = "DELETE FROM prestamo WHERE id_prestamo = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setInt(1, id_prestamo);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Prestamo Eliminado Correctamente.");
            } else {
                System.out.println("No se Encontró Ningun Prestamo con ID " + id_prestamo);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}