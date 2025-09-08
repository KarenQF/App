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

public class consultar {
    public static void main(String[] args) {
        conexion con = new conexion();
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        // Consulta de TODAS las reservas con nombres de usuario y equipo
        String sqlAll =
            "SELECT r.id_reserva, u.nombres, e.descripcion, " +
            "       r.fecha_reserva, r.hora_inicio_reserva, r.hora_fin_reserva, r.estado_reserva " +
            "FROM reserva r " +
            "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
            "JOIN recursos_tecnologicos e ON r.id_equipo = e.id_equipo";

        // Consultar de UNA reserva específica por ID
        String sqlById =
            "SELECT r.id_reserva, u.nombres, e.descripcion, " +
            "       r.fecha_reserva, r.hora_inicio_reserva, r.hora_fin_reserva, r.estado_reserva " +
            "FROM reserva r " +
            "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
            "JOIN recursos_tecnologicos e ON r.id_equipo = e.id_equipo " +
            "WHERE r.id_reserva = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            // 📌 Mostrar todas las reservas
            System.out.println("LISTADO DE RESERVAS: \n");
            System.out.println("--------------------------------------------------------------");
            pstmt = conn.prepareStatement(sqlAll);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_reserva = rs.getInt("id_reserva");
                String usuario = rs.getString("nombres");
                String equipo = rs.getString("descripcion");
                Date fecha = rs.getDate("fecha_reserva");
                Time horaInicio = rs.getTime("hora_inicio_reserva");
                Time horaFin = rs.getTime("hora_fin_reserva");
                String estado = rs.getString("estado_reserva");

                System.out.println("Reserva #" + id_reserva +
                                   " | Usuario: " + usuario +
                                   " | Equipo: " + equipo +
                                   " | Fecha: " + fecha +
                                   " | Inicio: " + horaInicio +
                                   " | Fin: " + horaFin +
                                   " | Estado: " + estado);
            }
            System.out.println("--------------------------------------------------------------");
            
            // Consulta de una reserva específica
            int idBusqueda = 9; // <-- Cambia este ID según lo que quieras buscar
            pstmt = conn.prepareStatement(sqlById);
            pstmt.setInt(1, idBusqueda);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("\nReserva Encontrada:");
                System.out.println("ID: " + rs.getInt("id_reserva"));
                System.out.println("Usuario: " + rs.getString("nombres"));
                System.out.println("Equipo: " + rs.getString("descripcion"));
                System.out.println("Fecha: " + rs.getDate("fecha_reserva"));
                System.out.println("Hora inicio: " + rs.getTime("hora_inicio_reserva"));
                System.out.println("Hora fin: " + rs.getTime("hora_fin_reserva"));
                System.out.println("Estado: " + rs.getString("estado_reserva"));
            } else {
                System.out.println("\nNo se Encontró la Reserva con ID " + idBusqueda);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}