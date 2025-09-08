package prestamo;

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

        // Datos del préstamo a editar
        int id_prestamo = 15; // Debe existir en la tabla prestamo
        int id_usuario = 12; // Nuevo usuario (si deseas cambiarlo)
        int id_equipo = 4;   // Nuevo equipo
        Date fecha_prestamo = Date.valueOf("2025-09-06"); 
        Time hora_inicio = Time.valueOf("17:00:00"); 
        String descripcion_actividad = "Reunión Actualizada";

        // Sentencia SQL para actualizar
        String sqlUpdate = "UPDATE prestamo SET id_usuario = ?, id_equipo = ?, "
                + "fecha_prestamo = ?, hora_inicio_prestamo = ?, descripcion_actividad = ? "
                + "WHERE id_prestamo = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = con.getConection();
                 PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {

                pstmt.setInt(1, id_usuario);
                pstmt.setInt(2, id_equipo);
                pstmt.setDate(3, fecha_prestamo);
                pstmt.setTime(4, hora_inicio);
                pstmt.setString(5, descripcion_actividad);
                pstmt.setInt(6, id_prestamo); // condición WHERE

                int filas = pstmt.executeUpdate();

                if (filas > 0) {
                    System.out.println("Préstamo Actualizado Correctamente.");
                } else {
                    System.out.println("No se Encontró el Préstamo con ID: " + id_prestamo);
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
