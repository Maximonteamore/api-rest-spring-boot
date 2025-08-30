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

Este proyecto incluye un archivo de ejemplo `application-example.properties` con la configuración necesaria. Para ejecutar el proyecto:
