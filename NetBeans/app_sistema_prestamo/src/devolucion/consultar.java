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

public class consultar {
    public static void main(String[] args) {
        conexion con = new conexion();
        Connection conn;
        PreparedStatement pstmt;
        ResultSet rs;

        // Consulta todas las devoluciones
        String sqlAll = "SELECT d.id_devolucion, d.id_usuario, d.id_equipo, d.fecha_devolucion, d.hora_devolucion, d.estado_equipo_devolucion, " +
                "u.nombres, u.apellidos, " +
                "e.descripcion " +
                "FROM devolucion d " +
                "JOIN usuarios u ON d.id_usuario = u.id_usuario " +
                "JOIN recursos_tecnologicos e ON d.id_equipo = e.id_equipo";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = con.getConection();

            System.out.println("LISTADO DE DEVOLUCIONES:");
            System.out.println("--------------------------------------------------------------");
            pstmt = conn.prepareStatement(sqlAll);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idDevolucion = rs.getInt("id_devolucion");
                Date fecha = rs.getDate("fecha_devolucion");
                Time hora = rs.getTime("hora_devolucion");
                String estado = rs.getString("estado_equipo_devolucion");
                int idUsuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombres");
                int idEquipo = rs.getInt("id_equipo");
                String descripcion = rs.getString("descripcion");

                System.out.println("ID Devolución: " + idDevolucion +
                                   " | Usuario: " + nombreUsuario + " (ID: " + idUsuario + ")" +
                                   " | Equipo: " + descripcion + " (ID: " + idEquipo + ")" +
                                   " | Fecha: " + fecha +
                                   " | Hora: " + hora +
                                   " | Estado: " + estado);
            }
            System.out.println("--------------------------------------------------------------");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(consultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}