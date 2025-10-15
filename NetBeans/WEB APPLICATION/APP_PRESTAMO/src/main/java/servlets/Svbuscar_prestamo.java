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
@WebServlet("/Svbuscar_prestamo")
public class Svbuscar_prestamo extends HttpServlet {
    
    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método GET
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configuración de codificación para evitar errores con acentos o caracteres especiales
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // Obtener el texto que el recurso ingresó en el campo de búsqueda
        String busqueda = request.getParameter("id_prestamo");
        // Lista donde se almacenarán los resultados de la búsqueda
        List<Map<String, String>> prestamos = new ArrayList<>();

        // Validación: verificar que el campo no esté vacío
        if (busqueda == null || busqueda.trim().isEmpty()) { HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ Debes ingresar un ID de préstamo para buscar.");
            response.sendRedirect("menu_gestion_prestamo.jsp");
            return;
        }
        // Conexión a la base de datos y ejecución de la consulta
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Consulta SQL que busca por no_inventario o descripcon usando LIKE 
            String sql = "SELECT id_prestamo, id_usuario, id_equipo, fecha_prestamo, hora_inicio_prestamo, descripcion_actividad, estado_prestamo\n" + "FROM prestamo\n" + "WHERE id_prestamo = ?\n";
            
            PreparedStatement ps = con.prepareStatement(sql); 
            ps.setInt(1, Integer.parseInt(busqueda)); 
            ResultSet rs = ps.executeQuery();

           // Procesar los resultados obtenidos 
               while (rs.next()) {Map<String, String> prestamo = new HashMap<>(); 
               prestamo.put("id_prestamo", rs.getString("id_prestamo")); 
               prestamo.put("id_usuario", rs.getString("id_usuario")); 
               prestamo.put("id_equipo", rs.getString("id_equipo")); 
               prestamo.put("fecha_prestamo", rs.getString("fecha_prestamo")); 
               prestamo.put("hora_inicio_prestamo", rs.getString("hora_inicio_prestamo")); 
               prestamo.put("descripcion_actividad", rs.getString("descripcion_actividad")); 
               prestamo.put("estado_prestamo", rs.getString("estado_prestamo")); 
               prestamos.add(prestamo); 
           }
      
           // Cerrar recursos 
           rs.close(); 
           ps.close(); 
           con.close();
           
           // Si no hay resultados, mostrar mensaje 
           HttpSession session = request.getSession(); 
           if (prestamos.isEmpty()) { session.setAttribute("mensaje", "⚠️ No se encontró ningun préstamo con ese ID.");
           response.sendRedirect("menu_gestion_prestamo.jsp");
           return; 
        } 

        // Mostrar los resultados en el JSP 
        request.setAttribute("prestamos", prestamos); 
        request.setAttribute("busqueda", busqueda); 
        request.getRequestDispatcher("menu_gestion_prestamo.jsp").forward(request, response);
            } catch (Exception e) { 
        // Manejo de errores e.printStackTrace(); 
        HttpSession session = request.getSession(); 
        session.setAttribute("mensaje", "❌ Error al buscar el préstamo: " + e.getMessage()); 
        response.sendRedirect("menu_gestion_prestamo.jsp");
        } 
    } 
}