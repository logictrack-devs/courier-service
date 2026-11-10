# courier-service
# Servicio de Gestión de Repartidores (courier-service)

Microservicio independiente de la arquitectura LogiTrack encargado de la administración, control operativo y validación de disponibilidad del cuerpo de repartidores.

## Funcionalidades Mínimas Implementadas
*   **Registro de Personal:** Endpoint optimizado para dar de alta nuevos repartidores en el sistema.
*   **Consultas Flexibles:** Capacidad de recuperar información de repartidores de forma individual (por ID) o mediante listados completos.
*   **Actualización de Perfiles:** Mecanismo centralizado para modificar los datos informativos de los empleados.
*   **Control del Estado Operativo:** Gestión dinámica de estados mediante tipos enumerados (`ACTIVO` / `INACTIVO`).
*   **Motor de Disponibilidad:** Consulta y validación en tiempo real de la disponibilidad de un repartidor para ser asignado automáticamente a un envío.
*   **Interoperabilidad REST:** Exposición de endpoints HTTP listos para ser consumidos de forma externa, específicamente por el componente `shipment-service`.

## Componentes Técnicos Utilizados
*   **Base de Datos:** Persistencia independiente en entorno relacional MySQL (`logitrack_courier_db`).
*   **Capa de Datos:** Implementación basada en Entidades JPA y Repositorios que extienden de `JpaRepository`.
*   **Arquitectura:** División limpia de responsabilidades en capas de Controlador (REST), Servicio (Lógica de negocio) y Repositorio.

## Configuración del Entorno (`application.yml`)
```yaml
server:
  port: 8082

spring:
  application:
    name: courier-service
  datasource:
    url: jdbc:mysql://localhost:3306/logitrack_courier_db?createDatabaseIfNotExist=true
    username: root
    password: tu_password_mysql
  jpa:
    hibernate:
      ddl-auto: update

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Endpoints Principales Disponibles
*   `POST /api/v1/couriers` - Registrar un nuevo repartidor.
*   `GET /api/v1/couriers` - Obtener listado completo de repartidores.
*   `GET /api/v1/couriers/{id}` - Buscar un repartidor por su identificador único.
*   `PUT /api/v1/couriers/{id}` - Actualizar los datos del repartidor.
*   `PATCH /api/v1/couriers/{id}/status` - Modificar estado (`ACTIVO`/`INACTIVO`).
*   `GET /api/v1/couriers/{id}/validate` - Validar disponibilidad para asignación.
