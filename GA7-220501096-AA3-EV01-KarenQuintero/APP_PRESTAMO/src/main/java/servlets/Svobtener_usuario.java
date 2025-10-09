// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svobtener_usuario")
public class Svobtener_usuario extends HttpServlet {
    
    // Datos de conexión a la base de datos MySQL  
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Se obtiene el parámetro "id_usuario" desde la URL o el formulario
        String idUsuario = request.getParameter("id_usuario");
         
        // Se crea un mapa (clave, valor) para guardar los datos del usuario
        Map<String, String> usuario = new HashMap<>();

        try {
            // Se carga el driver JDBC para conectar con MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Se establece la conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Consulta SQL para obtener la información del usuario por su ID
            String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idUsuario);

            // Se ejecuta la consulta y se obtiene el resultado
            ResultSet rs = ps.executeQuery();
            
            // Si se encuentra el usuario, se almacenan sus datos en el mapa
            if (rs.next()) {
                usuario.put("id_usuario", rs.getString("id_usuario"));
                usuario.put("cedula", rs.getString("cedula"));
                usuario.put("nombres", rs.getString("nombres"));
                usuario.put("apellidos", rs.getString("apellidos"));
                usuario.put("cargo", rs.getString("cargo"));
                usuario.put("rol", rs.getString("rol"));
                usuario.put("email", rs.getString("email"));
                usuario.put("telefono", rs.getString("telefono"));
                usuario.put("contrasena", rs.getString("contrasena"));
            }
            
            // Se cierran los recursos para liberar memoria
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            // Si ocurre un error en el proceso, se imprime en consola para depuración
            e.printStackTrace();
        }
        
        // Se guarda el objeto 'usuario' como atributo del request
        // para que los datos puedan ser accedidos desde el JSP "editar_usuario.jsp"
        request.setAttribute("usuario", usuario);
        
        // Se reenvía la solicitud al JSP encargado de mostrar el formulario de edición
        request.getRequestDispatcher("editar_usuario.jsp").forward(request, response);
    }

    // Método doPost: se implementa para evitar el error HTTP 405
    // si alguien intenta enviar un formulario con método POST a esta URL
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Se obtiene el ID del usuario desde el formulario
        String id = request.getParameter("id_usuario");
        // Se redirige al mismo servlet pero usando el método GET
        // De esta forma se mantiene un único flujo de consulta
        response.sendRedirect("Svobtener_usuario?id_usuario=" + id);
    }
}
