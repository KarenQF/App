<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- 
    Directiva JSP: define el tipo de contenido (HTML) y la codificación UTF-8
    Esto asegura que los acentos y caracteres especiales se vean correctamente.
-->

<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<!--  Importa las clases List y Map de Java para manejar colecciones de datos de usuarios. -->

<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Configuración de metadatos de la página -->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Gestión de Usuarios - Universidad del Valle</title>
        
        <!-- Íconos de Font Awesome para el menú y botones -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- Estilos CSS internos -->
    <style>   
    /* Reinicio general de márgenes, rellenos y modelo de caja */
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    /* Estilos generales del cuerpo */
    body {
        font-family: Helvetica;; 
        margin: 0;
        padding: 0;
        background-color: #fff;
        display: flex;
        flex-direction: column;
    }

    /* Encabezado superior */
    header {
        background-color: #cd1f32;
        color: white;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 40px;
    }

    .logo-container {
        display: flex;
        align-items: center;
    }

    .logo {
        height: 60px;
    }

    .header-controls {
        display: flex;
        align-items: center;
        gap: 20px;
    }

    .help-icon {
        color: white;
        font-size: 1.5rem;
        cursor: pointer;
        transition: transform 0.3s;
    }

    .help-icon:hover {
        transform: scale(1.1);
    }

    .user-menu {
        position: relative;
        display: flex;
        align-items: center;
        gap: 8px;
        cursor: pointer;
        padding: 8px 12px;
        border-radius: 4px;
        transition: background-color 0.3s;
    }

    .user-menu:hover {
        background-color: rgba(255, 255, 255, 0.1);
    }

    .user-name {
        font-weight: bold;
    }

    .dropdown {
        position: absolute;
        top: 100%;
        right: 0;
        background: white;
        color: #333;
        border-radius: 6px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        min-width: 180px;
        z-index: 100;
        display: none;
        overflow: hidden;
    }

    .dropdown.active {
        display: block;
    }

    .dropdown a {
        display: block;
        padding: 12px 16px;
        text-decoration: none;
        color: #333;
        border-bottom: 1px solid #eee;
        transition: background-color 0.2s;
    }

    .dropdown a:last-child {
        border-bottom: none;
    }

    .dropdown a:hover {
        background-color: #f5f5f5;
    }

    /* Contenedor principal */
    main {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        min-height: 70vh;
    }  

    .gestion {
        max-width: 900px;
        margin: 0 auto;
    }

    .gestion-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 25px;
    }

    /* Botón (Actualizar) */
    .btn-add {
        background-color: #c8102e;
        color: white;
        border: none;
        border-radius: 5px;
        padding: 12px 20px;
        font-size: 15px;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .btn-add:hover {
        background-color: #a10c23;
    }

    .search-box {
        position: relative;
        width: 350px;
        margin: 20px auto;
    }

    .search-box input {
        width: 100%;
        padding: 10px 35px 10px 12px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .search-box i {
        position: absolute;
        right: 10px;
        top: 10px;
        color: #555;
    }

    .user-list details {
        background-color: #e9e9e9;
        border-radius: 4px;
        margin-bottom: 8px;
        padding: 10px 15px;
        cursor: pointer;
    }

    .user-list summary {
        font-weight: 600;
        list-style: none;
    }

    .user-list details p {
        margin-top: 20px;
        color: #333;
    }

    .bottom-nav {
        margin-top: 25px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .search-container {
        display: flex;
        align-items: center;
        background: #fff;
        border-radius: 50px;
        box-shadow: 0 3px 10px rgba(0, 0, 0, 0.15);
        overflow: hidden;
        transition: all 0.3s ease;
        padding: 5px 10px;
    }

    .search-container:focus-within {
        box-shadow: 0 4px 15px rgba(200, 16, 46, 0.3);
    }

    .search-container input {
        border: none;
        outline: none;
        padding: 10px 15px;
        flex: 1;
        font-size: 15px;
        border-radius: 50px;
        background: transparent;
    }

    .search-container button {
        border: none;
        color: #fff;
        width: 42px;
        height: 42px;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        transition: transform 0.2s ease, background-color 0.3s ease;
    }

    .search-container button:hover {
        transform: scale(1.1);
    }

    .search-container button i {
        font-size: 18px;
    }

    .user-list {
        margin-top: 20px;
        display: flex;
        flex-direction: column;
        gap: 10px;
    }

    .user-card {
        background-color: #f1f1f1;
        padding: 15px;
        border-radius: 8px;
        text-align: left;
        font-size: 14px;
    }

    .btn-edit {
        background-color: #c8102e;
        color: white;
        border: none;
        border-radius: 5px;
        padding: 12px 20px;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .btn-edit:hover {
        background-color: #a10c23;
    }

    .btn-delete {
        background-color: #777; /* gris inicial */
        color: white;
        border: none;
        border-radius: 5px;
        padding: 12px 20px;
        cursor: pointer;
        transition: all 0.3s ease;
        margin-left: 5px; /* separa los botones */
    }

    .btn-delete:hover {
        background-color: #a10c23; /* rojo oscuro al pasar */
    }

    /* Enlace para volver a la página anterior */
    .volver {
        display: block;
        margin-top: 50px;
        color: #cd1f32;
        font-weight: bold;
        text-decoration: none;
        text-align: left;
    }

    .volver:hover {
        text-decoration: underline;
    }

    /* Pie de página (footer) */
    footer {
        background-color: #cd1f32;
        color: white;
        padding: 30px;
        text-align: center;
        width: 100%;
        flex-shrink: 0; /* Evita que el footer se encoja */
        margin-top: auto; /* Empuja el footer hacia abajo */
    }

    .footer-content {
        max-width: 800px;
        margin: 0 auto;
        }
    .footer-title {
        font-weight: 600;
        margin-bottom: 15px;
        font-size: 1.2rem;
    }

    .contact-info {
        margin-bottom: 20px;
        }

    .contact-info p {
        margin: 8px 0;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 10px;
    }

    .address {
        font-style: normal;
    }

    .divider {
        height: 1px;
        background-color: rgba(255, 255, 255, 0.3);
        margin: 20px 0;
    }

    .copyright {
        font-size: 0.9rem;
        opacity: 0.8;
    }

    </style>   
    
  
    </head>
    <body>

    <!-- Encabezado con el logo y menú usuario -->
    <header>
        <div class="logo-container">
            <img src="images/Captura.PNG" alt="Logo Universidad del Valle" class="logo">
        </div>
        <div class="header-controls">
            <div class="help-icon" title="Ayuda">
                <i class="fas fa-question-circle"></i>
            </div>
            <div class="user-menu" id="userMenu">
                <span class="user-name">
                    <%= session.getAttribute("usuario") != null ? session.getAttribute("usuario") : "Invitado" %>
                </span>
                <i class="fas fa-chevron-down"></i>
                <div class="dropdown" id="dropdownMenu">
                    <a href="Svperfil_usuario"><i class="fas fa-user"></i> Perfil</a>
                    <a href="#"><i class="fas fa-cog"></i> Configuración</a>
                    <a href="Svcerrar_sesion"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a>
                </div>
            </div>
        </div>
    </header>

    <main>
        <section class="gestion">
            <div class="gestion-header">
                <h2>Gestión de Usuarios</h2>
            </div>
            <%
                HttpSession sesion = request.getSession(false);
                String mensaje = null;
                if (sesion != null) {
                    mensaje = (String) sesion.getAttribute("mensaje");
                    if (mensaje != null) {
                        sesion.removeAttribute("mensaje"); // 💡 se elimina al mostrar
                    }
                }
            %>
            <% if (mensaje != null) { %>
            <div style="background-color:#e7f3fe; color:#0c5460; border:1px solid #b8daff; padding:10px; border-radius:5px; margin-bottom:10px;">
                <%= mensaje %>
            </div>
            <% } %>
            
            <script>
            // Desaparece automáticamente después de 4 segundos
                setTimeout(() => {
                    const msg = document.querySelector('div[style*="text-align: center"]');
                    if (msg) msg.style.display = 'none';
                }, 4000);
            </script>

            <div class="gestion-header">
                <button class="btn-add" onclick="location.href= 'registrar_usuario.jsp'">Añadir Usuario</button>
            </div>
            <!-- Barra de búsqueda -->
            <div class="search-box">
                <form action="Svbuscar_usuario" method="get" class="search-container">
                    <input type="text" name="cedula" placeholder="🔍 Buscar usuario por cédula, nombre o apellido..." required>
                    <button type="submit" title="Buscar">
                        <i class="fas fa-magnifying-glass"></i>
                    </button>
                </form>
            </div>
            
            <%
                List<Map<String, String>> usuarios = (List<Map<String, String>>) request.getAttribute("usuarios");
                String busqueda = (String) request.getAttribute("busqueda");
                if (busqueda != null) {
            %>
                <h3>Resultados de "<%= busqueda %>":</h3>
                <div class="user-list">
            <%
                if (usuarios != null && !usuarios.isEmpty()) {
                for (Map<String, String> u : usuarios) {
            %>
                <div class="user-card">
                    <strong><%= u.get("nombres") + " " + u.get("apellidos") %></strong><br>
                    <small>Cédula: <%= u.get("cedula") %></small><br>
                    <small>Cargo: <%= u.get("cargo") %></small><br>
                    <small>Rol: <%= u.get("rol") %></small><br>
                    <small>Email: <%= u.get("email") %></small><br>
                    <small>Tel: <%= u.get("telefono") %></small><br><br>

                    <form action="Svobtener_usuario" method="get" style="display:inline;">
                        <input type="hidden" name="id_usuario" value="<%= u.get("id_usuario") %>">
                        <button type="submit" class="btn-edit"><i class="fas fa-edit"></i> Editar</button>
                    </form>
                        
                    <form action="Sveliminar_usuario" method="post" style="display:inline;" onsubmit="return confirm('¿Estás segura de eliminar este usuario?');">
                        <input type="hidden" name="id_usuario" value="<%= u.get("id_usuario") %>">
                        <button type="submit" class="btn-delete"><i class="fas fa-trash"></i> Eliminar</button>
                    </form>
                </div>     
                
            <%
                }
                } else {
            %>
                <p>No se encontraron resultados.</p>
            <%
                }
            %>
                    </div>
            <%
                }
            %>
            
            <a href="ingreso.jsp" class="volver"><i class="fa-solid fa-arrow-left"></i> Volver</a>
            
            </div>
        </section>
    </main>

    <!-- Pie de página institucional -->
    <footer>
        <div class="footer-content">
            <div class="contact-info">
                <p class="address"><i class="fa-regular fa-envelope"></i>  recursostecnologicos.tulua@correounivalle.edu.co </p>
                <p class="address"><i class="fas fa-map-marker-alt"></i>  Calle 43 No 43-33</p>
                <p class="address"><i class="fas fa-city"></i>  Tuluá, Colombia</p>
            </div>
            <div class="divider"></div>
            <p class="copyright">Universidad del Valle &copy; 2025 - Todos los derechos reservados</p>
        </div>
    </footer>

    <!-- Script: Manejo del menú y redirecciones -->
    <script>
        // Abre o cierra el menú desplegable al hacer clic en el nombre del usuario
        document.getElementById('userMenu').addEventListener('click', function() {
        document.getElementById('dropdownMenu').classList.toggle('active');
        });

        // Cierra el menú si se hace clic fuera del área del menú
        document.addEventListener('click', function(event) {
            const userMenu = document.getElementById('userMenu');
            const dropdownMenu = document.getElementById('dropdownMenu');
            
            if (!userMenu.contains(event.target)) {
                dropdownMenu.classList.remove('active');
            }
        });

        
        // Función para manejar los clics de los botones
        function redirectTo(page) {
            // Aquí puedes redirigir a la página correspondiente
            window.location.href = page;
        }

        // Redirige a la página de ayuda (manual)
        document.querySelector('.help-icon').addEventListener('click', function() {
        window.location.href = 'manual.jsp';
        });
    </script>
    
</body>
</html>
