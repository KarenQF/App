package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/Sveliminar_usuario")
public class Sveliminar_usuario extends HttpServlet {

    // Datos de conexión a la base de datos
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Establecer codificación
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Recibir el ID del usuario desde el formulario
        String idUsuarioParam = request.getParameter("id_usuario");

        HttpSession session = request.getSession(); // 👈 para guardar mensajes temporales

        if (idUsuarioParam == null || idUsuarioParam.isEmpty()) {
            session.setAttribute("mensaje", "⚠️ ID de usuario no proporcionado.");
            response.sendRedirect("menu_gestion_usuario.jsp");
            return;
        }

        int idUsuario = Integer.parseInt(idUsuarioParam);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    session.setAttribute("mensaje", "✅ Usuario eliminado correctamente.");
                } else {
                    session.setAttribute("mensaje", "⚠️ No se encontró el usuario a eliminar.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("mensaje", "❌ Error al eliminar el usuario: " + e.getMessage());
        }

        // Redirección para evitar reenvío y limpiar el formulario
        response.sendRedirect("menu_gestion_usuario.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Servlet que elimina un usuario del sistema de préstamo.";
    }
}
