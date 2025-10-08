<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 
    Directiva JSP: define el tipo de contenido (HTML) y la codificación UTF-8
    Esto asegura que los acentos y caracteres especiales se vean correctamente.
-->

<!DOCTYPE html>
<html lang="en">
    <head>
        <!-- Configuración de metadatos de la página -->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Inicio - Préstamo de Recursos Tecnológicos</title>
        
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
        margin: 0;
        padding: 0;
        background-color: #ffffff;
        height: 100vh;
        display: flex;
        flex-direction: column;
    }
    
    /* Encabezado superior */
    header {
        background-color: #cd1f32;
        color: white;
        padding: 10px 10px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        flex-wrap: wrap; /* Permite que los elementos se envuelvan */
        gap: 15px; /* Espacio entre elementos cuando se envuelven */
        min-height: 80px; /* Altura mínima para evitar colapsos */
        width: 100%;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 10;
    }

    .logo-container {
        display: flex;
        align-items: center;
        min-width: 200px; /* Ancho mínimo para el logo */
    }

    .logo {
        height: 60px;
        max-width: 100%; /* Evita que la imagen se desborde */
        object-fit: contain; /* Mantiene la proporción de la imagen */
    }
    
    /* Caja del formulario de registro */
    .container {
        display: flex;
        justify-content: space-between;
        align-items: center; 
        width: 90%; 
        max-width: 900px; /* no se expande demasiado en pantallas grandes */
        margin: 0 auto 50px auto; /* espacio inferior */
        gap: 40px;
    }
    
    .login-container {
        flex: 1;
        min-width: 250px;
    }
    
    /* Contenedor principal */
    main {
        padding: 150px 100px 300px 50px; /* 80px = altura aproximada del header + margen */
        text-align: center;
        flex: 1; /* Permite que el main ocupe el espacio disponible */
    }
   
    button {
        width: 50%;
        padding: 10px;
        background-color: #cd1f32;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-top: 20px;
        font-size: 16px;
    }

    button:hover {
        background-color:#b01827;
        color: white;
    }

    .enlaces {
        margin-top: 15px;
    }

    .enlaces a {
        color: #000000;
        text-decoration: none;
        margin-top: 5px;
        width: 100%;
    }

    .input-group {
        position: relative;
        margin-bottom: 25px; /* separación entre campos */
    }

    .input-group input {
        width: 100%;
        padding: 12px 1px 10px 10px;
        border: 1px solid #ccc;
        border-radius: 8px;
        font-size: 14px;
    }

    .input-group label {
        position: absolute;
        top: -10px;
        left: 12px;
        background-color: white;
        font-size: 12px;
        padding: 0 5px;
        color: #888;
    }

    .input-group .icon {
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
        color: #555;
        cursor: pointer;
    }
    
    .error {
        color: red;
        padding: 8px;
        text-align: center;
        margin-bottom: 10px;
        font-weight: bold;
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
    
    <script>
        function redirectToIngreso() {
            window.location.href = "ingreso.jsp";
        }
    </script>
  
    </head>
    <body>
    <!-- Encabezado con el logo y menú usuario -->
        <header>
            <div class="logo-container">
                <img src="images/Captura.PNG" alt="Logo Universidad del Valle" class="logo">
            </div>
        </header>

    <!-- Contenido principal -->
    <main>
      <div class="container">
          <div>
              <img src="images/Logo Univalle.png" alt="Logo institucional" height="200">
              <h2>Préstamo de Recursos Tecnológicos</h2>
          </div>

          <form action="Svinicio_sesion" method="post" id="loginForm">
              <div class="login-container">
                  <div class="input-group">
                      <label for="usuario">Usuario:</label>
                      <input type="text" id="usuarioInput" name="usuario" placeholder="Ingrese su Correo Electrónico">
                      <i class="fas fa-user icon"></i>
                  </div>

                  <div class="input-group">
                      <label for="clave">Contraseña:</label>
                      <input type="password" id="clave" name="clave" placeholder="Ingrese su Contraseña">
                  </div>

                  <% if (request.getAttribute("error") != null) { %>
                      <div class="error">
                          <%= request.getAttribute("error") %>
                      </div>
                  <% } %>

                  <div class="enlaces">
                      <a href="#"><strong>¿Olvidaste la contraseña?</strong></a>
                  </div>

                  <button name="role" value="Clientes">Ingresar</button>
                  <div class="enlaces">
                      <strong>¿Es tu primera vez? <a href="registrar_usuario.jsp"> <span style="color: #cd1f32;">Regístrate</span></strong></a>
                  </div>
              </div>
          </form>
      </div>
    </main>

  <<!-- Pie de página institucional -->
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

</body>
</html>