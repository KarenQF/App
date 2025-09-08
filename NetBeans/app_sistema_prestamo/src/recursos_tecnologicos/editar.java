package recursos_tecnologicos;

import conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// Karen Lizeth Quintero Franco

public class editar {
    public static void main(String[] args) {
        conexion con = new conexion();

        // Dato clave para identificar el recurso 
        String noInventarioBuscar = "539065438"; // con este buscamos el recurso a editar

        // Nuevos Datos Para Actualizar
        String nuevoNoInventario = "539065438";
        String nuevaDescripcion = "Monitor 32";
        // Ojo: septiembre tiene 30 días, "2025-09-31" es inválida
        // AAAA - MM - DD
        Date nuevaFechaAdquisicion = Date.valueOf("2025-08-31");
        int nuevoValor = 1500000;
        //En Localización Estan Definidos los Siguientes:(Villa Campestre,Principe,Victoria) 
        String nuevaLocalizacion = "Victoria"; // Villa Campestre, Principe, Victoria
         //En Estado Equipo Estan Definido los Siguientes: (Activo,En reparación,Dado de baja,Prestado)
        String nuevoEstadoEquipo = "Dado de Baja";   // Activo, En reparación, Dado de baja, Prestado

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, "Error cargando el driver MySQL", ex);
            return;
        }

        // Sentencias SQL
        String sqlGetId = "SELECT id_equipo FROM recursos_tecnologicos WHERE no_inventario = ?";
        String sqlCheck = "SELECT COUNT(*) AS total FROM recursos_tecnologicos WHERE no_inventario = ? AND id_equipo <> ?";
        String sqlUpdate = "UPDATE recursos_tecnologicos SET no_inventario = ?, descripcion = ?, fecha_adquisicion = ?, valor = ?, localizacion = ?, estado_equipo = ? WHERE id_equipo = ?";
        String sqlSelect = "SELECT * FROM recursos_tecnologicos";

        try (Connection cn = con.getConection()) {

            // Buscar el ID del recurso usando el no_inventario
            int id_equipoEditar = -1;
            try (PreparedStatement psGetId = cn.prepareStatement(sqlGetId)) {
                psGetId.setString(1, noInventarioBuscar);
                try (ResultSet rs = psGetId.executeQuery()) {
                    if (rs.next()) {
                        id_equipoEditar = rs.getInt("id_equipo");
                    } else {
                        System.out.println("No Existe un Recurso Tecnológico con No. Inventario: " + noInventarioBuscar);
                        return;
                    }
                }
            }

            // Validación de que el nuevo inventario no esté duplicado
            try (PreparedStatement psCheck = cn.prepareStatement(sqlCheck)) {
                psCheck.setString(1, nuevoNoInventario);
                psCheck.setInt(2, id_equipoEditar);
                ResultSet rsCheck = psCheck.executeQuery();
                rsCheck.next();
                int existe = rsCheck.getInt("total");

                if (existe > 0) {
                    System.out.println("El Número de Inventario " + nuevoNoInventario + " Ya Está en Uso Por Otro Equipo.");
                    return;
                }
            }

            // Ejecutar actualización
            try (PreparedStatement ps = cn.prepareStatement(sqlUpdate)) {
                ps.setString(1, nuevoNoInventario);
                ps.setString(2, nuevaDescripcion);
                ps.setDate(3, nuevaFechaAdquisicion);
                ps.setInt(4, nuevoValor);
                ps.setString(5, nuevaLocalizacion);
                ps.setString(6, nuevoEstadoEquipo);
                ps.setInt(7, id_equipoEditar);

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Recurso Tecnológico con Inventario " + noInventarioBuscar + " Actualizado Correctamente.\n");
                } else {
                    System.out.println("No se Pudo Actualizar el Recurso Tecnológico.");
                }
            }

            // Mostrar listado actualizado
            try (PreparedStatement psSelect = cn.prepareStatement(sqlSelect);
                 ResultSet rs = psSelect.executeQuery()) {

                System.out.println("LISTADO DE RECURSOS TECNOLÓGICOS: \n");
                System.out.println("--------------------------------------------------------------");
                while (rs.next()) {
                    System.out.println(
                        "ID: " + rs.getInt("id_equipo") +
                        " | Inventario: " + rs.getString("no_inventario") +
                        " | Descripción: " + rs.getString("descripcion") +
                        " | Fecha: " + rs.getDate("fecha_adquisicion") +
                        " | Valor: " + rs.getInt("valor") +
                        " | Localización: " + rs.getString("localizacion") +
                        " | Estado: " + rs.getString("estado_equipo")
                    );
                }
                System.out.println("--------------------------------------------------------------");
            }

        } catch (SQLException ex) {
            Logger.getLogger(editar.class.getName()).log(Level.SEVERE, "Error en la operación SQL", ex);
        }
    }
}
