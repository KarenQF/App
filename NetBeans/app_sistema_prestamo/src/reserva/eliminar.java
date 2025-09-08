package reserva;

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
        int id_reserva = 15; // <-- cambia por el ID que quieras borrar

        //SQL para eliminar la reserva
        String sqlDelete = "DELETE FROM reserva WHERE id_reserva = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setInt(1, id_reserva);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Reserva Eliminada Correctamente.");
            } else {
                System.out.println("No se Encontró Ninguna Reserva con ID " + id_reserva);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}