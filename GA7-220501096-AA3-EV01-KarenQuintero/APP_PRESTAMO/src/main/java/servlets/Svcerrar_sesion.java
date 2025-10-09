// Paquete donde se encuentra la clase del servlet
package servlets;

// Importaciones necesarias para manejar servlets y conexiones JDBC
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

// Anotación que indica la URL con la que se accede al servlet
@WebServlet("/Svcerrar_sesion")
public class Svcerrar_sesion extends HttpServlet {

    // Método que se ejecuta cuando el formulario usa el método GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la sesión actual (si existe)
        HttpSession session = request.getSession(false);

        // Invalidar la sesión para cerrar completamente
        if (session != null) {
            session.invalidate();
        }

         // Redirigir al usuario a la página de inicio de sesión
        response.sendRedirect("inicio.jsp");
    }
}
