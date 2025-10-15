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
        <title>Editar Usuario</title>
        
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
        min-height: 100vh; /* asegura que el body ocupe toda la pantalla */
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
 
    header img {
        height: 55px;
    }

    /* Contenedor principal */
        main {
            flex: 1; /* ocupa el espacio disponible */
            display: flex;
            justify-content: center; /* centra horizontalmente */
            align-items: center; /* centra verticalmente */
            padding: 40px 20px;
        }

    /* Caja del formulario */
        .container {
            max-width: 500px;
            width: 100%;
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
        background: white;
        padding: 30px;
        border-radius: 8px;
        width: 400px;
    }
    
    form label {
        display: block;
        margin-top: 15px;
        font-weight: bold;
    }
    
    input, select {
        width: 100%;
        padding: 10px;
        margin-bottom: 12px;
        border-radius: 5px;
        border: 1px solid #ccc;
    }
    
    button {
        background: #c8102e;
        color: white;
        padding: 10px 15px;
        border: none;
        cursor: pointer;
        font-size: 16px;
    }
    
    button:hover {
        background: #a10c23;
    }
        
    .alerta-exito {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
        padding: 10px 15px;
        border-radius: 6px;
        margin-bottom: 15px;
        animation: fadeOut 3s 3s forwards;
    }

    @keyframes fadeOut {
        to { opacity: 0; display: none; }
    }
        
    .volver {
        display: block;
        margin-top: 20px;
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
        margin-top: auto; /* Empuja el footer hacia abajo */
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
        
        <!-- Contenedor -->
        <main>
        <div class="container">  
        <h2>Editar Usuario</h2>
        <%
            String mensaje = (String) session.getAttribute("mensaje");
            if (mensaje != null) {
        %>
            <div style="background-color:#e7f3fe; color:#0c5460; border:1px solid #b8daff; padding:10px; border-radius:5px; margin-bottom:10px;">
                <%= mensaje %>
            </div>
        <%
                // Elimina el mensaje de la sesión después de mostrarlo
                session.removeAttribute("mensaje");
            }
        %>
        
        <form action="Svactualizar_usuario" method="post">
       
        <input type="hidden" name="id_usuario" value="${usuario.id_usuario}">
        <label>Cédula:</label>
        <input type="text" name="cedula" value="${usuario.cedula}" required>

        <label>Nombres:</label>
        <input type="text" name="nombres" value="${usuario.nombres}" required>

        <label>Apellidos:</label>
        <input type="text" name="apellidos" value="${usuario.apellidos}" required>

        <label>Cargo:</label>
        <select name="cargo">
            <option ${usuario.cargo == 'Seleccione Opción' ? 'selected' : ''}>Seleccione Opción</option>
            <option ${usuario.cargo == 'Docente' ? 'selected' : ''}>Docente</option>
            <option ${usuario.cargo == 'Estudiante' ? 'selected' : ''}>Estudiante</option>
            <option ${usuario.cargo == 'Personal Administrativo' ? 'selected' : ''}>Personal Administrativo</option>
            <option ${usuario.cargo == 'Personal Externo' ? 'selected' : ''}>Personal Externo</option>
        </select>

        <label>Rol:</label>
        <select name="rol">
            <option ${usuario.rol == 'Seleccione Opción' ? 'selected' : ''}>Seleccione Opción</option>
            <option ${usuario.rol == 'Usuario' ? 'selected' : ''}>Usuario</option>
            <option ${usuario.rol == 'Administrador' ? 'selected' : ''}>Administrador</option>
        </select>

        <label>Email:</label>
        <input type="email" name="email" value="${usuario.email}" required>

        <label>Teléfono:</label>
        <input type="text" name="telefono" value="${usuario.telefono}" required>
        
        <label>Contrasena:</label>
        <input type="text" name="contrasena" value="${usuario.contrasena}">

        <button type="submit">Actualizar</button>
        </form>
        
        <a href="menu_gestion_usuario.jsp" class="volver"><i class="fa-solid fa-arrow-left"></i> Volver</a>
        </div>
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

