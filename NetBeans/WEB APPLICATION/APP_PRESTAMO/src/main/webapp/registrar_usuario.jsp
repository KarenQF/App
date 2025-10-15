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
        <title>Registro Usuario - Universidad del Valle</title>

        <!-- Íconos de Font Awesome para el menú y botones -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <!-- Estilos CSS internos -->
        <style>
        /* Reinicio general de márgenes, rellenos y modelo de caja */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box
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
            background-color: #cd1f32; /* Rojo institucional */
            color: white;
            padding: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
        }

        header img {
            height: 55px; /* Tamaño del logo */
        }

        /* Contenedor principal */
        main {
            flex: 1; /* ocupa el espacio disponible */
            display: flex;
            justify-content: center; /* centra horizontalmente */
            align-items: center; /* centra verticalmente */
            padding: 40px 20px;
        }

        /* Caja del formulario de registro */
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

        /* Estilos de las etiquetas y campos del formulario */
        form label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        form input,
        form select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }

        /* Contenedor de botones */
        .buttons {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
        }

        /* Botón principal (Crear) */
        .btn-crear {
            width: 45%;
            padding: 10px 25px;
            background-color: #cd1f32;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .btn-crear:hover {
            background-color: #b01827;
        }

        /* Botón secundario (Limpiar) */
        .btn-limpiar {
            width: 45%;
            padding: 10px 25px;
            background-color: #f4f4f4;
            color: black;
            border: 1px solid #ccc;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .btn-limpiar:hover {
            background-color: #ddd;
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

    <!-- Encabezado con el logo -->
    <header>
        <div style="text-align: left;">
            <img src="images/Captura.PNG" alt="Logo de la institución">
        </div>
    </header>

    <!-- Contenedor principal del formulario -->
    
    <main>
    <div class="container">
        <h2>Registro Usuario</h2>

        <% 
            // Bloque JSP para mostrar mensajes temporales
            // Si existe un mensaje en la sesión (por ejemplo, "Usuario registrado correctamente"), se muestra.
            String mensaje = (String) session.getAttribute("mensaje");
            if (mensaje != null) {
        %>
            <!-- Caja visual del mensaje -->
            <div style="background-color:#e7f3fe; color:#0c5460; border:1px solid #b8daff; padding:10px; border-radius:5px; margin-bottom:10px;">
                <%= mensaje %>
            </div>
        <%
            // Elimina el mensaje de la sesión después de mostrarlo, para que no se repita al recargar.
            session.removeAttribute("mensaje");
            }
        %>

        <!-- Formulario para registrar un usuario -->
        <form action="Svregistrar_usuario" method="post" autocomplete="off">
            
            <!-- Campos de entrada -->
            <label for="cedula">Cédula</label>
            <input type="text" id="cedula" name="cedula" placeholder="Ingrese su cédula">

            <label for="nombres">Nombres</label>
            <input type="text" id="nombres" name="nombres" placeholder="Ingrese sus nombres">
            
            <label for="apellidos">Apellidos</label>
            <input type="text" id="apellidos" name="apellidos" placeholder="Ingrese sus apellidos">

            <label for="cargo">Cargo</label>
            <select id="cargo" name="cargo">
                <option value="">Seleccione Opción</option>
                <option value="Docente">Docente</option>
                <option value="Estudiante">Estudiante</option>
                <option value="Personal Administrativo">Personal Administrativo</option>
                <option value="Personal Externo">Personal Externo</option>
            </select>
            
            <label for="rol">Rol</label>
            <select id="rol" name="rol">
                <option value="">Seleccione Opción</option>
                <option value="Usuario">Usuario</option>
                <option value="Administrador">Administrador</option>
            </select>

            <label for="correo">Correo Electrónico</label>
            <input type="email" id="correo" name="correo" placeholder="usuario@correo.com">

            <label for="telefono">Número de Contacto</label>
            <input type="tel" id="telefono" name="telefono" placeholder="Ej: 3001234567">

            <label for="contrasena">Contraseña</label>
            <input type="password" id="contrasena_registro" name="contrasena_registro" placeholder="Ingrese una contraseña" autocomplete="new-password">

            <!-- Botones de acción -->
            <div class="buttons">
                <button type="submit" class="btn-crear">Crear</button>
                <button type="reset" class="btn-limpiar">Limpiar</button>
            </div>

            <!-- Enlace para regresar al inicio -->
            <a href="inicio.jsp" class="volver"><i class="fa-solid fa-arrow-left"></i> Volver</a>
        </form>
    </div>
    </main>

    <!-- Pie de página institucional -->
    <footer>
        <div class="footer-content">
            <div class="contact-info">
                <p class="address"><i class="fa-regular fa-envelope"></i> recursostecnologicos.tulua@correounivalle.edu.co </p>
                <p class="address"><i class="fas fa-map-marker-alt"></i> Calle 43 No 43-33</p>
                <p class="address"><i class="fas fa-city"></i> Tuluá, Colombia</p>
            </div>
            <div class="divider"></div>
            <p class="copyright">
                Universidad del Valle &copy; 2025 - Todos los derechos reservados
            </p>
        </div>
    </footer>

</body>
</html>
