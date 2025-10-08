// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svperfil_usuario")
public class Svperfil_usuario extends HttpServlet {

    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";
    
    // Método que se ejecuta cuando el formulario usa el método GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Se obtiene la sesión actual del usuario (false = no crea una nueva si no existe)
        HttpSession sesion = request.getSession(false);

        // Si no hay sesión o el atributo "usuario" no está definido, se redirige al inicio de sesión
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("inicio.jsp");
            return; // se detiene la ejecución del método
        }
        
        // Se obtiene el correo del usuario almacenado en sesión (al iniciar sesión)
        String correo = (String) sesion.getAttribute("correo"); // guardaremos este dato al iniciar sesión

        try {
            // Se carga el controlador JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Se establece la conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Consulta SQL para obtener los datos del usuario según su correo electrónico
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
                        
            // Se ejecuta la consulta y se almacenan los resultados
            ResultSet rs = ps.executeQuery();

            // Si el usuario existe, se guardan sus datos en atributos del request
            if (rs.next()) {
                request.setAttribute("cedula", rs.getString("cedula"));
                request.setAttribute("nombres", rs.getString("nombres"));
                request.setAttribute("apellidos", rs.getString("apellidos"));
                request.setAttribute("cargo", rs.getString("cargo"));
                request.setAttribute("rol", rs.getString("rol"));
                request.setAttribute("correo", rs.getString("email"));
                request.setAttribute("telefono", rs.getString("telefono"));
            }
            
            // Se cierran los recursos para liberar memoria
            rs.close();
            ps.close();
            con.close();

            // Se reenvía la información al JSP encargado de mostrar el perfil del usuario
            request.getRequestDispatcher("perfil_usuario.jsp").forward(request, response);

        } catch (Exception e) {
            // Si ocurre un error durante el proceso, se imprime en consola
            e.printStackTrace();
            
            // Se guarda un mensaje de error y se redirige al JSP de ingreso
            request.setAttribute("mensaje", "⚠️ Error al cargar perfil: " + e.getMessage());
            request.getRequestDispatcher("ingreso.jsp").forward(request, response);
        }
    }
}
