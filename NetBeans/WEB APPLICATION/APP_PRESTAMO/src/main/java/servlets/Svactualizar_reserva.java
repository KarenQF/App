// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svactualizar_reserva")
public class Svactualizar_reserva extends HttpServlet {

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
        int id_reserva = Integer.parseInt(request.getParameter("id_reserva"));
        String id_usuario = request.getParameter("id_usuario");
        String id_equipo = request.getParameter("id_equipo");
        String fecha_reserva = request.getParameter("fecha_reserva");
        String hora_inicio_reserva = request.getParameter("hora_inicio_reserva");
        String hora_fin_reserva = request.getParameter("hora_fin_reserva");
        String estado_reserva = request.getParameter("estado_reserva");

        try {
            // Cargar el driver JDBC para conectar con MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Sentencia SQL para actualizar los datos de la resera en la tabla "reserva"
            String sql = "UPDATE reserva SET id_usuario=?, id_equipo=?, fecha_reserva=?, hora_inicio_reserva=?, hora_fin_reserva=?, estado_reserva=? WHERE id_reserva=?";
                
            // Preparar la consulta con los valores capturados del formulario
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id_usuario);
            ps.setString(2, id_equipo);
            ps.setString(3, fecha_reserva);
            ps.setString(4, hora_inicio_reserva);
            ps.setString(5, hora_fin_reserva);
            ps.setString(6, estado_reserva);
            ps.setInt(7, id_reserva);

            
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
                session.setAttribute("mensaje", "✅ Reserva actualizada correctamente.");
            } else {
                // Si no se encontró el usuario con ese ID
                session.setAttribute("mensaje", "⚠️ No se encontró la reserva para actualizar.");
            }
            response.sendRedirect("menu_gestion_reserva.jsp");


        } catch (Exception e) {
            // En caso de error, mostrar el mensaje en consola y enviar alerta al usuario
            e.printStackTrace();
            
            // Guardar el mensaje de error en la sesión para mostrarlo en el JSP
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al actualizar la reserva: " + e.getMessage());
            response.sendRedirect("menu_gestion_reserva.jsp");
        }
    }
}
