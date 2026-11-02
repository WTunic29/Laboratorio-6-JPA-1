# Laboratorio 6: Java Persistence API (JPA)

## Descripción

Este proyecto implementa un sistema de gestión de competidores y productos utilizando Java Persistence API (JPA). Permite persistir información en una base de datos relacional y exponer operaciones mediante servicios REST.

El laboratorio también incorpora un servicio de autenticación que verifica las credenciales de los competidores.

## Tecnologías utilizadas

- **Java:** lenguaje de programación.
- **Jakarta EE:** plataforma para el desarrollo de aplicaciones empresariales.
- **JPA:** persistencia y mapeo objeto-relacional.
- **JAX-RS:** desarrollo de servicios REST.
- **Apache NetBeans IDE:** entorno de desarrollo.
- **GlassFish:** servidor de aplicaciones.
- **Apache Derby:** sistema de gestión de bases de datos relacionales.
- **Maven:** gestión de dependencias y construcción del proyecto.

## Estructura del proyecto

```text
Laboratorio6JPA/
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/example/models/
│       │   │   ├── Competitor.java
│       │   │   └── Producto.java
│       │   └── co/edu/unipiloto/laboratorio6jpa/
│       │       └── services/
│       │           ├── CompetitorService.java
│       │           └── LoginService.java
│       └── resources/
│           └── META-INF/
│               └── persistence.xml
├── nbproject/
├── pom.xml
└── README.md
```

## Modelo de datos

### Competitor

Representa a un competidor e incluye información personal, credenciales y datos de contacto.

### Producto

Representa un producto asociado a un competidor.

La relación entre ambas entidades es de uno a muchos (`@OneToMany` y `@ManyToOne`).

La clave foránea `COMPETITOR_ID` se encuentra en la tabla `PRODUCTO` y referencia al registro correspondiente en `COMPETITOR`.

## Servicios REST

La aplicación expone los siguientes endpoints:

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| GET | `/resources/competitors` | Consulta los competidores registrados. |
| POST | `/resources/competitors` | Registra un nuevo competidor. |
| POST | `/resources/login` | Verifica las credenciales de un competidor. |

**URL base local:**

```text
http://localhost:8080/Laboratorio6JPA/resources
```

### Ejemplo de inicio de sesión

**Solicitud:**

```http
POST /Laboratorio6JPA/resources/login
Content-Type: application/json
```

```json
{
  "email": "laura.prueba@example.com",
  "password": "Prueba123"
}
```

Si las credenciales son correctas, el servicio devuelve una respuesta `200 OK` con información del competidor.

Si las credenciales son incorrectas, devuelve `401 Unauthorized`.

## Configuración y ejecución

1. Instalar un JDK compatible con la versión del servidor de aplicaciones.
2. Instalar Apache NetBeans y configurar GlassFish.
3. Configurar la conexión JDBC a la base de datos Apache Derby.
4. Verificar el origen de datos `jdbc/Laboratorio6JPA`.
5. Abrir el proyecto desde Apache NetBeans.
6. Construir el proyecto mediante Maven.
7. Desplegar la aplicación en GlassFish.
8. Probar los endpoints REST utilizando Postman o una herramienta equivalente.

## Pruebas realizadas

Se verificaron las siguientes operaciones:

- Registro de un competidor mediante una solicitud POST.
- Consulta de los competidores registrados.
- Inicio de sesión con credenciales válidas.
- Rechazo de credenciales incorrectas mediante una respuesta `401 Unauthorized`.
- Persistencia de las entidades en la base de datos.
- Configuración de la relación entre competidores y productos.

## Objetivo académico

Aplicar conceptos de persistencia de datos mediante JPA, mapeo de entidades, relaciones entre tablas y desarrollo de servicios REST con Jakarta EE.

## Autoría

Proyecto académico desarrollado como parte del Laboratorio 6 de JPA.