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
@WebServlet("/Svbuscar_recurso")
public class Svbuscar_recurso extends HttpServlet {
    
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
        
        // Obtener el texto que el recurso ingresó en el campo de búsqueda
        String busqueda = request.getParameter("no_inventario");
        
        // Lista donde se almacenarán los resultados de la búsqueda
        List<Map<String, String>> recursos = new ArrayList<>();

        // Validación: verificar que el campo no esté vacío
        if (busqueda == null || busqueda.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ Debes ingresar un no. inventario para buscar.");
            response.sendRedirect("menu_gestion_recurso.jsp");
            return;
            }
        // Conexión a la base de datos y ejecución de la consulta
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer conexión
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Consulta SQL que busca por no_inventario o descripcon usando LIKE
            String sql = "SELECT id_equipo, no_inventario, descripcion, fecha_adquisicion, valor, localizacion, estado_equipo\n" + "FROM recursos_tecnologicos\n" + "WHERE no_inventario LIKE ?\n" + "   OR descripcion LIKE ?\n";


            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + busqueda + "%");
            ps.setString(2, "%" + busqueda + "%");

            ResultSet rs = ps.executeQuery();

            // Procesar los resultados obtenidos
            while (rs.next()) {
                Map<String, String> recurso = new HashMap<>();
                recurso.put("id_equipo", rs.getString("id_equipo"));
                recurso.put("no_inventario", rs.getString("no_inventario"));
                recurso.put("descripcion", rs.getString("descripcion"));
                recurso.put("fecha_adquisicion", rs.getString("fecha_adquisicion"));
                recurso.put("valor", rs.getString("valor"));
                recurso.put("localizacion", rs.getString("localizacion"));
                recurso.put("estado_equipo", rs.getString("estado_equipo"));
                recursos.add(recurso);
            }
                
            // Cerrar recursos
            rs.close();
            ps.close();
            con.close();

            // Si no hay resultados, mostrar mensaje
            HttpSession session = request.getSession();
            if (recursos.isEmpty()) {
                session.setAttribute("mensaje", "⚠️ No se encontraron recursos con ese criterio de búsqueda.");
                response.sendRedirect("menu_gestion_recurso.jsp");
                return;
            }

            // Mostrar los resultados en el JSP
            request.setAttribute("recursos", recursos);
            request.setAttribute("busqueda", busqueda);
            request.getRequestDispatcher("menu_gestion_recurso.jsp").forward(request, response);

        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al buscar recurso: " + e.getMessage());
            response.sendRedirect("menu_gestion_recurso.jsp");
        }
    }
}
