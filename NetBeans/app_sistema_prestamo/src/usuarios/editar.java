package usuarios;

import conexion.conexion; //Clase personalizada para conectarte a MySQL
import java.sql.Connection; //Representa la conexion activa a la base de datos
import java.sql.PreparedStatement; //Ejecuta consultas SQL seguras (evita inyecciones SQL).
import java.sql.SQLException; //Clase de Java que representa errores relacionados con la base de datos
import java.util.logging.Level; //Sistema de Java para registrar mensajes y errores con distintos niveles de importancia
import java.util.logging.Logger; //Sistema de Java para registrar mensajes y errores con distintos niveles de importancia

// Realizado por: Karen Lizeth Quintero Franco

public class editar {
    public static void main(String[] args) {
        conexion con = new conexion();

        // Datos para Actualizar:
        String cedula = "52482828727222827";
        String nuevoNombres = "Carlos";
        String nuevoApellidos = "Sabogal";
        //En Cargo Estan Definidos los Siguientes:(Docente,Estudiante,Personal Administrativo,Personal Externo)
        String nuevoCargo = "Docente";
        //En Rol Estan Definidos los Siguientes:(Usuario,Administrador)
        String nuevoRol = "Usuario";
        String nuevoEmail = "carlos.sabogal@gmail.com";
        String nuevoTelefono = "3125678743";

        // SQL parametrizado
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, cargo = ?, rol = ?, email = ?, telefono = ? WHERE cedula = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection conn = con.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoNombres);
            pstmt.setString(2, nuevoApellidos);
            pstmt.setString(3, nuevoCargo);
            pstmt.setString(4, nuevoRol);
            pstmt.setString(5, nuevoEmail);
            pstmt.setString(6, nuevoTelefono);
            pstmt.setString(7, cedula);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Registro Actualizado Correctamente.");
            } else {
                System.out.println("No se Encontró Usuario con la Cédula: " + cedula);
            }

        } catch (SQLException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}