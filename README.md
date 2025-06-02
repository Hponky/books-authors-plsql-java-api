# Aplicación de Gestión de Libros y Autores: BooksApi

Este proyecto implementa una aplicación backend para la administración de libros y sus respectivos autores, cumpliendo con las siguientes condiciones:

-   **Backend realizado en PL/SQL:** La lógica de negocio principal y la persistencia de datos se gestionan mediante paquetes PL/SQL en una base de datos Oracle.
-   **Capa de servicios expuesta mediante API REST:** Se ha desarrollado una API RESTful utilizando Java 21 y Spring Boot 3.5 (con Maven) para interactuar con la lógica de negocio implementada en PL/SQL.

La aplicación permite las operaciones de inserción, edición, consulta y borrado de información, tanto para libros como para autores, incluyendo la validación de dependencias para asegurar la integridad de los datos.

## Tecnologías Utilizadas

-   **Lenguaje de Programación (API):** Java 21
-   **Framework (API):** Spring Boot 3.5
-   **Gestor de Dependencias:** Apache Maven
-   **Base de Datos:** Oracle Database (utilizando la imagen Docker `gvenzl/oracle-free`)
-   **Lenguaje de Backend (BD):** PL/SQL
-   **Conectividad a BD (Java):** Spring JDBC
-   **Contenerización:** Docker, Docker Compose
-   *(Opcional si aplica) Pruebas: JUnit 5, Mockito, Spring Boot Test, Testcontainers*

## Estructura del Proyecto

-   `books-api/`: Directorio del proyecto Spring Boot (código Java, `pom.xml`, `Dockerfile`).
-   `db_scripts/`: Contiene los scripts SQL para la inicialización de la base de datos.
    -   `01_create_user.sql`: Creación del usuario de la aplicación y otorgamiento de privilegios.
    -   `02_create_schema.sql`: Creación de tablas (`AUTORES`, `LIBROS`) y paquetes PL/SQL.
-   `docker-compose.yml`: Archivo para orquestar los servicios de la aplicación y la base de datos.
-   `README.md`: Este archivo.

## Cómo Ejecutar la Aplicación (Usando Docker Compose - Recomendado)

Este método levanta la base de datos Oracle y la aplicación Spring Boot en contenedores Docker.

### Prerrequisitos

-   **Docker Desktop** instalado y en ejecución.
-   **Apache Maven** (para construir el proyecto si se hacen cambios locales antes de `docker-compose build`).
-   **JDK 21** (para desarrollo local si no se usa Docker para la app Java).

### Pasos de Ejecución

1.  **Clona este repositorio.**
2.  **Navega al directorio raíz del proyecto** (donde se encuentra `docker-compose.yml`).
3.  **Configura las Contraseñas:**
    *   Revisa y modifica la variable `ORACLE_PASSWORD` en `docker-compose.yml` para el servicio `oracle-db`. Esta será la contraseña para `SYS`/`SYSTEM`.
    *   Modifica la variable `SPRING_DATASOURCE_PASSWORD` en `docker-compose.yml` para el servicio `books-api`.
    *   Asegúrate de que la contraseña para `BOOKS_APP` en el script `db_scripts/01_create_user.sql` coincida con `SPRING_DATASOURCE_PASSWORD`.
    *   **Es crucial cambiar las contraseñas por defecto/ejemplo por unas seguras.**
4.  **Levanta los Servicios:**
    ```bash
    docker-compose up --build
    ```
    *   La opción `--build` reconstruirá la imagen de la aplicación Java si hay cambios.
    *   La primera vez, la descarga de la imagen de Oracle y la inicialización de la base de datos (ejecutando los scripts de `db_scripts/`) pueden tardar varios minutos.
    *   La aplicación API estará disponible en `http://localhost:8080` (o el puerto que hayas mapeado).
5.  **Para detener los servicios:**
    ```bash
    docker-compose down
    ```
    Para eliminar también los volúmenes (¡esto borrará los datos de la BD!):
    ```bash
    docker-compose down -v
    ```

## Ejecución Alternativa (API Local conectando a BD Oracle Externa)

Si deseas ejecutar la aplicación Java localmente y conectarla a una instancia de Oracle Database ya existente (no gestionada por el `docker-compose` de este proyecto):

1.  **Prepara tu Instancia Oracle:**
    *   Asegúrate de tener una instancia de Oracle Database accesible.
    *   Conéctate como `SYS` o `SYSTEM` a tu PDB y ejecuta el script `db_scripts/01_create_user.sql` para crear el usuario `BOOKS_APP` y sus privilegios.
    *   Conéctate como `BOOKS_APP` y ejecuta el script `db_scripts/02_create_schema.sql` para crear las tablas y paquetes.
2.  **Configura la Aplicación Java:**
    *   Abre el archivo `src/main/resources/application.properties` dentro del directorio `books-api/`.
    *   Modifica las propiedades `spring.datasource.url`, `spring.datasource.username`, y `spring.datasource.password` para que apunten a tu instancia de Oracle externa.
3.  **Ejecuta la Aplicación:**
    *   Navega al directorio `books-api/`.
    *   Ejecuta:
        ```bash
        ./mvnw spring-boot:run
        ```

## Endpoints de la API

La API REST proporciona los siguientes endpoints para la gestión de autores y libros (base path `/api/v1`):

### Autores (`/api/autores`)
-   `GET /`: Obtiene una lista de todos los autores.
-   `GET /{id}`: Obtiene un autor por su ID.
-   `POST /`: Crea un nuevo autor. Cuerpo de la solicitud: JSON con datos del autor.
-   `PUT /{id}`: Actualiza un autor existente. Cuerpo de la solicitud: JSON con datos del autor.
-   `DELETE /{id}`: Elimina un autor (valida dependencias).

### Libros (`/api/libros`)
-   `GET /`: Obtiene una lista de todos los libros (con información del autor).
-   `GET /{id}`: Obtiene un libro por su ID.
-   `POST /`: Crea un nuevo libro. Cuerpo de la solicitud: JSON con datos del libro (incluyendo `idAutor`).
-   `PUT /{id}`: Actualiza un libro existente. Cuerpo de la solicitud: JSON con datos del libro.
-   `DELETE /{id}`: Elimina un libro.
-   `GET /autor/{autorId}`: Obtiene libros por el ID de un autor.
-   `GET /isbn/{isbn}`: Obtiene un libro por su ISBN.

## Demostración Funcional

Para interactuar con la API una vez que esté ejecutándose:

1.  Utiliza una herramienta como **Postman** o **Insomnia**.
2.  Ejemplo para consultar todos los autores:
    -   **Método:** `GET`
    -   **URL:** `http://localhost:8080/api/autores`
    -   Envía la solicitud y observa la respuesta JSON.

*(Opcional: Sección de Pruebas y Calidad del Código si aplica)*
> ## Pruebas
> Para ejecutar las pruebas unitarias y de integración:
> ```bash
> cd books-api
> ./mvnw test
> ```
