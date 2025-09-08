package recursos_tecnologicos;

import conexion.conexion; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.logging.Level; 
import java.util.logging.Logger; 
import java.sql.Date; 
import java.sql.PreparedStatement; 

// Karen Lizeth Quintero Franco 

public class agregar {
    public static void main (String[] args){
        conexion con = new conexion();
        
        // Datos a Ingresar:
        String no_inventario = "539065452";
        String descripcion = "Portatil";
        // Ojo: septiembre tiene 30 días, "2025-09-31" es inválida
        // AAAA - MM - DD
        Date fecha_adquisicion = Date.valueOf("2025-09-30"); 
        //Valor del inventario
        int valor = 1123456;
        //En Localización Estan Definidos los Siguientes:(Villa Campestre,Principe,Victoria) 
        String localizacion = "Villa Campestre";
        //En Estado Equipo Estan Definido los Siguientes: (Activo,En reparación,Dado de baja,Prestado)
        String estado_equipo = "Activo";
        
        String sqlCheck = "SELECT COUNT(*) AS total FROM recursos_tecnologicos WHERE no_inventario = ?";
        String sqlInsert = "INSERT INTO recursos_tecnologicos "
                + "(no_inventario, descripcion, fecha_adquisicion, valor, localizacion, estado_equipo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        String sqlSelect = "SELECT * FROM recursos_tecnologicos";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = con.getConection()) {
                
                // Verificación si Ya Existe el No. de Inventario
                try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                    pstmtCheck.setString(1, no_inventario);
                    ResultSet rsCheck = pstmtCheck.executeQuery();
                    rsCheck.next();
                    int existe = rsCheck.getInt("total");

                    if (existe > 0) {
                        System.out.println("El Número de Inventario " + no_inventario + " Ya Existe. \nNo Se Insertó el Recurso Tecnológico.\n");
                        return; // detener el programa para no duplicar
                    }
                }
                
                // Si no Existe, Hacer la Inserción
                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                    pstmt.setString(1, no_inventario);
                    pstmt.setString(2, descripcion);
                    pstmt.setDate(3, fecha_adquisicion);
                    pstmt.setInt(4, valor);
                    pstmt.setString(5, localizacion);
                    pstmt.setString(6, estado_equipo);

                    pstmt.executeUpdate();
                    System.out.println("Recurso Tecnológico Agregado Correctamente.\n");
                }

                // Mostrar en Pantalla los Registros Actualizados
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(sqlSelect)) {
                    
                    System.out.println("LISTADO DE RECURSOS TECNOLÓGICOS: \n");
                    System.out.println("--------------------------------------------------------------");
                    while (rs.next()) {
                        System.out.println(
                            rs.getInt("id_equipo") + " : " +
                            rs.getString("no_inventario") + " - " +
                            rs.getString("descripcion") + " - " +
                            rs.getDate("fecha_adquisicion") + " - " +
                            rs.getInt("valor") + " - " +
                            rs.getString("localizacion") + " - " +
                            rs.getString("estado_equipo")
                        );
                    }
                    System.out.println("--------------------------------------------------------------");
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(agregar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}