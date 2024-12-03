
# Ejercicio: Gestión de Seguros y Asistencias Médicas en Spring Boot con Java

En este ejercicio, desarrollarás una API REST completa en **Spring Boot** para la gestión de seguros médicos (**Seguro**) y asistencias médicas asociadas (**AsistenciaMedica**). Implementarás validaciones, relaciones entre entidades, manejo de excepciones personalizadas y retorno de códigos de estado HTTP adecuados.

En la segunda parte del ejercicio se deberá securizar la aplicación configurando filtros que restrinjan el acceso a la API con Spring Security.

---

# PARTE 1. ENTIDADES BASE

## **Enunciado del Ejercicio**

La aplicación debe cumplir los siguientes objetivos:

### **1. Entidades a Gestionar**

1. **Entidad Seguro:**
    - Representa un seguro médico.
    - Propiedades:
        - `idSeguro`: Identificador único del seguro.
        - `nif`: Número de identificación fiscal (validar formato).
        - `nombre`: Nombre del asegurado (no vacío).
        - `ape1`: Primer apellido del asegurado (no vacío).
        - `ape2`: Segundo apellido del asegurado (opcional).
        - `edad`: Edad del asegurado (mayor que 0; no menor de edad).
        - `numHijos`: Número de hijos (mayor o igual que 0; debe ser 0 si el asegurado no está casado).
        - `fechaCreacion`: Fecha de creación del seguro.
        - `sexo`: Sexo del asegurado (no puede ser nulo).
        - `casado`: Estado civil del asegurado.
        - `embarazada`: Indica si el asegurado está embarazado (no permitido para hombres).

2. **Entidad AsistenciaMedica:**
    - Representa una asistencia médica asociada a un seguro.
    - Propiedades:
        - `idAsistenciaMedica`: Identificador único de la asistencia.
        - `seguro`: Referencia al seguro asociado.
        - `breveDescripcion`: Breve descripción de la asistencia (no vacío).
        - `lugar`: Lugar de la asistencia (no vacío).
        - `explicacion`: Explicación detallada de la asistencia (no vacío).
        - `tipoAsistencia`: Tipo de asistencia (no nulo).
        - `fecha`: Fecha de la asistencia (no nula).
        - `hora`: Hora de la asistencia (no nula).
        - `importe`: Importe asociado (double; mayor que 0, con 2 decimales).

---

### **2. Requisitos Funcionales**

1. **Operaciones CRUD:**
    - **Seguro:**
        - Crear un seguro.
        - Consultar un seguro por su identificador.
        - Listar todos los seguros.
        - Actualizar un seguro existente.
        - Eliminar un seguro (debe eliminar también las asistencias asociadas).

    - **AsistenciaMedica:**
        - Crear una asistencia médica asociada a un seguro.
        - Consultar una asistencia médica por su identificador.
        - Listar todas las asistencias médicas.
        - Actualizar una asistencia médica existente.
        - Eliminar una asistencia médica.

2. **Validaciones:**
    - Implementa las siguientes validaciones para cada entidad (ver tabla de validaciones más abajo).
    - Retorna un código de estado **400 Bad Request** y un mensaje descriptivo cuando no se cumplan las reglas.

3. **Manejo de Excepciones:**
    - Crea excepciones personalizadas para validaciones específicas (**ValidationException**) y recursos no encontrados (**ResourceNotFoundException**).
    - Centraliza el manejo de excepciones con una clase anotada con `@ControllerAdvice`.
    - Responde con códigos de estado y mensajes adecuados:
        - **400 Bad Request**: Para errores de validación.
        - **404 Not Found**: Para recursos inexistentes.
        - **500 Internal Server Error**: Para errores inesperados.

4. **Códigos de Respuesta:**
    - Operaciones exitosas:
        - **201 Created**: Para creación de recursos.
        - **200 OK**: Para consultas y actualizaciones exitosas.
        - **204 No Content**: Para eliminaciones exitosas.
    - Operaciones fallidas:
        - **400 Bad Request**: Para errores de validación.
        - **404 Not Found**: Para recursos inexistentes.

---

### **3. Requisitos Técnicos**

1. **Relación entre Entidades:**
    - Relación **1 a N** entre `Seguro` y `AsistenciaMedica`. Cada seguro puede tener múltiples asistencias médicas.

2. **Base de Datos:**
    - Modelo relacional:
        - Tabla `Seguro` con las columnas correspondientes a sus propiedades.
        - Tabla `AsistenciaMedica` con una columna `idSeguro` como clave foránea.

3. **Configuración:**
    - Configura el proyecto con las siguientes dependencias:
        - **Spring Web**
        - **Spring Data JPA**
        - **MySQL Database** (o un sistema de base de datos a elección).

4. **Documentación y Pruebas:**
    - Proporciona pruebas funcionales que incluyan casos de éxito y error, utilizando herramientas como **Insomnia**.

---

### **4. Validaciones**

