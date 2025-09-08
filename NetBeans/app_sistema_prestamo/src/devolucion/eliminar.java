package devolucion;

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

        // ID de la devolución que quieres eliminar
        int idDevolucion = 10;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            // Primero obtener el id_prestamo asociado a la devolución
            String sqlGetPrestamo = "SELECT id_prestamo FROM devolucion WHERE id_devolucion = ?";
            pstmt = conn.prepareStatement(sqlGetPrestamo);
            pstmt.setInt(1, idDevolucion);
            var rs = pstmt.executeQuery();

            int idPrestamo = 0;
            if (rs.next()) {
                idPrestamo = rs.getInt("id_prestamo");
            }

            // Eliminar la devolución
            String sqlDelete = "DELETE FROM devolucion WHERE id_devolucion = ?";
            pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setInt(1, idDevolucion);
            int filasEliminadas = pstmt.executeUpdate();

            if (filasEliminadas > 0) {
                System.out.println("Devolución Eliminada Correctamente.");

                // Actualizar estado del préstamo asociado (por ejemplo: 'Activo' o 'Pendiente')
                if (idPrestamo > 0) {
                    String sqlUpdatePrestamo = "UPDATE prestamo SET estado_prestamo = 'Activo' WHERE id_prestamo = ?";
                    pstmt = conn.prepareStatement(sqlUpdatePrestamo);
                    pstmt.setInt(1, idPrestamo);
                    pstmt.executeUpdate();
                    System.out.println("Estado del Préstamo Actualizado a 'Activo'.");
                }

            } else {
                System.out.println("No se Encontró la Devolución con ID " + idDevolucion);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}