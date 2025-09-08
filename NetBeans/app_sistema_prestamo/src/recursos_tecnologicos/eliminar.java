package recursos_tecnologicos;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco

public class eliminar {

    public static void main(String[] args) {
        conexion con = new conexion();

        // ID del recurso a eliminar
        int id_equipo = 4;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection cn = con.getConection()) {

                // Validación del estado del recurso
                String sqlEstadoEquipo = "SELECT estado_equipo FROM recursos_tecnologicos WHERE id_equipo = ?";
                try (PreparedStatement ps = cn.prepareStatement(sqlEstadoEquipo)) {
                    ps.setInt(1, id_equipo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            System.out.println("No Existe el Recurso Tecnológico con ID: " + id_equipo);
                            return;
                        }
                        String estadoEquipo = rs.getString("estado_equipo");
                        if (!"Activo".equalsIgnoreCase(estadoEquipo)) {
                            System.out.println("No se Puede Eliminar Porque el Estado del Recurso Tecnológico no es 'Activo'.");
                            return;
                        }
                    }
                }

                // Validación de los préstamos finalizados
                String sqlPrestamo = "SELECT COUNT(*) FROM prestamo WHERE id_equipo = ? AND estado_prestamo <> 'Finalizado'";
                try (PreparedStatement ps = cn.prepareStatement(sqlPrestamo)) {
                    ps.setInt(1, id_equipo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            System.out.println("No se Puede Eliminar Porque Existen Préstamos no Finalizados.");
                            return;
                        }
                    }
                }

                // Validación de las reservas finalizadas
                String sqlReserva = "SELECT COUNT(*) FROM reserva WHERE id_equipo = ? AND estado_reserva <> 'Finalizado'";
                try (PreparedStatement ps = cn.prepareStatement(sqlReserva)) {
                    ps.setInt(1, id_equipo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            System.out.println("No se Puede Eliminar Porque Existen Reservas no Finalizadas.");
                            return;
                        }
                    }
                }

                // Validación de las devoluciones (solo se permite si estado_equipo_devolucion = 'Devuelto')
                String sqlDevolucion = "SELECT COUNT(*) FROM devolucion WHERE id_equipo = ? AND estado_equipo_devolucion <> 'Devuelto'";
                try (PreparedStatement ps = cn.prepareStatement(sqlDevolucion)) {
                    ps.setInt(1, id_equipo);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            System.out.println("No se Puede Eliminar Porque Existen Devoluciones que No Están en Estado 'Devuelto'.");
                            return;
                        }
                    }
                }

                // Eliminar Recurso
                String sqlDelete = "DELETE FROM recursos_tecnologicos WHERE id_equipo = ?";
                try (PreparedStatement ps = cn.prepareStatement(sqlDelete)) {
                    ps.setInt(1, id_equipo);
                    int filas = ps.executeUpdate();
                    if (filas > 0) {
                        System.out.println("Recurso Tecnológico Eliminado Correctamente (ID: " + id_equipo + ")");
                    } else {
                        System.out.println("No se Encontró el Recurso Tecnológico con ID: " + id_equipo);
                    }
                }

            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(eliminar.class.getName()).log(Level.SEVERE, "Error al Eliminar Recurso", ex);
        }
    }
}
