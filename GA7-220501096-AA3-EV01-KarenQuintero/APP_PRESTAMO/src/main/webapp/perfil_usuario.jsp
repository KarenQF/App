<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 
    Directiva JSP: define el tipo de contenido (HTML) y la codificación UTF-8
    Esto asegura que los acentos y caracteres especiales se vean correctamente.
-->

<!DOCTYPE html>
<html lang="es">
    <head>
        <!-- Configuración de metadatos de la página -->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Perfil Usuario - Universidad del Valle</title>

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
            font-family: Helvetica;
            background-color: #fff;
            display: flex;
            flex-direction: column;
        }

        /* Encabezado superior */
        header {
            background-color: #cd1f32; /* Rojo institucional */
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

        /* Contenedor del icono de ayuda y menú del usuario */
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

        /* Menú del usuario(perfil, cerrar sesión, etc.) */
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

        /* Menú desplegable */
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

        /* Clase activa para mostrar el menú */
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
            text-align: center;
            flex: 1; /* Permite que el main crezca y empuje el footer hacia abajo */
        }

        /* Caja del formulario */
        .container {
            max-width: 500px;
            margin: 20px 20px 20px 500px;
            padding: 30px;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
        } 

        /* Título del formulario */
        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        /* Estilos del formulario */
        form {
            background-color: white;
            max-width: 600px;
            margin: 40px auto;
            padding: 30px;
            border-radius: 10px;
        }

        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            background-color: #c8102e;
            color: white;
            border: none;
            padding: 10px;
            margin-top: 20px;
            width: 100%;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover {
            background-color: #a60f24;
        }

        /* Pie de página (footer) */
        footer {
            background-color: #cd1f32;
            color: white;
            padding: 30px;
            text-align: center;
            width: 100%;
            margin-top: auto;
        }

        .footer-content {
            max-width: 800px;
            margin: 0 auto;
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
            <!-- Icono de ayuda que redirige al manual -->
            <div class="help-icon" title="Ayuda">
                <i class="fas fa-question-circle"></i>
            </div>

            <!-- Menú desplegable con nombre del usuario -->
            <div class="user-menu" id="userMenu">
                <span class="user-name">
                    <!-- Muestra el nombre del usuario almacenado en sesión -->
                    <%= session.getAttribute("usuario") != null ? session.getAttribute("usuario") : "Invitado" %>
                </span>
                <i class="fas fa-chevron-down"></i>

                <!-- Opciones del menú -->
                <div class="dropdown" id="dropdownMenu">
                    <a href="Svperfil_usuario"><i class="fas fa-user"></i> Perfil</a>
                    <a href="#"><i class="fas fa-cog"></i> Configuración</a>
                    <a href="Svcerrar_sesion"><i class="fas fa-sign-out-alt"></i> Cerrar sesión</a>
                </div>
            </div>
        </div>
    </header>
                
    <!-- Contenedor del perfil -->
    <div class="container">  
        <h2>Perfil del Usuario</h2>
        <form>
            <!-- Los valores se cargan dinámicamente desde el servlet con request.setAttribute -->
            <label>Cédula:</label>
            <input type="text" name="cedula" value="<%= request.getAttribute("cedula") %>" readonly>

            <label>Nombres:</label>
            <input type="text" name="nombres" value="<%= request.getAttribute("nombres") %>">

            <label>Apellidos:</label>
            <input type="text" name="apellidos" value="<%= request.getAttribute("apellidos") %>">

            <label>Cargo:</label>
            <input type="text" name="cargo" value="<%= request.getAttribute("cargo") %>">

            <label>Rol:</label>
            <input type="text" name="rol" value="<%= request.getAttribute("rol") %>" readonly>

            <label>Correo:</label>
            <input type="email" name="correo" value="<%= request.getAttribute("correo") %>">

            <label>Teléfono:</label>
            <input type="text" name="telefono" value="<%= request.getAttribute("telefono") %>">

            <button type="submit" disabled>Actualizar (Próximamente)</button>
        </form>
    </div>
        
    <!-- Pie de página institucional -->
    <footer>
        <div class="footer-content">
            <div class="contact-info">
                <p class="address"><i class="fa-regular fa-envelope"></i> recursostecnologicos.tulua@correounivalle.edu.co </p>
                <p class="address"><i class="fas fa-map-marker-alt"></i> Calle 43 No 43-33</p>
                <p class="address"><i class="fas fa-city"></i> Tuluá, Colombia</p>
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

        // Redirige a la página de ayuda (manual)
        document.querySelector('.help-icon').addEventListener('click', function() {
        window.location.href = 'manual.jsp';
        });
    </script>
    
</body>
</html>