| Campo                   | Regla de Validación                                                       | Código HTTP  | Mensaje de Error                                     |
|-------------------------|---------------------------------------------------------------------------|--------------|-----------------------------------------------------|
| `nif`                  | Formato válido (regex).                                                   | 400          | "El campo NIF no tiene un formato válido."          |
| `nombre`, `ape1`       | No puede estar vacío.                                                     | 400          | "El campo {nombre/ape1} no puede estar vacío."      |
| `edad`                 | Mayor que 0. No menor de edad (<18).                                      | 400          | "No es posible ser menor de edad para hacer un seguro." |
| `numHijos`             | Mayor o igual que 0. Si `casado = false`, debe ser 0.                    | 400          | "Un seguro no puede registrar hijos si no está casado." |
| `embarazada`           | No puede ser `true` si `sexo = Hombre`.                                  | 400          | "El campo embarazada no puede ser true si el asegurado es hombre." |
| `breveDescripcion`     | No puede estar vacío.                                                     | 400          | "El campo breveDescripcion no puede estar vacío."   |
| `lugar`                | No puede estar vacío.                                                     | 400          | "El campo lugar no puede estar vacío."              |
| `explicacion`          | No puede estar vacío.                                                     | 400          | "El campo explicacion no puede estar vacío."        |
| `tipoAsistencia`       | No puede ser null.                                                        | 400          | "El campo tipoAsistencia no puede ser nulo."        |
| `fecha`, `hora`        | No pueden ser null.                                                       | 400          | "El campo {fecha/hora} no puede ser nulo."          |
| `importe`              | Mayor que 0.                                                             | 400          | "El campo importe debe ser mayor que 0."            |

---

### **5. Entregables**

1. **Código Fuente:**
    - Incluye todas las entidades, controladores, servicios, repositorios y excepciones.

2. **SQL de Prueba:**
    - Proporciona un script para poblar la base de datos con datos de prueba.

3. **Pruebas Funcionales:**
    - Evidencia de pruebas realizadas con Postman o Swagger:
        - Casos exitosos para cada operación.
        - Casos de validación fallida.

4. **Documentación:**
    - Esquema de la base de datos.
    - Descripción de los endpoints disponibles.

---

## **Resumen de Endpoints**

### Endpoints para Seguros
- **GET** `/seguros`
- **GET** `/seguros/{idSeguro}`
- **POST** `/seguros`
- **PUT** `/seguros/{idSeguro}`
- **DELETE** `/seguros/{idSeguro}`

### Endpoints para Asistencias Médicas
- **GET** `/asistencias`
- **GET** `/asistencias/{idAsistenciaMedica}`
- **POST** `/seguros/{idSeguro}/asistencias`
- **PUT** `/asistencias/{idAsistenciaMedica}`
- **DELETE** `/asistencias/{idAsistenciaMedica}`

## **Resumen de DTOs**

### **Entidad Seguro**

Se creará un `SeguroDTO` para la transferencia de datos. Este incluirá los campos necesarios para las operaciones CRUD y omitirá relaciones complejas o datos no necesarios en ciertos contextos.

**Ejemplo del DTO de Seguro**:
```java
public class SeguroDTO {
    private String nif;
    private String nombre;
    private String ape1;
    private String ape2;
    private int edad;
    private int numHijos;
    private String sexo;
    private boolean casado;
    private boolean embarazada;
    // Getters y setters
}
```

### **Entidad AsistenciaMedica**

Se creará un `AsistenciaMedicaDTO` para la transferencia de datos. Este incluirá los campos necesarios para las operaciones CRUD.

**Ejemplo del DTO de AsistenciaMedica**:
```java
public class AsistenciaMedicaDTO {
    private String breveDescripcion;
    private String lugar;
    private String explicacion;
    private String tipoAsistencia;
    private LocalDate fecha;
    private LocalTime hora;
    private double importe;
    // Getters y setters
}
```

## SQL de Inserción de Datos de Prueba

```sql
-- Insertar seguros
INSERT INTO seguro (id_eguro, nif, nombre, ape1, ape2, edad, num_hijos, fecha_creacion, sexo, casado, embarazada)
VALUES
    (1, '12345678A', 'Juan', 'Pérez', 'García', 35, 2, '2024-11-01 10:00:00', 'Hombre', TRUE, FALSE),
    (2, '87654321B', 'María', 'López', NULL, 28, 1, '2024-10-20 14:30:00', 'Mujer', TRUE, TRUE);

-- Insertar asistencias médicas
INSERT INTO asistencias_medicas (id_asistencia_medica, id_seguro, breve_descripcion, lugar, explicacion, tipo_asistencia, fecha, hora, importe)
VALUES
    (1, 1, 'Consulta médica general', 'Madrid', 'Consulta por síntomas leves', 'Consulta', '2024-11-02', '09:30:00', 50.00),
    (2, 1, 'Urgencia médica', 'Barcelona', 'Dolor abdominal intenso', 'Urgencia', '2024-11-03', '12:15:00', 150.00),
    (3, 2, 'Revisión ginecológica', 'Sevilla', 'Control durante embarazo', 'Consulta', '2024-11-04', '10:00:00', 70.00);
```

