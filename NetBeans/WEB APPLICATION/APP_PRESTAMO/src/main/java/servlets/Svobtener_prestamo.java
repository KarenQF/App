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
@WebServlet("/Svobtener_prestamo")
public class Svobtener_prestamo extends HttpServlet {
    
    // Datos de conexión a la base de datos MySQL  
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método GET: obtiene un recurso específico por su ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el parámetro "id_equipo"
        String idPrestamo = request.getParameter("id_prestamo");
        
        // Validación: si no se envía el ID, redirigir al listado general
        if (idPrestamo == null || idPrestamo.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ No se recibió el ID del préstamo a editar.");
            return;
        }

        // Crear un mapa para guardar los datos del recurso
        Map<String, String> prestamo = new HashMap<>();

        try {
            // Conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Consulta SQL
            String sql = "SELECT * FROM prestamo WHERE id_prestamo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idPrestamo);

            ResultSet rs = ps.executeQuery();
            
            // Si se encuentra el recurso, guardar sus datos
            if (rs.next()) {
                prestamo.put("id_prestamo", rs.getString("id_prestamo"));
                prestamo.put("id_usuario", rs.getString("id_usuario"));
                prestamo.put("id_equipo", rs.getString("id_equipo"));
                prestamo.put("fecha_prestamo", rs.getString("fecha_prestamo"));
                prestamo.put("hora_inicio_prestamo", rs.getString("hora_inicio_prestamo"));
                prestamo.put("descripcion_actividad", rs.getString("descripcion_actividad"));
                prestamo.put("estado_prestamo", rs.getString("estado_prestamo"));
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("mensaje", "⚠️ No se encontró el préstamo con ID: " + idPrestamo);
                return;
            }
            
            // Cerrar recursos
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al obtener el préstamo: " + e.getMessage());
            return;
        }
        
        // Guardar el prestamo como atributo para el JSP
        request.setAttribute("prestamo", prestamo);
        
        // ✅ Reenviar a la vista de edición del prestamo
        request.getRequestDispatcher("editar_prestamo.jsp").forward(request, response);
    }

    // Método POST: redirige al GET para mantener un flujo único
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id_prestamo");
        response.sendRedirect("Svobtener_prestamo?id_prestamo=" + id);
    }
}

