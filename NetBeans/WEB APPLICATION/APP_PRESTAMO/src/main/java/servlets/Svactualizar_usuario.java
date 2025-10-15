// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svactualizar_usuario")
public class Svactualizar_usuario extends HttpServlet {

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
        String id = request.getParameter("id_usuario");
        String cedula = request.getParameter("cedula");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String cargo = request.getParameter("cargo");
        String rol = request.getParameter("rol");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String contrasena = request.getParameter("contrasena");

        try {
            // Cargar el driver JDBC para conectar con MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Sentencia SQL para actualizar los datos del usuario en la tabla "usuarios"
            String sql = "UPDATE usuarios SET cedula=?, nombres=?, apellidos=?, cargo=?, rol=?, email=?, telefono=?, contrasena=? WHERE id_usuario=?";
                
            // Preparar la consulta con los valores capturados del formulario
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, cargo);
            ps.setString(5, rol);
            ps.setString(6, email);
            ps.setString(7, telefono);
            ps.setString(8, contrasena);
            ps.setInt(9, Integer.parseInt(id)); // Conversión del ID a entero
            
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
                session.setAttribute("mensaje", "✅ Usuario actualizado correctamente.");
            } else {
                // Si no se encontró el usuario con ese ID
                session.setAttribute("mensaje", "⚠️ No se encontró el usuario para actualizar.");
            }
            response.sendRedirect("menu_gestion_usuario.jsp");


        } catch (Exception e) {
            // En caso de error, mostrar el mensaje en consola y enviar alerta al usuario
            e.printStackTrace();
            
            // Guardar el mensaje de error en la sesión para mostrarlo en el JSP
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al actualizar el usuario: " + e.getMessage());
            response.sendRedirect("menu_gestion_usuario.jsp");
        }
    }
}
