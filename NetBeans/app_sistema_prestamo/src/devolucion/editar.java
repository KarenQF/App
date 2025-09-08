package devolucion;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco 

public class editar {
    public static void main(String[] args) {
        conexion con = new conexion();
        Connection conn;
        PreparedStatement pstmt;

        // Datos para actualizar
        int idDevolucion = 8; // ID de la devolución que quieres editar
        Date nuevaFecha = Date.valueOf(LocalDate.of(2025, 9, 2));
        Time nuevaHora = Time.valueOf(LocalTime.of(10, 30, 0));
        String nuevoEstado = "Devuelto"; // Devuelto, Dañado, Extraviado

        String sqlUpdate = "UPDATE devolucion SET fecha_devolucion = ?, hora_devolucion = ?, estado_equipo_devolucion = ? WHERE id_devolucion = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setDate(1, nuevaFecha);
            pstmt.setTime(2, nuevaHora);
            pstmt.setString(3, nuevoEstado);
            pstmt.setInt(4, idDevolucion);

            int filasActualizadas = pstmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Devolución Actualizada Correctamente.");
            } else {
                System.out.println("No se Encontró la Devolución con ID " + idDevolucion);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}