package usuarios;

import conexion.conexion; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.sql.SQLIntegrityConstraintViolationException; 

// Realizado por: Karen Lizeth Quintero Franco

public class agregar {
    public static void main(String[] args) {
        conexion con = new conexion();

        // Datos a Ingresar:
        String cedula = "54633323";
        String nombres = "Camilo";
        String apellidos = "Nieto";
        //En Cargo Estan Definidos los Siguientes:(Docente,Estudiante,Personal Administrativo,Personal Externo)
        String cargo = "Docente"; 
        //En Rol Estan Definidos los Siguientes:(Usuario,Administrador)
        String rol = "Usuario"; 
        String email = "camilonieto@gmail.com"; 
        String telefono = "3107643259";
        String contraseña = "54633323";
        
        String sql = "INSERT INTO usuarios (cedula, nombres, apellidos, cargo, rol, email, telefono, contraseña) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Connection conn = con.getConection()) {

            // Validación si la cédula ya existe
            String sqlCheckCedula = "SELECT COUNT(*) FROM usuarios WHERE cedula=?";
            boolean cedulaExiste = false;
            try (PreparedStatement checkCedula = conn.prepareStatement(sqlCheckCedula)) {
                checkCedula.setString(1, cedula);
                try (ResultSet rs = checkCedula.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        cedulaExiste = true;
                    }
                }
            }

            // Validación si el nombre completo ya existe
            String sqlCheckNombre = "SELECT COUNT(*) FROM usuarios WHERE nombres=? AND apellidos=?";
            boolean nombreExiste = false;
            try (PreparedStatement checkNombre = conn.prepareStatement(sqlCheckNombre)) {
                checkNombre.setString(1, nombres);
                checkNombre.setString(2, apellidos);
                try (ResultSet rs = checkNombre.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        nombreExiste = true;
                    }
                }
            }

            // Validación si el email ya existe
            String sqlCheckEmail = "SELECT COUNT(*) FROM usuarios WHERE email=?";
            boolean emailExiste = false;
            try (PreparedStatement checkEmail = conn.prepareStatement(sqlCheckEmail)) {
                checkEmail.setString(1, email);
                try (ResultSet rs = checkEmail.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        emailExiste = true;
                    }
                }
            }

            // Validación si el teléfono ya existe
            String sqlCheckTelefono = "SELECT COUNT(*) FROM usuarios WHERE telefono=?";
            boolean telefonoExiste = false;
            try (PreparedStatement checkTelefono = conn.prepareStatement(sqlCheckTelefono)) {
                checkTelefono.setString(1, telefono);
                try (ResultSet rs = checkTelefono.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        telefonoExiste = true;
                    }
                }
            }

            // Mostrar en pantalla según corresponda
            if (cedulaExiste && nombreExiste) {
                System.out.println("El Usuario con Cédula '" + cedula + "' y Nombre Completo '" + nombres + " " + apellidos + "' Ya se Encuentra Registrado.");
                return;
            } else if (cedulaExiste) {
                System.out.println("La Cédula '" + cedula + "' Ya se Encuentra Registrada.");
                return;
            } else if (nombreExiste) {
                System.out.println("El Nombre Completo '" + nombres + " " + apellidos + "' Ya se Encuentra Registrado.");
                return;
            } else if (emailExiste) {
                System.out.println("El Email '" + email + "' Ya se Encuentra Registrado.");
                return;
            } else if (telefonoExiste) {
                System.out.println("El Teléfono '" + telefono + "' Ya se Encuentra Registrado.");
                return;
            }

            // Validación Cargo Permitido
            String[] cargosValidos = {"Docente", "Estudiante", "Personal Administrativo", "Personal Externo"};
            boolean cargoValido = false;
            for (String c : cargosValidos) {
                if (cargo.equalsIgnoreCase(c)) {
                    cargoValido = true;
                    break;
                }
            }
            if (!cargoValido) {
                System.out.println("ERROR: El Cargo '" + cargo + "' No es Válido. Debe ser: Docente, Estudiante, Personal Administrativo o Personal Externo");
                return;
            }

            // Validación Rol Permitido
            String[] rolesValidos = {"Usuario", "Administrador"};
            boolean rolValido = false;
            for (String r : rolesValidos) {
                if (rol.equalsIgnoreCase(r)) {
                    rolValido = true;
                    break;
                }
            }
            if (!rolValido) {
                System.out.println("ERROR: El Rol '" + rol + "' No es Válido. Debe ser: Usuario o Administrador");
                return;
            }

            // Si cumple con todas las validaciones, inserta:
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cedula);
                pstmt.setString(2, nombres);
                pstmt.setString(3, apellidos);
                pstmt.setString(4, cargo);
                pstmt.setString(5, rol);
                pstmt.setString(6, email);
                pstmt.setString(7, telefono);
                pstmt.setString(8, contraseña);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Registro Agregado Correctamente.");
                }
            }

            // Mostrar en Pantalla los Registros
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
                   
                System.out.println("\n===== LISTADO COMPLETO DE USUARIOS =====");
                System.out.println("--------------------------------------------------------------");
                
                while (rs.next()) {
                    System.out.println(
                        rs.getInt("id_usuario") + ": " +
                        rs.getString("cedula") + "-" +
                        rs.getString("nombres") + "-" +
                        rs.getString("apellidos") + "-" +
                        rs.getString("cargo") + "-" +
                        rs.getString("rol") + "-" +
                        rs.getString("email") + "-" +
                        rs.getString("telefono")+ "-" +
                        rs.getString("Contraseña")
                    );
                }
                System.out.println("--------------------------------------------------------------");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERROR: La Cédula Ya se Encuentra Registrada en el Sistema.");
        } catch (SQLException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}