// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svinicio_sesion")
public class Svinicio_sesion extends HttpServlet {

    // Credenciales estáticas para un usuario administrador general
    private static final String USUARIO_VALIDO = "Karen";
    private static final String CLAVE_VALIDA = "123";

    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";
    
    // Método que se ejecuta cuando el formulario usa el método POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Se obtienen los valores del formulario (usuario y contraseña)
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // Validar usuario estático (administrador general, por ejemplo)
        if (USUARIO_VALIDO.equals(usuario) && CLAVE_VALIDA.equals(clave)) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            sesion.setAttribute("rol", "Administrador");
              
            // Redirige al usuario al panel principal
            request.getRequestDispatcher("ingreso.jsp").forward(request, response);
            return;
        }

        // Validar usuario desde la base de datos
        try {
            // Se carga el driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Se establece la conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Consulta SQL para verificar si existe un usuario con las credenciales ingresadas
            String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            
            // Se ejecuta la consulta
            ResultSet rs = ps.executeQuery();
            
            // Validar si las credenciales son correctas   
            if (rs.next()) {
                // Si el usuario existe, se crea una sesión y se guardan sus datos
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", rs.getString("nombres"));
                sesion.setAttribute("correo", rs.getString("email"));
                sesion.setAttribute("rol", rs.getString("rol"));
                
                // Redirige al usuario al panel de inicio o menú principal
                request.getRequestDispatcher("ingreso.jsp").forward(request, response);              
            } else {
                // Si no se encontró el usuario, se envía un mensaje de error
                request.setAttribute("error", "❌ Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("inicio.jsp").forward(request, response);
            }
            
            // Se cierran los recursos para liberar memoria
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            //Manejo de errores de conexión o SQL
            e.printStackTrace();
            request.setAttribute("error", "⚠️ Error al iniciar sesión: " + e.getMessage());
            request.getRequestDispatcher("inicio.jsp").forward(request, response);
        }
    }
}
