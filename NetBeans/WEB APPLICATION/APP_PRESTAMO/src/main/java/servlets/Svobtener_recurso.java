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
@WebServlet("/Svobtener_recurso")
public class Svobtener_recurso extends HttpServlet {
    
    // Datos de conexión a la base de datos MySQL  
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método GET: obtiene un recurso específico por su ID
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el parámetro "id_equipo"
        String idEquipo = request.getParameter("id_equipo");
        
        // Validación: si no se envía el ID, redirigir al listado general
        if (idEquipo == null || idEquipo.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "⚠️ No se recibió el ID del recurso a editar.");
            return;
        }

        // Crear un mapa para guardar los datos del recurso
        Map<String, String> recurso = new HashMap<>();

        try {
            // Conexión a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Consulta SQL
            String sql = "SELECT * FROM recursos_tecnologicos WHERE id_equipo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idEquipo);

            ResultSet rs = ps.executeQuery();
            
            // Si se encuentra el recurso, guardar sus datos
            if (rs.next()) {
                recurso.put("id_equipo", rs.getString("id_equipo"));
                recurso.put("no_inventario", rs.getString("no_inventario"));
                recurso.put("descripcion", rs.getString("descripcion"));
                recurso.put("fecha_adquisicion", rs.getString("fecha_adquisicion"));
                recurso.put("valor", rs.getString("valor"));
                recurso.put("localizacion", rs.getString("localizacion"));
                recurso.put("estado_equipo", rs.getString("estado_equipo"));
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("mensaje", "⚠️ No se encontró el recurso con ID: " + idEquipo);
                return;
            }
            
            // Cerrar recursos
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al obtener el recurso: " + e.getMessage());
            return;
        }
        
        // Guardar el recurso como atributo para el JSP
        request.setAttribute("recurso", recurso);
        
        // ✅ Reenviar a la vista de edición del recurso
        request.getRequestDispatcher("editar_recurso.jsp").forward(request, response);
    }

    // Método POST: redirige al GET para mantener un flujo único
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id_equipo");
        response.sendRedirect("Svobtener_recurso?id_equipo=" + id);
    }
}
