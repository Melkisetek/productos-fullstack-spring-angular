# Sistema de Gestión de Inventarios (Fullstack App)

Este proyecto es una **aplicación fullstack robusta** diseñada como un portafolio profesional para la gestión de productos. Implementa operaciones **CRUD (Create, Read, Update, Delete)** completas con validaciones de negocio en el lado del servidor y una interfaz de usuario reactiva y moderna. El objetivo principal es demostrar buenas prácticas y una estructura clara de desarrollo de software.

El ecosistema está construido bajo una arquitectura desacoplada utilizando un **Backend HTTP REST en java** y un **Frontend SPA (Single Page Application).**

## 🛠️ Tecnologías y Arquitectura

### Backend (`gestion-db-api`)

- **Java 17** junto con **Spring Boot 4.x**
- **Spring Web MVC** para exposición de endpoints RESTful.
- **Spring Data JPA** Para el mapeo y persistencia de datos.
- **Base de Datos** MariaDB
- **Maven Wrapper** (`mvnw`) para portabilidad en el despliegue. 

### Frontend (`frontend-productos`)

- **Angular 19** como framework SPA principal
- **Componentes Standalone** para una arquitectura modular y moderna.
- **HttpClient** para el consumo eficiente de la API REST.

## 🚀 Guía de Ejecución Local

Sigue estas instrucciones  paso a paso para clonar, configurar y levantar ambos entornos en tu computadora de manera simultánea.

### Prerrequisitos Básicos

Asegúrate de tener instaladas las siguientes herramientas globales:

- **Java Development Kit (JDK) 17** o superior
- **Node.js** (Versión LTS recomendada para Angular 19)
- **MariaDB Server** activo localmente

## ☕ 1. configuración del Backend (Spring Boot)

El proyecto incluye el **Maven Wrapper**, por lo que no necesitas Maven instalado globalmente de manera obligatoria.

1. **Configurar la Base de Datos**:
    Antes de arrancar el backend, asegúrate de crear una base de datos vacía en tu servidor MariaDB con el nombre `gestion_db_api`:
   
   ```bash
        CREATE DATABASE gestion_db_api;
   ```
   
   *El backend está configurado por defecto para conectarse con el usuario `root` y sin contraseña en el puerto `3306`. Si tus credenciales locales son distintas, puedes editarlas en `src/main/resources/application.properties`)*.

2. Abre tu terminal y navega hasta la carpeta del backend:
   
   ```bash
   cd gestion-db-api
   ```

3. Ejecuta el servidor de desarrollo utilizando el script empaquetado según tu sistema operativo:
   
   1. **En Windows (PowerShell):**
      
      ```powershell
       .\mvnw.cmd spring-boot:run
      ```
   2. **En Windows (Git Bash o CMD):**
      
      ```bash
      ./mvnw.cmd spring-boot:run
      ```
   3. **En Linux / macOS:**
      
      ```bash
      chmod +x mvnw
      ./mvnw spring-boot:run
      ```

El servidor backend iniciará por defecto en el puerto **`http://localhost:8080`** e Hibernate creará automáticamente las tablas necesarias gracias a la propiedad `ddl-auto=update`.



## 🅰️ 2. Configuración del Frontend (Angular 19)

1. Abre una nueva pestaña o ventana de la terminal y dirígete a la carpeta del cliente:
   
   ```bash
   cd frontend-productos
   ```

2. Instala la totalidad de las dependencias de Node definidas en el manifiesto `package.json`:
   
   ```bahs
   npm install
   ```

3. Inicia el servidor de desarrollo local del CLI de Angular:
   
   ```bash
   npx ng serve -o
   # O también mediante: npm start
   ```

La aplicación web compilará satisfactoriamente y estará disponible para pruebas en tu navegador en **`http://localhost:4200`**.

🎯 Buenas Prácticas Demostradas

- **Conventional Commits:** Historial de Git semántico, ordenado y autodocumentado.
- **Separación de Responsabilidades:** Configuración independiente de entornos utilizando múltiples archivos `.gitignore`.
- **Seguridad CORS:** Configuración en el Backend para permitir solicitudes HTTP de origen cruzado de manera controlada desde el puerto del cliente Angular.
- **Persistencia Automatizada:** Sincronización del modelo de datos de Spring Boot directo hacia MariaDB.



## 📄 **Licencia**

Este proyecto se encuentra bajo la **Licencia MIT**, garantizando su disponibilidad para modificaciones abiertas de código. Consulta el archivo `LICENSE` adjunto para obtener más detalles.