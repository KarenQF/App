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
        <title>Bienvenido - Universidad del Valle</title>
        
         <!-- Íconos de Font Awesome para el menú y botones -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    
    <!-- Estilos CSS internos -->
    <style>
    
    /* Estilos generales del cuerpo */
    
    html, body {
      overflow-x: hidden; 
    height: 100%;
    margin: 0;
    padding: 0;
    }   
    
    body {
        font-family: Helvetica;
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }
    /* Encabezado superior */
    header {
        background-color: #cd1f32;
        color: white;
        padding: 15px 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center; /* centra verticalmente el contenido */
        text-align: center;
        padding: 40px 20px;
    }
     
    .grid-buttons {
        display: grid;
        max-width: 400px;
        width: 100%;
        margin: 0 auto;
        margin-bottom: 30px;
    }
        
    .grid-buttons button {
        background-color: #c8102e;
        color: white;
        padding: 40px 20px;
        border: none;
        border-radius: 8px;
        cursor: pointer;
        margin-right: 15px;
        margin-top: 15px;
        width: 100%;
        font-size: 20px;
    }

    .grid-buttons button:hover {
        background-color: #a20b24;
    }
    
    /* Pie de página (footer) */
    footer {
        background-color: #cd1f32;
        color: white;
        padding: 30px;
        text-align: center;
        width: 100%;
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

    <main>
        <h1>Bienvenid@!</h1>
        <h1>¡Hola, <%= session.getAttribute("usuario") != null ? session.getAttribute("usuario") : "Invitado" %>!</h1>
        <div class="grid-buttons">
            <button class="grid-button" onclick="redirectTo('menu_gestion_usuario.jsp')">Gestión Usuarios</button>
            <button class="grid-button" onclick="redirectTo('menu_gestion_reserva_prestamo.jsp')">Gestión Reservas / Préstamos</button>
            <button class="grid-button" onclick="redirectTo('menu_gestion_recurso.jsp')">Gestión Recursos Tecnológicos</button>
            <button class="grid-button" onclick="redirectTo('reportes.jsp')">Reportes e Informes</button><br>
        </div>
    </main>

    <footer >
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
        // Toggle dropdown menu
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
