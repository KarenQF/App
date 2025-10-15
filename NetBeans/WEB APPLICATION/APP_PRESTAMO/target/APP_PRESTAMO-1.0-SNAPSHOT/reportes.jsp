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
    <title>Reportes e Informes</title>
    
    <!-- Íconos de Font Awesome para el menú y botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- Estilos CSS internos -->
    
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    html, body {
        height: 100%;
        margin: 0;
        padding: 0;
    }

    /* Hace que el footer siempre quede abajo */
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
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 40px;
    }

    header img {
        height: 40px;
        margin-right: 10px;
    }

    header h1 {
        font-size: 20px;
        margin: 0;
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

    main {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start; /* o center si quieres centrar el contenido verticalmente */
        padding: 40px 20px;
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

    .container {
        max-width: 800px;
        margin: 80px auto;
        text-align: center;
    }
        
    select, button {
        padding: 10px;
        font-size: 15px;
        margin: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
    }

    button {
        cursor: pointer;
        transition: 0.3s;
    }

    .btn-visualizar {
        background-color: #fff;
        color: #c8102e;
        border: 2px solid #c8102e;
    }

    .btn-visualizar:hover {
        background-color: #c8102e;
        color: #fff;
    }

    .btn-pdf {
        background-color: #c8102e;
        color: white;
        border: none;
    }

    .btn-pdf:hover {
        background-color: #a40026;
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
    <h2>Reportes e Informes</h2>
    </div>

    <form action="Svreporte" method="POST">
        <label for="tipo">Buscar</label><br>
        <select name="tipo" id="tipo" required>
            <option value="">Seleccionar Opción</option>
            <option value="listado_usuarios">Listado de Usuarios</option>
            <option value="listado_reservas">Listado de Reservas</option>
            <option value="listado">Listado de Equipos en Préstamo</option>
            <option value="historico">Listado del Total de Equipos Tecnológicos</option>
        </select>
        <br>
        <button type="submit" class="btn-visualizar">Visualizar</button>
        <button type="button" class="btn-pdf">Descargar PDF</button>
    </form>

    <br>
    <a href="ingreso.jsp" class="volver">← Volver</a>

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
