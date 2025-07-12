Foro API
Una API REST para la gestión de tópicos en un foro, construida con Spring Boot. 
Permite a los usuarios registrarse, autenticarse, crear, listar, actualizar y eliminar tópicos, con funcionalidades de paginación y filtrado.

🚀 Tecnologías Utilizadas
Spring Boot: Framework principal para el desarrollo de la aplicación.

Spring Data JPA: Para la persistencia de datos y el acceso a la base de datos.

Hibernate: Implementación de JPA.

MySQL: Base de datos relacional utilizada.

Flyway: Herramienta de migración de bases de datos para gestionar el esquema.

Spring Security: Para la autenticación y autorización de usuarios, incluyendo JWT.

Auth0 Java JWT: Librería para la generación y validación de JSON Web Tokens (JWT).

Lombok: Para reducir el código repetitivo (boilerplate) en las clases de modelo.

Jakarta Validation: Para la validación de datos de entrada.

Springdoc OpenAPI UI: Para la generación automática de documentación de la API (Swagger UI).

⚙️ Configuración del Entorno
Asegúrate de tener instalado lo siguiente:

JDK 21 o superior.

MySQL Server (versión 8.0+ recomendada).

Maven (para la gestión de dependencias).

Un IDE como IntelliJ IDEA (recomendado) o VS Code.

▶️ Ejecución del Proyecto
Clona el repositorio (si aún no lo has hecho).

Abre el proyecto en tu IDE (IntelliJ IDEA, VS Code).

Configura las propiedades en application.properties como se indicó anteriormente.

Asegúrate de que tu servidor MySQL esté corriendo.

La aplicación se iniciará en http://localhost:8080.

🔒 Seguridad y Autenticación (JWT)
Esta API utiliza Spring Security y JSON Web Tokens (JWT) para la autenticación.

Los usuarios deben iniciar sesión en el endpoint /login para obtener un JWT.

Este JWT debe ser incluido en el encabezado Authorization de las solicitudes a los endpoints protegidos (como /topicos) con el prefijo Bearer.
Ejemplo: Authorization: Bearer <tu_token_jwt>

El token JWT expira a las 2 horas de su emisión.

🗺️ Endpoints de la API
Autenticación
POST /login

Descripción: Autentica a un usuario y devuelve un JWT si las credenciales son válidas.

Requiere Autenticación: No

Cuerpo de la Solicitud (JSON):

{
    "usuario": "nombre_de_usuario",
    "contrasena": "tu_contrasena"
}

Respuesta Exitosa (200 OK - JSON):

{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgRWR3aW4ubWVkIiwic3ViIjoibmljb2xhcyIsImV4cCI6MTY3ODU2NzQyOX0.tu_jwt_aqui"
}

Gestión de Tópicos
Todos los endpoints de /topicos requieren autenticación con un JWT válido.

POST /topicos

Descripción: Registra un nuevo tópico en el foro.

Requiere Autenticación: Sí (Bearer Token)

Cuerpo de la Solicitud (JSON):

{
    "titulo": "Título único del tópico",
    "mensaje": "Mensaje único del tópico",
    "autor": "Nombre del autor",
    "curso": "Nombre del curso"
}

Respuesta Exitosa (201 Created - JSON):

{
    "id": 1,
    "titulo": "Título único del tópico",
    "mensaje": "Mensaje único del tópico",
    "fechaCreacion": "2025-07-11T20:00:00",
    "estado": "1",
    "autor": "Nombre del autor",
    "curso": "Nombre del curso"
}

(La URL de ubicación del recurso creado se devuelve en el encabezado Location).

GET /topicos

Descripción: Lista todos los tópicos con paginación y opciones de filtrado.

Requiere Autenticación: Sí (Bearer Token)

Parámetros de Consulta (Opcionales):

page: Número de página (por defecto 0).

size: Cantidad de elementos por página (por defecto 10).

sort: Campo por el que ordenar y dirección (ej. fechaCreacion,asc o titulo,desc).

curso: Filtra por el nombre exacto del curso.

anio: Filtra por el año de creación del tópico.

Ejemplos de URL:

GET /topicos

GET /topicos?page=0&size=5

GET /topicos?sort=fechaCreacion,desc

GET /topicos?curso=Programacion%20Avanzada

GET /topicos?anio=2024

GET /topicos?curso=Bases%20de%20Datos&anio=2023&page=0&size=5&sort=titulo,asc

Respuesta Exitosa (200 OK - JSON):

{
    "content": [
        {
            "id": 1,
            "titulo": "Título del Tópico 1",
            "mensaje": "Mensaje del Tópico 1",
            "fechaCreacion": "2025-07-11T20:00:00",
            "estado": "1",
            "autor": "Autor 1",
            "curso": "Curso A"
        },
        // ... otros tópicos
    ],
    "pageable": { /* ... */ },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": { /* ... */ },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}

GET /topicos/{id}

Descripción: Obtiene los detalles de un tópico específico por su ID.

Requiere Autenticación: Sí (Bearer Token)

Parámetros de Ruta:

id: ID del tópico (ej. 1).

Ejemplo de URL: GET /topicos/1

Respuesta Exitosa (200 OK - JSON):

{
    "id": 1,
    "titulo": "Título del Tópico",
    "mensaje": "Mensaje del Tópico",
    "fechaCreacion": "2025-07-11T20:00:00",
    "estado": "1",
    "autor": "Autor del Tópico",
    "curso": "Curso del Tópico"
}

Respuesta de Error (404 Not Found): Si el tópico no existe.

PUT /topicos

Descripción: Actualiza un tópico existente. Solo se actualizarán los campos proporcionados.

Requiere Autenticación: Sí (Bearer Token)

Cuerpo de la Solicitud (JSON):

{
    "id": 1,
    "titulo": "Nuevo título (opcional)",
    "mensaje": "Nuevo mensaje (opcional)",
    "autor": "Nuevo autor (opcional)",
    "curso": "Nuevo curso (opcional)"
}

Respuesta Exitosa (200 OK - JSON):

{
    "id": 1,
    "titulo": "Título actualizado",
    "mensaje": "Mensaje actualizado",
    "fechaCreacion": "2025-07-11T20:00:00",
    "estado": "1",
    "autor": "Autor actualizado",
    "curso": "Curso actualizado"
}

Respuesta de Error (404 Not Found): Si el tópico con el ID proporcionado no existe.

Respuesta de Error (400 Bad Request): Si el título o mensaje actualizado ya existen (debido a las restricciones UNIQUE).

DELETE /topicos/{id}

Descripción: Elimina un tópico por su ID.

Requiere Autenticación: Sí (Bearer Token)

Parámetros de Ruta:

id: ID del tópico a eliminar (ej. 1).

Ejemplo de URL: DELETE /topicos/1

Respuesta Exitosa (204 No Content): Si el tópico fue eliminado correctamente.

Respuesta de Error (404 Not Found): Si el tópico con el ID proporcionado no existe.

📂 Estructura del Proyecto
El proyecto sigue una arquitectura en capas común en Spring Boot:

controladores: Contiene los controladores REST (@RestController) que manejan las solicitudes HTTP.

modelos: Define las entidades JPA (@Entity) que mapean a las tablas de la base de datos y los DTOs (Data Transfer Objects) para la entrada/salida de datos.

dominio: Contiene las interfaces de los repositorios (JpaRepository) para el acceso a datos.

negocio (o servicios): Contiene la lógica de negocio principal (@Service).

infra: Contiene la configuración de seguridad, filtros JWT y otras configuraciones de infraestructura.
