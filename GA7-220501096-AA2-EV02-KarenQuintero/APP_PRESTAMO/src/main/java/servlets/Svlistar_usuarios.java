package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/Svlistar_usuarios")
public class Svlistar_usuarios extends HttpServlet {

    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int registrosPorPagina = 5;
        int paginaActual = 1;

        if (request.getParameter("pagina") != null) {
            paginaActual = Integer.parseInt(request.getParameter("pagina"));
        }

        int offset = (paginaActual - 1) * registrosPorPagina;
        List<Map<String, String>> todosUsuarios = new ArrayList<>();
        int totalRegistros = 0;

        try {
            // Conectar
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");

            // Contar registros
            String countSql = "SELECT COUNT(*) AS total FROM usuarios";
            PreparedStatement psCount = con.prepareStatement(countSql);
            ResultSet rsCount = psCount.executeQuery();
            if (rsCount.next()) {
                totalRegistros = rsCount.getInt("total");
            }
            rsCount.close();
            psCount.close();
            System.out.println("Total de usuarios en BD: " + totalRegistros);

            // Consultar usuarios con paginación
            String sql = "SELECT id_usuario, cedula, nombres, apellidos, cargo, rol, email, telefono " +
                         "FROM usuarios ORDER BY id_usuario ASC LIMIT ? OFFSET ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, registrosPorPagina);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> usuario = new HashMap<>();
                usuario.put("id_usuario", rs.getString("id_usuario"));
                usuario.put("cedula", rs.getString("cedula"));
                usuario.put("nombres", rs.getString("nombres"));
                usuario.put("apellidos", rs.getString("apellidos"));
                usuario.put("cargo", rs.getString("cargo"));
                usuario.put("rol", rs.getString("rol"));
                usuario.put("email", rs.getString("email"));
                usuario.put("telefono", rs.getString("telefono"));
                todosUsuarios.add(usuario);
            }

            System.out.println("Usuarios recuperados en esta página: " + todosUsuarios.size());

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        int totalPaginas = (int) Math.ceil((double) totalRegistros / 5);

        request.setAttribute("todosUsuarios", todosUsuarios);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);

        request.getRequestDispatcher("menu_gestion_usuario.jsp").forward(request, response);
    }
}
