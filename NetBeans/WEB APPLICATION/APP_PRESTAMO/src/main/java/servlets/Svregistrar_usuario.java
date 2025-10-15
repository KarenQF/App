// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svregistrar_usuario")
public class Svregistrar_usuario extends HttpServlet {

     // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configura la codificación de caracteres para evitar problemas con tildes o ñ
        request.setCharacterEncoding("UTF-8");

        // Se obtienen los datos enviados desde el formulario HTML (campos input)
        String cedula = request.getParameter("cedula");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String cargo = request.getParameter("cargo");
        String rol = request.getParameter("rol");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String contrasena = request.getParameter("contrasena_registro");
        
        // Variable para almacenar el mensaje que se mostrará al usuario
        String mensaje;

        try {
            // Se carga el driver de conexión JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Se establece la conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Sentencia SQL para insertar un nuevo usuario en la tabla "usuarios"
            String sql = "INSERT INTO usuarios (cedula, nombres, apellidos, cargo, rol, email, telefono, contrasena) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            // Se prepara la sentencia SQL para evitar inyección de código (seguridad)
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, cargo);
            ps.setString(5, rol);
            ps.setString(6, correo);
            ps.setString(7, telefono);
            ps.setString(8, contrasena);

            
            // Se ejecuta la sentencia SQL y se obtiene el número de filas afectadas
            int filas = ps.executeUpdate();

            // Se cierran los recursos para liberar memoria
            ps.close();
            con.close();

            // Si al menos una fila fue insertada, el registro fue exitoso
            if (filas > 0) {
                mensaje = "✅ Usuario registrado correctamente.";
            } else {
                mensaje = "❌ Error al registrar el usuario.";
            }

        } catch (Exception e) {
            // Captura de cualquier error ocurrido durante el proceso
            e.printStackTrace();
            mensaje = "⚠️ Error en el registro: " + e.getMessage();
        }

        // Se guarda el mensaje en la sesión para poder mostrarlo en el JSP
        HttpSession session = request.getSession();
        session.setAttribute("mensaje", mensaje);

        // Redirección hacia la página "registrar_usuario.jsp" para mostrar el resultado
        // Se usa sendRedirect en lugar de forward para evitar reenvío del formulario al refrescar
        response.sendRedirect("registrar_usuario.jsp");
    }
}
