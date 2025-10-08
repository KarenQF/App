package servlets;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/Svinicio_sesion")
public class Svinicio_sesion extends HttpServlet {

    private static final String USUARIO_VALIDO = "Karen";
    private static final String CLAVE_VALIDA = "123";

    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // Validar usuario estático (administrador general, por ejemplo)
        if (USUARIO_VALIDO.equals(usuario) && CLAVE_VALIDA.equals(clave)) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            sesion.setAttribute("rol", "Administrador");
            request.getRequestDispatcher("ingreso.jsp").forward(request, response);
            return;
        }

        // Validar usuario desde la base de datos
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();
                
            if (rs.next()) {
                // Usuario encontrado
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", rs.getString("nombres"));
                sesion.setAttribute("correo", rs.getString("email"));
                sesion.setAttribute("rol", rs.getString("rol"));
                request.getRequestDispatcher("ingreso.jsp").forward(request, response);              
            } else {
                // Usuario no encontrado
                request.setAttribute("error", "❌ Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("inicio.jsp").forward(request, response);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "⚠️ Error al iniciar sesión: " + e.getMessage());
            request.getRequestDispatcher("inicio.jsp").forward(request, response);
        }
    }
}