---

Con este ejercicio, aplicarás tus conocimientos en el diseño y desarrollo de APIs REST, validaciones, manejo de excepciones y buenas prácticas en Spring Boot.

---


# PARTE 2. SEGURIDAD DE LA APLICACIÓN

## Enunciado

En esta parte se deben crear una serie de filtros para poder securizar el acceso a algunos endpoints que no deben ser accesibles para todo el mundo. Para realizar esta 
seguridad se utilizarán tokens JWT.  
La aplicación dará la posibilidad a cualquier usuario de registrarse en la aplicación, lo cual insertará un nuevo usuario en la base de datos. *IMPORTANTE* El usuario quedará guardado en la base de datos junto con su 
password **"hasheada"**.
Cuando el usuario esté correctamente guardado en la base de datos, entonces se le dará la posibilidad de realizar un **login**, tras lo cual se deberá generar un token JWT que se le suministrará para que pueda 
realizar peticiones a los endpoints protegidos.

## **1. Estructura de la entidad extra**

1. **Usuario**:
   - `id` (Long): Identificador único del usuario.
   - `username` (String): Nombre del usuario (debe ser único).
   - `password` (String): Contraseña del usuario (debe estar hasheada).
   - `rol` (String): Rol del usuario, que puede ser `USER` o `ADMIN`.

## **2. Requisitos funcionales**

1. **Gestión de Usuarios**:
   - Los usuarios deben estar almacenados en la base de datos con los atributos mencionados.
   - Las contraseñas deben estar **hasheadas** utilizando un algoritmo seguro como `BCrypt`.
   - Cada usuario debe tener un rol:
      - `USER`: Tienen privilegios de tipo USER
      - `ADMIN`: Tienen privilegios de tipo ADMIN
   - Se deben tener en cuenta las posibles excepciones que puedan ocasionarse tanto al realizar el login como al realizar el registro de un nuevo usuario.

2. **Autenticación mediante JWT**:
   - Los usuarios deben autenticarse mediante el endpoint `POST /login`, enviando su nombre y contraseña.
   - Si las credenciales son válidas:
      - Se genera un **token** que debe enviarse al cliente para que lo almacene.
   - Los token JWT generados deberán tener una validez máxima de 1 hora, tras lo cual deberán de dejar de ser válidos.

## **3. Resumen de los endpoints**

### Endpoints para Usuarios
- **POST** `/usuarios/login`
  - *RUTA PÚBLICA* Cualquier usuario puede acceder a este endpoint
- **POST** `/usuarios/register`
   - *RUTA PÚBLICA* Cualquier usuario puede acceder a este endpoint

### Endpoints para Seguros
- **GET** `/seguros`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **GET** `/seguros/{idSeguro}`
  - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso
  - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` que sean propietarios del seguro pueden acceder a este recurso
  - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente
- **POST** `/seguros`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **PUT** `/seguros/{idSeguro}`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **DELETE** `/seguros/{idSeguro}`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso

### Endpoints para Asistencias Médicas
- **GET** `/asistencias`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **GET** `/asistencias/{idAsistenciaMedica}`
  - *RUTA PROTEGIDA* **AUTHENTICATED** Sólo usuarios correctamente autenticados pueden acceder a este recurso
  - *RUTA PROTEGIDA* **ROL USER** Sólo usuarios con `ROL USER` que sean propietarios del seguro pueden acceder a este recurso
  - *RUTA PROTEGIDA* **ROL ADMIN** Usuarios con `ROL ADMIN` pueden acceder al recurso libremente
- **POST** `/seguros/{idSeguro}/asistencias`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **PUT** `/asistencias/{idAsistenciaMedica}`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso
- **DELETE** `/asistencias/{idAsistenciaMedica}`
  - *RUTA PROTEGIDA* **SÓLO ADMIN** Sólo usuarios con `ROL ADMIN` pueden acceder a este recurso

### **4. Entregables**

1. **Código Fuente:**
   - Incluye todas las entidades, controladores, servicios, repositorios y excepciones.

2. **SQL de Prueba:**
   - Proporciona un script para poblar la base de datos con datos de prueba.

3. **Pruebas Funcionales:**
   - Evidencia de pruebas realizadas con Postman, Swagger o Insomnia:
      - Casos exitosos para cada operación.
      - Casos de validación fallida.

4. **Documentación:**
   - Esquema de la base de datos.
   - Descripción de los endpoints disponibles.

### RESULTADOS DE APRENDIZAJE IMPLICADOS

#### *6.* Desarrolla aplicaciones de acceso a almacenes de datos, aplicando medidas para mantener la seguridad y la integridad de la información.

#### *7.* Desarrolla servicios Web analizando su funcionamiento e implantando la estructura de sus componentes.

#### *8.* Genera páginas Web dinámicas analizando y utilizando tecnologías del servidor Web que añadan código al lenguaje de marcas.

#### *9.* Desarrolla aplicaciones Web híbridas seleccionando y utilizando librerías de código y repositorios heterogéneos de información.












