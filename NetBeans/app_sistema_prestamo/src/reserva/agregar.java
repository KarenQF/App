package reserva;

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

        // Datos de la reserva
        int id_usuario = 13;
        int id_equipo  = 7;
        // Ojo: septiembre tiene 30 días, "2025-09-31" es inválida
        // AAAA - MM - DD
        Date fecha_reserva = Date.valueOf("2025-12-21"); 

        // Recibimos las horas como String
        String strHoraInicio = "17:00:00";
        String strHoraFin    = "18:00:00";

        // Validar formato y rango de horas
        try {
            int horaInicioInt = Integer.parseInt(strHoraInicio.split(":")[0]);
            int horaFinInt = Integer.parseInt(strHoraFin.split(":")[0]);

            if (horaInicioInt < 0 || horaInicioInt > 24 || horaFinInt < 0 || horaFinInt > 24) {
                System.out.println("Error: La Hora de Inicio o Fin Debe Estar Entre 0 y 24. \nNo se Puede Agregar la Reserva.");
                return;
            }

            // Convertir a java.sql.Time
            Time hora_inicio = Time.valueOf(strHoraInicio);
            Time hora_fin    = Time.valueOf(strHoraFin);

            // Validar que hora inicio < hora fin
            if (hora_inicio.compareTo(hora_fin) >= 0) {
                System.out.println("Error: La Hora de Inicio Debe Ser Menor a la Hora de Fin.");
                return;
            }

            // SQL para verificar si ya está reservado
            String sqlCheck = 
                    "SELECT COUNT(*) AS total FROM reserva "
                  + "WHERE id_equipo = ? AND fecha_reserva = ? "
                  + "AND (hora_inicio_reserva < ? AND hora_fin_reserva > ?)";

            // SQL para insertar reserva
            String sqlInsert = 
                   "INSERT INTO reserva (id_usuario, id_equipo, fecha_reserva, hora_inicio_reserva, hora_fin_reserva, estado_reserva) "
                  + "VALUES (?, ?, ?, ?, ?, 'Activo')";

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = con.getConection();
                 PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {

                pstmtCheck.setInt(1, id_equipo);
                pstmtCheck.setDate(2, fecha_reserva);
                pstmtCheck.setTime(3, hora_fin);    
                pstmtCheck.setTime(4, hora_inicio);

                try (ResultSet rs = pstmtCheck.executeQuery()) {
                    rs.next();
                    int existe = rs.getInt("total");

                    if (existe > 0) {
                        System.out.println("El Equipo Tecnológico Ya Está Reservado en Ese Horario.");
                    } else {
                        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                            pstmtInsert.setInt(1, id_usuario);
                            pstmtInsert.setInt(2, id_equipo);
                            pstmtInsert.setDate(3, fecha_reserva);
                            pstmtInsert.setTime(4, hora_inicio);
                            pstmtInsert.setTime(5, hora_fin);

                            pstmtInsert.executeUpdate();
                            System.out.println("Reserva Agregada Correctamente.");
                        }
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Hora Inválida. \nDebe Estar en Formato HH:MM:SS y Entre 0 y 24.");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, "Error al Agregar la Reserva", ex);
        }
    }
}
