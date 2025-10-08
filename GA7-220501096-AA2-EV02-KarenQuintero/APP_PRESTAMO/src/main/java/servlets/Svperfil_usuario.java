package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/Svperfil_usuario")
public class Svperfil_usuario extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect("inicio.jsp");
            return;
        }

        String correo = (String) sesion.getAttribute("correo"); // guardaremos este dato al iniciar sesión

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM usuarios WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("cedula", rs.getString("cedula"));
                request.setAttribute("nombres", rs.getString("nombres"));
                request.setAttribute("apellidos", rs.getString("apellidos"));
                request.setAttribute("cargo", rs.getString("cargo"));
                request.setAttribute("rol", rs.getString("rol"));
                request.setAttribute("correo", rs.getString("email"));
                request.setAttribute("telefono", rs.getString("telefono"));
            }

            rs.close();
            ps.close();
            con.close();

            request.getRequestDispatcher("perfil_usuario.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "⚠️ Error al cargar perfil: " + e.getMessage());
            request.getRequestDispatcher("ingreso.jsp").forward(request, response);
        }
    }
}
