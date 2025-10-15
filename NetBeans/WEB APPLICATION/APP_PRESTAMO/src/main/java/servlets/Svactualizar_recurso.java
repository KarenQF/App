// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svactualizar_recurso")
public class Svactualizar_recurso extends HttpServlet {

    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configuración de codificación y tipo de contenido (para soportar caracteres especiales como tildes)
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Capturar los datos enviados desde el formulario HTML o JSP 
        int id_equipo = Integer.parseInt(request.getParameter("id_equipo"));
        String no_inventario = request.getParameter("no_inventario");
        String descripcion = request.getParameter("descripcion");
        String fecha_adquisicion = request.getParameter("fecha_adquisicion");
        String valor = request.getParameter("valor");
        String localizacion = request.getParameter("localizacion");
        String estado_equipo = request.getParameter("estado_equipo");

        try {
            // Cargar el driver JDBC para conectar con MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Sentencia SQL para actualizar los datos del usuario en la tabla "usuarios"
            String sql = "UPDATE recursos_tecnologicos SET no_inventario=?, descripcion=?, fecha_adquisicion=?, valor=?, localizacion=?, estado_equipo=? WHERE id_equipo=?";
                
            // Preparar la consulta con los valores capturados del formulario
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, no_inventario);
            ps.setString(2, descripcion);
            ps.setString(3, fecha_adquisicion);
            ps.setInt(4, Integer.parseInt(valor));
            ps.setString(5, localizacion);
            ps.setString(6, estado_equipo);
            ps.setInt(7, id_equipo); // Conversión del ID a entero
            
 
            // Ejecutar la actualización y guardar el número de filas afectadas
            int filas = ps.executeUpdate();

            // Cerrar los recursos abiertos (PreparedStatement y conexión)
            ps.close();
            con.close();

            // Crear o recuperar la sesión para mostrar mensajes en el JSP
            HttpSession session = request.getSession();
            
            // Evaluar si la actualización fue exitosa
            if (filas > 0) {
                // Si se actualizó al menos un registro
                session.setAttribute("mensaje", "✅ recurso Tecnológico actualizado correctamente.");
            } else {
                // Si no se encontró el usuario con ese ID
                session.setAttribute("mensaje", "⚠️ No se encontró el recurso Tecnológico para actualizar.");
            }
            response.sendRedirect("menu_gestion_recurso.jsp");


        } catch (Exception e) {
            // En caso de error, mostrar el mensaje en consola y enviar alerta al usuario
            e.printStackTrace();
            
            // Guardar el mensaje de error en la sesión para mostrarlo en el JSP
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al actualizar el recurso Tecnológico: " + e.getMessage());
            response.sendRedirect("menu_gestion_recurso.jsp");
        }
    }
}
