# Spring Boot API REST con JWT y Roles

Este proyecto es una API REST creada con Spring Boot que implementa autenticación mediante JWT, roles de usuario y CRUD sobre entidades como **Fabricante** y **Producto**,
contiene un import  con insert a sus respectivas tablas con dos usuarios con distinto roles para hacer pruebas.

---

##  Funcionalidades

- CRUD completo para:
    - `/api/maker`: Fabricantes
    - `/api/product`: Productos
- Registro y login de usuarios
- Seguridad con JWT (Spring Security)
- Control de acceso por roles (`ROLE_dev`)
- Insert automático de datos vía `import.sql`

---

##  Seguridad

- Endpoints públicos:
    - `POST /auth/sign-up`: Registro de usuarios
    - `POST /auth/log-in`: Login y generación de JWT
    - `GET /api/product/**`: Acceso libre
- Endpoints protegidos:
    - `GET /api/maker/findAll`: Requiere rol `dev`
    - Todo lo demás: Requiere autenticación

---



1. Registrate:

### Endpoints públicos:
- `POST /auth/sign-up` → Registrar nuevo usuario
- `POST /auth/log-in` → Login y obtención de token JWT
- `GET /api/product/**` → Consultar productos sin autenticación

### Endpoints protegidos:
- `GET /api/maker/findAll` → Requiere rol `dev`
- Cualquier otro endpoint → Requiere autenticación JWT válida

### Ejemplo de uso del token:

Después del login, usá el token recibido en los headers:



## 🔧 Configuración del proyecto

### 1. Crear archivo de configuración real y pone los datos necesaarios para conexion a la BD.
    2. Copiá ese archivo y renombralo como `application.properties`
    3. Conexión a la base de datos
        spring.datasource.url=jdbc:mysql://localhost:3306/api_rest
        spring.datasource.username=TU_USUARIO
        spring.datasource.password=TU_CONTRASEÑA
        
        # Configuración de Hibernate
        spring.jpa.hibernate.ddl-auto=create
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        
        # Clave secreta para firmar tokens JWT
        security.jwt.key.private=TU_CLAVE_SECRETA
        
        # Identificador del emisor del token
        security.jwt.user.generator=TU_EMISOR

### 2. Ejecutar
        mvn spring-boot:run





pplication-example.properties` con la configuración necesaria. Para ejecutar el proyecto:
