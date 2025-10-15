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
@WebServlet("/Svobtener_reserva")
public class Svobtener_reserva extends HttpServlet {
    
    // Datos de conexión a la base de datos MySQL  
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método GET: obtiene un recurso específico por su ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el parámetro "id_equipo"
        String idReserva = request.getParameter("id_reserva");
        
        // Validación: si no se envía el ID, redirigir al listado general
        if (idReserva == null || idReserva.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ No se recibió el ID de la reserva a editar.");
            return;
        }

        // Crear un mapa para guardar los datos del recurso
        Map<String, String> reserva = new HashMap<>();

        try {
            // Conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Consulta SQL
            String sql = "SELECT * FROM reserva WHERE id_reserva = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idReserva);

            ResultSet rs = ps.executeQuery();
            
            // Si se encuentra el recurso, guardar sus datos
            if (rs.next()) {
                reserva.put("id_reserva", rs.getString("id_reserva"));
                reserva.put("id_usuario", rs.getString("id_usuario"));
                reserva.put("id_equipo", rs.getString("id_equipo"));
                reserva.put("fecha_reserva", rs.getString("fecha_reserva"));
                reserva.put("hora_inicio_reserva", rs.getString("hora_inicio_reserva"));
                reserva.put("hora_fin_reserva", rs.getString("hora_fin_reserva"));
                reserva.put("estado_reserva", rs.getString("estado_reserva"));
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("mensaje", "⚠️ No se encontró la reserva con ID: " + idReserva);
                return;
            }
            
            // Cerrar recursos
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al obtener la reserva: " + e.getMessage());
            return;
        }
        
        // Guardar el recurso como atributo para el JSP
        request.setAttribute("reserva", reserva);
        
        // ✅ Reenviar a la vista de edición del recurso
        request.getRequestDispatcher("editar_reserva.jsp").forward(request, response);
    }

    // Método POST: redirige al GET para mantener un flujo único
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id_reserva");
        response.sendRedirect("Svobtener_reserva?id_reserva=" + id);
    }
}
