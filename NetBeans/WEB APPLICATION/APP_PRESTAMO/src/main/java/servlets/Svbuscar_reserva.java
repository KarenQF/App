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
@WebServlet("/Svbuscar_reserva")
public class Svbuscar_reserva extends HttpServlet {
    
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
        String busqueda = request.getParameter("id_reserva");
        // Lista donde se almacenarán los resultados de la búsqueda
        List<Map<String, String>> reservas = new ArrayList<>();

        // Validación: verificar que el campo no esté vacío
        if (busqueda == null || busqueda.trim().isEmpty()) { HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ Debes ingresar un ID de reserva para buscar.");
            response.sendRedirect("menu_gestion_reserva.jsp");
            return;
        }
        // Conexión a la base de datos y ejecución de la consulta
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Consulta SQL que busca por no_inventario o descripcon usando LIKE 
            String sql = "SELECT id_reserva, id_usuario, id_equipo, fecha_reserva, hora_inicio_reserva, hora_fin_reserva, estado_reserva\n" + "FROM reserva\n" + "WHERE id_reserva = ?\n";
            
            PreparedStatement ps = con.prepareStatement(sql); 
            ps.setInt(1, Integer.parseInt(busqueda)); 
            ResultSet rs = ps.executeQuery();

           // Procesar los resultados obtenidos 
               while (rs.next()) {Map<String, String> reserva = new HashMap<>(); 
               reserva.put("id_reserva", rs.getString("id_reserva")); 
               reserva.put("id_usuario", rs.getString("id_usuario")); 
               reserva.put("id_equipo", rs.getString("id_equipo")); 
               reserva.put("fecha_reserva", rs.getString("fecha_reserva")); 
               reserva.put("hora_inicio_reserva", rs.getString("hora_inicio_reserva")); 
               reserva.put("hora_fin_reserva", rs.getString("hora_fin_reserva")); 
               reserva.put("estado_reserva", rs.getString("estado_reserva")); 
               reservas.add(reserva); 
           }
      
           // Cerrar recursos 
           rs.close(); 
           ps.close(); 
           con.close();
           
           // Si no hay resultados, mostrar mensaje 
           HttpSession session = request.getSession(); 
           if (reservas.isEmpty()) { session.setAttribute("mensaje", "⚠️ No se encontró ninguna reserva con ese ID.");
           response.sendRedirect("menu_gestion_reserva.jsp");
           return; 
        } 

        // Mostrar los resultados en el JSP 
        request.setAttribute("reservas", reservas); 
        request.setAttribute("busqueda", busqueda); 
        request.getRequestDispatcher("menu_gestion_reserva.jsp").forward(request, response);
            } catch (Exception e) { 
        // Manejo de errores e.printStackTrace(); 
        HttpSession session = request.getSession(); 
        session.setAttribute("mensaje", "❌ Error al buscar la reserva: " + e.getMessage()); 
        response.sendRedirect("menu_gestion_reserva.jsp");
        } 
    } 
}