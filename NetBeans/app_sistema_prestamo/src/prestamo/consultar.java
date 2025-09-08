package prestamo;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco

public class consultar {

    public static void main(String[] args) {
        conexion con = new conexion();

        // SQL para todas las reservas con JOIN para traer nombres
        String sqlAll = "SELECT p.id_prestamo, u.nombres AS nombres, r.descripcion AS descripcion, " +
                        "p.fecha_prestamo, p.hora_inicio_prestamo, p.descripcion_actividad " +
                        "FROM prestamo p " +
                        "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                        "JOIN recursos_tecnologicos r ON p.id_equipo = r.id_equipo";

        // SQL para una reserva específica por ID
        String sqlById = "SELECT p.id_prestamo, u.nombres AS nombres, r.descripcion AS descripcion, " +
                         "p.fecha_prestamo, p.hora_inicio_prestamo, p.descripcion_actividad " +
                         "FROM prestamo p " +
                         "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                         "JOIN recursos_tecnologicos r ON p.id_equipo = r.id_equipo " +
                         "WHERE p.id_prestamo = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = con.getConection()) {

                // Consultar todas las reservas
                System.out.println("LISTADO DE PRESTAMOS:");
                System.out.println("--------------------------------------------------------------");

                try (PreparedStatement pstmt = conn.prepareStatement(sqlAll);
                     ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("id_prestamo") +
                                           " | Usuario: " + rs.getString("nombres") +
                                           " | Equipo: " + rs.getString("descripcion") +
                                           " | Fecha: " + rs.getDate("fecha_prestamo") +
                                           " | Inicio: " + rs.getTime("hora_inicio_prestamo") +
                                           " | DescripcionActividad: " + rs.getString("descripcion_actividad"));
                    }
                }

                System.out.println("--------------------------------------------------------------");

                // Consultar un Prestamo Específico
                int idBusqueda = 14; // <-- aquí se pone el ID que se quiere consultar
                try (PreparedStatement pstmt = conn.prepareStatement(sqlById)) {
                    pstmt.setInt(1, idBusqueda);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("\nPrestamo Encontrado:");
                            System.out.println("ID: " + rs.getInt("id_prestamo"));
                            System.out.println("Usuario: " + rs.getString("nombres"));
                            System.out.println("Equipo: " + rs.getString("descripcion"));
                            System.out.println("Fecha: " + rs.getDate("fecha_prestamo"));
                            System.out.println("Hora inicio: " + rs.getTime("hora_inicio_prestamo"));
                            System.out.println("DescripcionActividad: " + rs.getString("descripcion_actividad"));
                        } else {
                            System.out.println("\nNo se encontró el Prestamo con ID " + idBusqueda);
                        }
                    }
                }

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
