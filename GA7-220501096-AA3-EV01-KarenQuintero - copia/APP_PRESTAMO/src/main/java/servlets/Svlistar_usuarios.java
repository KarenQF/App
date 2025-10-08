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
@WebServlet("/Svlistar_usuarios")
public class Svlistar_usuarios extends HttpServlet {
     
    // Datos de conexión a la base de datos MySQL
    private final String URL = "jdbc:mysql://localhost:3306/sistema_prestamo";
    private final String USER = "root";
    private final String PASSWORD = "Kquintero92*";

    // Método que se ejecuta cuando el formulario usa el método GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Número de registros que se mostrarán por página
        int registrosPorPagina = 5;
        // Página actual por defecto
        int paginaActual = 1;

        // Si el parámetro "pagina" existe, se toma su valor (desde el enlace o formulario)
        if (request.getParameter("pagina") != null) {
            paginaActual = Integer.parseInt(request.getParameter("pagina"));
        }
        
        // Cálculo del desplazamiento (offset) para la consulta SQL según la página actual
        int offset = (paginaActual - 1) * registrosPorPagina;

        // Lista donde se almacenarán los datos de todos los usuarios recuperados
        List<Map<String, String>> todosUsuarios = new ArrayList<>();
        
        // Variable para almacenar el total de registros en la base de datos
        int totalRegistros = 0;

        try {
            // Cargar el driver JDBC para MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establecer conexión con la base de datos
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos");

            // Consulta: Contar todos los registros de la tabla
            String countSql = "SELECT COUNT(*) AS total FROM usuarios";
            PreparedStatement psCount = con.prepareStatement(countSql);
            ResultSet rsCount = psCount.executeQuery();
            
            // Se obtiene el número total de registros encontrados
            if (rsCount.next()) {
                totalRegistros = rsCount.getInt("total");
            }
            
            // Se cierran los recursos de la consulta de conteo
            rsCount.close();
            psCount.close();
            System.out.println("Total de usuarios en BD: " + totalRegistros);

            // Consultar: Obtener usuarios con paginación
            String sql = "SELECT id_usuario, cedula, nombres, apellidos, cargo, rol, email, telefono " +
                         "FROM usuarios ORDER BY id_usuario ASC LIMIT ? OFFSET ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            // Se asignan los valores para la paginación
            ps.setInt(1, registrosPorPagina); // cantidad de registros por página
            ps.setInt(2, offset);             // desplazamiento inicial

            ResultSet rs = ps.executeQuery();
            
            // Recorre cada registro y guarda los datos en un mapa (clave, valor)
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
                
                // Se agrega cada usuario a la lista general
                todosUsuarios.add(usuario);
            }

            System.out.println("Usuarios recuperados en esta página: " + todosUsuarios.size());

            // Se cierran los recursos abiertos
            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            // Captura cualquier error en la conexión o ejecución SQL
            e.printStackTrace();
        }
        
        // Cálculo del número total de páginas según los registros totales y los que se muestran por página
        int totalPaginas = (int) Math.ceil((double) totalRegistros / 5);

        // Se almacenan los atributos necesarios para el JSP
        request.setAttribute("todosUsuarios", todosUsuarios);
        request.setAttribute("paginaActual", paginaActual);
        request.setAttribute("totalPaginas", totalPaginas);
        
        // Se reenvía la solicitud al JSP encargado de mostrar la lista de usuarios
        request.getRequestDispatcher("menu_gestion_usuario.jsp").forward(request, response);
    }
}
