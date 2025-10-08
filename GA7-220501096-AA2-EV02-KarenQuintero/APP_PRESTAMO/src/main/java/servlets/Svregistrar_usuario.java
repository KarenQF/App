package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/Svregistrar_usuario")
public class Svregistrar_usuario extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String cedula = request.getParameter("cedula");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String cargo = request.getParameter("cargo");
        String rol = request.getParameter("rol");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String contrasena = request.getParameter("contrasena");

        String mensaje;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO usuarios (cedula, nombres, apellidos, cargo, rol, email, telefono, contrasena) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cedula);
            ps.setString(2, nombres);
            ps.setString(3, apellidos);
            ps.setString(4, cargo);
            ps.setString(5, rol);
            ps.setString(6, correo);
            ps.setString(7, telefono);
            ps.setString(8, contrasena);

            int filas = ps.executeUpdate();
            ps.close();
            con.close();

            if (filas > 0) {
                mensaje = "✅ Usuario registrado correctamente.";
            } else {
                mensaje = "❌ Error al registrar el usuario.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "⚠️ Error en el registro: " + e.getMessage();
        }

        // Guardamos el mensaje en sesión temporal
        HttpSession session = request.getSession();
        session.setAttribute("mensaje", mensaje);

        // Redirigimos (no forward) para evitar reenvío del formulario
        response.sendRedirect("registrar_usuario.jsp");
    }
}
