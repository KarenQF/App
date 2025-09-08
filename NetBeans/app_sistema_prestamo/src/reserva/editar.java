package reserva;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco 

public class editar {
    public static void main(String[] args) {
        conexion con = new conexion();
        Connection conn;
        PreparedStatement pstmt;

        //Datos de la Reserva a Actualizar
        int id_reserva = 15; // <-- ID de la reserva que quieres editar
        int id_usuario = 13; // nuevo usuario
        int id_equipo = 7;  // nuevo equipo
        Date fecha_reserva = Date.valueOf("2025-12-28"); // nueva fecha
        Time hora_inicio = Time.valueOf("09:00:00");     // nueva hora inicio
        Time hora_fin = Time.valueOf("11:00:00");        // nueva hora fin

        //SQL para actualizar la reserva
        String sqlUpdate = "UPDATE reserva SET id_usuario = ?, id_equipo = ?, fecha_reserva = ?, "
                         + "hora_inicio_reserva = ?, hora_fin_reserva = ? "
                         + "WHERE id_reserva = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setInt(1, id_usuario);
            pstmt.setInt(2, id_equipo);
            pstmt.setDate(3, fecha_reserva);
            pstmt.setTime(4, hora_inicio);
            pstmt.setTime(5, hora_fin);
            pstmt.setInt(6, id_reserva);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Reserva Actualizada Correctamente.");
            } else {
                System.out.println("No se Encontró Ninguna Reserva con ID " + id_reserva);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}