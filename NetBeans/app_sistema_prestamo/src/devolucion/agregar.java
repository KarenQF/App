package devolucion;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco

public class agregar {

    public static void main(String[] args) {
        conexion con = new conexion();

        // Datos de la devolución 
        int id_prestamo = 14;   // Préstamo a devolver
        int id_usuario = 13;    // Usuario que devuelve
        int id_equipo = 1;      // Equipo devuelto
        Date fecha_devolucion = Date.valueOf("2025-09-07");
        Time hora_devolucion = Time.valueOf("18:00:00");
        String estado_equipo = "Dañado"; // Opciones válidas: Devuelto, Dañado, Extraviado

        // Validación del estado para evitar error de ENUM
        if (!estado_equipo.equals("Devuelto") &&
            !estado_equipo.equals("Dañado") &&
            !estado_equipo.equals("Extraviado")) {
            System.out.println("Estado Inválido. Debe ser: Devuelto, Dañado o Extraviado.");
            return;
        }

        // Verificación que el préstamo existe y coincide con usuario/equipo
        String sqlCheck = "SELECT estado_prestamo FROM prestamo "
                        + "WHERE id_prestamo = ? AND id_usuario = ? AND id_equipo = ?";

        // Insertar devolución
        String sqlInsert = "INSERT INTO devolucion "
                         + "(id_usuario, id_equipo, fecha_devolucion, hora_devolucion, estado_equipo_devolucion, id_prestamo) "
                         + "VALUES (?, ?, ?, ?, ?, ?)";

        // Actualizar préstamo a "Finalizado"
        String sqlUpdatePrestamo = "UPDATE prestamo SET estado_prestamo = 'Finalizado' WHERE id_prestamo = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = con.getConection();
                 PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {

                pstmtCheck.setInt(1, id_prestamo);
                pstmtCheck.setInt(2, id_usuario);
                pstmtCheck.setInt(3, id_equipo);

                try (ResultSet rs = pstmtCheck.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No se Puede Registrar Devolución. "
                                + "El Préstamo No Existe o No Corresponde al Usuario/Equipo Indicado.");
                        return;
                    }

                    // NUEVA VALIDACIÓN: si el préstamo ya está finalizado
                    String estadoPrestamo = rs.getString("estado_prestamo");
                    if ("Finalizado".equalsIgnoreCase(estadoPrestamo)) {
                        System.out.println("Este Préstamo Ya Está Finalizado. No se Puede Registrar Otra Devolución.");
                        return;
                    }

                    // Insertar devolución
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setInt(1, id_usuario);
                        pstmtInsert.setInt(2, id_equipo);
                        pstmtInsert.setDate(3, fecha_devolucion);
                        pstmtInsert.setTime(4, hora_devolucion);
                        pstmtInsert.setString(5, estado_equipo);
                        pstmtInsert.setInt(6, id_prestamo);

                        pstmtInsert.executeUpdate();
                        System.out.println("Devolución Registrada Correctamente.");
                    }

                    // Actualizar préstamo a "Finalizado"
                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdatePrestamo)) {
                        pstmtUpdate.setInt(1, id_prestamo);
                        pstmtUpdate.executeUpdate();
                        System.out.println("Estado del Préstamo Actualizado a 'Finalizado'.");
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
