// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svregistrar_recurso")
public class Svregistrar_recurso extends HttpServlet {

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
        String no_inventario = request.getParameter("no_inventario");
        String descripcion = request.getParameter("descripcion");
        String fecha_adquisicion = request.getParameter("fecha_adquisicion");
        String valor = request.getParameter("valor");
        String localizacion = request.getParameter("localizacion");
        String estado_equipo = request.getParameter("estado_equipo");

        // Variable para almacenar el mensaje que se mostrará al usuario
        String mensaje;

        try {
            // Se carga el driver de conexión JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Se establece la conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Sentencia SQL para insertar un nuevo usuario en la tabla "usuarios"
            String sql = "INSERT INTO recursos_tecnologicos (no_inventario, descripcion, fecha_adquisicion, valor, localizacion, estado_equipo) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";
            
            // Se prepara la sentencia SQL para evitar inyección de código (seguridad)
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, no_inventario);
            ps.setString(2, descripcion);
            ps.setString(3, fecha_adquisicion);
            ps.setString(4, valor);
            ps.setString(5, localizacion);
            ps.setString(6, estado_equipo);
            
            // Se ejecuta la sentencia SQL y se obtiene el número de filas afectadas
            int filas = ps.executeUpdate();

            // Se cierran los recursos para liberar memoria
            ps.close();
            con.close();

            // Si al menos una fila fue insertada, el registro fue exitoso
            if (filas > 0) {
                mensaje = "✅ Recursos Tecnológico registrado correctamente.";
            } else {
                mensaje = "❌ Error al registrar el Recurso Tecnológico.";
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
        response.sendRedirect("registrar_recurso.jsp");
    }
}
