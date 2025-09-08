package informe;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class consultar{
    public static void main(String[] args) {
        conexion con = new conexion();
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        // SQL para listar todos los informes con información detallada
        String sqlAll = "SELECT i.id_informe, u.nombre AS nombre_usuario, e.descripcion AS descripcion_equipo, " +
                        "r.fecha_reserva, r.hora_inicio_reserva, r.hora_fin_reserva, " +
                        "p.fecha_prestamo, p.hora_inicio_prestamo, p.descripcion_actividad " +
                        "FROM informe i " +
                        "LEFT JOIN usuarios u ON i.id_usuario = u.id_usuario " +
                        "LEFT JOIN recursos_tecnologicos e ON i.id_equipo = e.id_equipo " +
                        "LEFT JOIN reserva r ON i.id_reserva = r.id_reserva " +
                        "LEFT JOIN prestamo p ON i.id_prestamo = p.id_prestamo";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            // Listar todos los informes con detalles
            System.out.println("LISTADO DETALLADO DE INFORMES:");
            pstmt = conn.prepareStatement(sqlAll);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Informe ID: " + rs.getInt("id_informe") +
                                   " | Usuario: " + rs.getString("nombre_usuario") +
                                   " | Equipo: " + rs.getString("descripcion_equipo") +
                                   " | Fecha Reserva: " + rs.getDate("fecha_reserva") +
                                   " | Hora Inicio Reserva: " + rs.getTime("hora_inicio_reserva") +
                                   " | Hora Fin Reserva: " + rs.getTime("hora_fin_reserva") +
                                   " | Fecha Prestamo: " + rs.getDate("fecha_prestamo") +
                                   " | Hora Inicio Prestamo: " + rs.getTime("hora_inicio_prestamo") +
                                   " | Actividad: " + rs.getString("descripcion_actividad"));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}