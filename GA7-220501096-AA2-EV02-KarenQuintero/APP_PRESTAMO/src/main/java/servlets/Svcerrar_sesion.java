package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/Svcerrar_sesion")
public class Svcerrar_sesion extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la sesión actual (si existe)
        HttpSession session = request.getSession(false);

        // Invalidar la sesión para cerrar completamente
        if (session != null) {
            session.invalidate();
        }

        // Redirigir al inicio
        response.sendRedirect("inicio.jsp");
    }
}
