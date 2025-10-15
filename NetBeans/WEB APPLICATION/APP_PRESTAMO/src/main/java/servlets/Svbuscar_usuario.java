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
@WebServlet("/Svbuscar_usuario")
public class Svbuscar_usuario extends HttpServlet {
    
    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configuración de codificación para evitar errores con acentos o caracteres especiales
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // Obtener el texto que el usuario ingresó en el campo de búsqueda
        String busqueda = request.getParameter("cedula");
        
        // Lista donde se almacenarán los resultados de la búsqueda
        List<Map<String, String>> usuarios = new ArrayList<>();

        // Validación: verificar que el campo no esté vacío
        if (busqueda == null || busqueda.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ Debes ingresar una cédula o nombre para buscar.");
            response.sendRedirect("menu_gestion_usuario.jsp");
            return;
            
        }
        // Conexión a la base de datos y ejecución de la consulta
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer conexión
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Consulta SQL que busca por cédula, nombres o apellidos usando LIKE
            String sql = "SELECT id_usuario, cedula, nombres, apellidos, cargo, rol, email, telefono\n" + "FROM usuarios\n" + "WHERE cedula LIKE ?\n" + "   OR nombres LIKE ?\n" + "   OR apellidos LIKE ?\n";


            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + busqueda + "%");
            ps.setString(2, "%" + busqueda + "%");
            ps.setString(3, "%" + busqueda + "%");

            ResultSet rs = ps.executeQuery();

            // Procesar los resultados obtenidos
            while (rs.next()) {
                Map<String, String> usuario = new HashMap<>();
                usuario.put("id_usuario", rs.getString("id_usuario"));
                usuario.put("cedula", rs.getString("cedula"));
                usuario.put("nombres", rs.getString("nombres"));
                usuario.put("apellidos", rs.getString("apellidos"));
                usuario.put("cargo", rs.getString("cargo"));
                usuario.put("rol", rs.getString("rol"));
                usuario.put("email", rs.getString("email"));
                usuario.put("telefono", rs.getString("telefono"));
                usuarios.add(usuario);
            }
                
            // Cerrar recursos
            rs.close();
            ps.close();
            con.close();

            // Si no hay resultados, mostrar mensaje
            HttpSession session = request.getSession();
            if (usuarios.isEmpty()) {
                session.setAttribute("mensaje", "⚠️ No se encontraron usuarios con ese criterio de búsqueda.");
                response.sendRedirect("menu_gestion_usuario.jsp");
                return;

            }

            // Mostrar los resultados en el JSP
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("busqueda", busqueda);
            request.getRequestDispatcher("menu_gestion_usuario.jsp").forward(request, response);

        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al buscar usuario: " + e.getMessage());
            response.sendRedirect("menu_gestion_usuario.jsp");
        }
    }
}
