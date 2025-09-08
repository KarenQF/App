package prestamo;

import conexion.conexion; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.sql.Date; 
import java.sql.Time;

// Karen Lizeth Quintero Franco 

public class agregar {
    public static void main (String[] args){
        conexion con = new conexion();

        // Datos del Préstamo a Ingresar:
        int id_usuario = 40;   // Debe existir en tabla usuarios
        int id_equipo  = 9;    // Debe existir en tabla recursos_tecnologicos
        // Ojo: septiembre tiene 30 días, "2025-09-31" es inválida
        // AAAA - MM - DD
        Date fecha_prestamo = Date.valueOf("2025-12-21"); 
        Time hora_inicio = Time.valueOf("17:00:00"); 
        String descripcion_actividad = "Clase";

        // Verificación de la reserva activa
        String sqlCheckReserva = 
            "SELECT id_reserva, id_usuario FROM reserva " +
            "WHERE id_equipo = ? AND fecha_reserva = ? " +
            "AND estado_reserva = 'Activo' " +
            "AND (? BETWEEN hora_inicio_reserva AND hora_fin_reserva)";

        // Verificación si ya hay préstamo activo ese día
        String sqlCheckPrestamoActivo = 
            "SELECT COUNT(*) AS total FROM prestamo " +
            "WHERE id_equipo = ? AND fecha_prestamo = ? AND estado_prestamo = 'Activo'";

        // Verificación de devoluciones (último estado del equipo)
        String sqlCheckDevolucion = 
            "SELECT estado_equipo_devolucion FROM devolucion " +
            "WHERE id_equipo = ? ORDER BY fecha_devolucion DESC LIMIT 1";

        // Insertar Préstamo
        String sqlInsert = 
            "INSERT INTO prestamo (id_usuario, id_equipo, fecha_prestamo, hora_inicio_prestamo, descripcion_actividad, estado_prestamo) " +
            "VALUES (?, ?, ?, ?, ?, 'Activo')";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = con.getConection()) {

                // Validación de las devoluciones primero
                try (PreparedStatement psDev = conn.prepareStatement(sqlCheckDevolucion)) {
                    psDev.setInt(1, id_equipo);
                    try (ResultSet rs = psDev.executeQuery()) {
                        if (rs.next()) {
                            String estadoDev = rs.getString("estado_equipo_devolucion");
                            if ("Dañado".equalsIgnoreCase(estadoDev) || "Extraviado".equalsIgnoreCase(estadoDev)) {
                                System.out.println("No se Puede Prestar: El Equipo Está Marcado Como: " + estadoDev + " en la Última Devolución.");
                                return;
                            }
                        }
                    }
                }

                int idReserva = -1;
                int usuarioReserva = -1;

                // Validación de las reservas activas
                try (PreparedStatement pstmtReserva = conn.prepareStatement(sqlCheckReserva)) {
                    pstmtReserva.setInt(1, id_equipo);
                    pstmtReserva.setDate(2, fecha_prestamo);
                    pstmtReserva.setTime(3, hora_inicio);

                    try (ResultSet rs = pstmtReserva.executeQuery()) {
                        if (rs.next()) {
                            idReserva = rs.getInt("id_reserva");
                            usuarioReserva = rs.getInt("id_usuario");
                        }
                    }
                }

                if (idReserva != -1) {
                    // Existe una reserva → verificar que coincida el usuario
                    if (usuarioReserva != id_usuario) {
                        System.out.println("Este Equipo Tecnológico Está RESERVADO por Otro Usuario en Esa Fecha.");
                        return;
                    }

                    // Validación de que no exista préstamo activo en esa fecha
                    try (PreparedStatement pstmtCheckActivo = conn.prepareStatement(sqlCheckPrestamoActivo)) {
                        pstmtCheckActivo.setInt(1, id_equipo);
                        pstmtCheckActivo.setDate(2, fecha_prestamo);

                        try (ResultSet rs = pstmtCheckActivo.executeQuery()) {
                            rs.next();
                            if (rs.getInt("total") > 0) {
                                System.out.println("El Equipo Tecnológico Ya Tiene un PRÉSTAMO ACTIVO en Esa Fecha.");
                                return;
                            }
                        }
                    }

                    // Finalizar la reserva
                    String sqlUpdateReserva = "UPDATE reserva SET estado_reserva = 'Finalizado' WHERE id_reserva = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sqlUpdateReserva)) {
                        ps.setInt(1, idReserva);
                        ps.executeUpdate();
                        System.out.println("Reserva ID " + idReserva + " Finalizada.");
                    }

                    // Insertar préstamo
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setInt(1, id_usuario);
                        pstmtInsert.setInt(2, id_equipo);
                        pstmtInsert.setDate(3, fecha_prestamo);
                        pstmtInsert.setTime(4, hora_inicio);
                        pstmtInsert.setString(5, descripcion_actividad);

                        pstmtInsert.executeUpdate();
                        System.out.println("Préstamo Registrado Desde Reserva.");
                    }

                } else {
                    // Validar que no exista préstamo activo en esa fecha
                    try (PreparedStatement pstmtCheckActivo = conn.prepareStatement(sqlCheckPrestamoActivo)) {
                        pstmtCheckActivo.setInt(1, id_equipo);
                        pstmtCheckActivo.setDate(2, fecha_prestamo);

                        try (ResultSet rs = pstmtCheckActivo.executeQuery()) {
                            rs.next();
                            if (rs.getInt("total") > 0) {
                                System.out.println("El Equipo Tecnológico Ya Tiene un PRÉSTAMO ACTIVO en Esa Fecha.");
                                return;
                            }
                        }
                    }

                    // Insertar Préstamo Normal
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setInt(1, id_usuario);
                        pstmtInsert.setInt(2, id_equipo);
                        pstmtInsert.setDate(3, fecha_prestamo);
                        pstmtInsert.setTime(4, hora_inicio);
                        pstmtInsert.setString(5, descripcion_actividad);

                        pstmtInsert.executeUpdate();
                        System.out.println("Préstamo Agregado Correctamente.");
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}