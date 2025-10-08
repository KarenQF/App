package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/Svactualizar_usuario")
public class Svactualizar_usuario extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("id_usuario");
        String cedula = request.getParameter("cedula");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String cargo = request.getParameter("cargo");
        String rol = request.getParameter("rol");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String contrasena = request.getParameter("contrasena");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "UPDATE usuarios SET cedula=?, nombres=?, apellidos=?, cargo=?, rol=?, email=?, telefono=?, contrasena=? WHERE id_usuario=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, cargo);
            ps.setString(5, rol);
            ps.setString(6, email);
            ps.setString(7, telefono);
            ps.setString(8, contrasena);
            ps.setInt(9, Integer.parseInt(id));

            int filas = ps.executeUpdate();

            ps.close();
            con.close();

            // Mostrar mensaje temporal y redirigir
            HttpSession session = request.getSession();
            if (filas > 0) {
                session.setAttribute("mensaje", "✅ Usuario actualizado correctamente.");
            } else {
                session.setAttribute("mensaje", "⚠️ No se encontró el usuario para actualizar.");
            }

            // Redirige al listado principal (puede ser tu servlet de listar)
            response.sendRedirect("Svlistar_usuarios");

        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("mensaje", "❌ Error al actualizar el usuario: " + e.getMessage());
            response.sendRedirect("Svlistar_usuarios");
        }
    }
}
