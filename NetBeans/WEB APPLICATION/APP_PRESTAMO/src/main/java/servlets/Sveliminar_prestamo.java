// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Sveliminar_prestamo")
public class Sveliminar_prestamo extends HttpServlet {

    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Establecer codificación
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Recibir el ID del usuario desde el formulario
        String idPrestamoParam = request.getParameter("id_prestamo");
        
        // Crear o recuperar la sesión actual (para mostrar mensajes de confirmación o error)
        HttpSession session = request.getSession(); // 👈 para guardar mensajes temporales

        //Validación del parámetro recibido
        // Si el ID no fue enviado o está vacío, se envía un mensaje de advertencia y se redirige al menú principal
        if (idPrestamoParam == null || idPrestamoParam.isEmpty()) {
            session.setAttribute("mensaje", "ID de la reserva no proporcionado.");
            response.sendRedirect("menu_gestion_reserva.jsp");
            return;
        }
        // Convertir el ID recibido (que llega como texto) a un número entero
        int idPrestamo = Integer.parseInt(idPrestamoParam);

        // Conexión y eliminación del usuario
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Sentencia SQL para eliminar un usuario según su ID
            String sql = "DELETE FROM prestamo WHERE id_prestamo = ?";
            
            // Se usa PreparedStatement para evitar inyección SQL
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idPrestamo); // Asignar el valor del ID en la consulta
                
                // Ejecutar la sentencia y obtener el número de filas afectadas
                int filasAfectadas = ps.executeUpdate();
                
                //Verificar si se eliminó correctamente
                if (filasAfectadas > 0) {
                    
                    // Si el usuario existía y fue eliminado
                    session.setAttribute("mensaje", "✅ Préstamo eliminado correctamente.");
                } else {
                    // Si no se encontró el usuario con el ID proporcionado
                    session.setAttribute("mensaje", "⚠️ No se encontró el préstamo a eliminar.");
                }
            }

        } catch (SQLException e) {
             // Manejo de errores SQL o de conexión
            e.printStackTrace();
            session.setAttribute("mensaje", "❌ Error al eliminar el préstamo: " + e.getMessage());
        }

        // Redirección para evitar reenvío y limpiar el formulario
        response.sendRedirect("menu_gestion_prestamo.jsp");
    }

    //Método opcional de descripción del servlet
    @Override
    public String getServletInfo() {
        return "Servlet que elimina un préstamo del sistema de préstamo.";
    }
}
