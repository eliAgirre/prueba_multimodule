# Prueba

## Arquitectura hexagonal

La arquitectura hexagonal tiene las siguientes capas:
- **infraestructure**: es la capa que se conecta con el exterior como la base de datos o los controladores.
- **application**: es la capa intermediaria que se conecta con la infraestructura.
- **domain**: es el núcleo, el core o la capa del negocio.

## Estructura y/o módulos

Este proyecto tiene la siguiente estructura:
- **client**: es la capa cliente por ejemplo para usar las tablas, payments o shopping externos (en este caso no es necesario).
- **authentication**: conecta todos los microservicios rest mediante un token, así es más seguro la comunicación (no es el caso, pero sería recomendable, se refuerza la seguridad).
- **database**: tiene la configuración de las base de datos h2 y se conecta con la base de datos.
- **domain**: es la capa que se encarga de solucionar la lógica de negocio.
- **entity**: son todas las entidades de la lógica del dominio.
- **web**: es el starter, el servicio web y la que se conecta con el exterior, por ejemplo, con la parte del front.

En este proyecto se ha dispersado las capas que tiene teóricamente la arquitectura hexagonal de forma modularizada, así la lógica del negocio está desacoplada con el exterior. Es decir, si se realiza algún cambio en el exterior no tiene que afectar en la lógica del negocio.

En la capa de infraestructura estarían los módulos `database`, `authentication` y `client`.

En la capa de aplicación estaría el módulo `web`.

En la capa del dominio entran los módulos `domain` y `entity`.

Se ha usado mockito para realizar los tests unitarios desde un fichero json.

Los ficheros json están en el módulo `web` en la carpeta `resources` y después en el paquete `json`.

Se ha usado diferentes tipos de json por cada caso para realizar los tests unitarios en la parte del controlador.

### Relaciones entre los módulos

- **entity**: tiene dependencia de ningún módulo, ya que son las entidades de la lógica del dominio.
- **domain**:
  - Se relaciona con el módulo `entity`, ya que necesita las entidades de la lógica del dominio para el negocio.
  - Depende del módulo `database` para que se puedan insertar y obtener los datos de una base de datos en memoria, que es la capa externa de la arquitectura hexagonal.
- **database**: tiene la relación con el módulo `entity` para manejar los datos.
- **web**: tiene dependencia con el módulo `domain` para el manejo del negocio y dar respuestas de las peticiones.

## Lista de dependencias

| Dependencia              |                                    Descripción                                    |
|--------------------------|:---------------------------------------------------------------------------------:|
| Spring Boot Starter Web  |                             Arranque de la aplicación                             |
| Spring Boot DevTools     |                               Logs de la aplicación                               |
| Spring Boot Starter Test |                          Se realizan los test unitarios                           |
| H2                       |                             Base de datos en memoria                              |
| Lombok                   |      Biblioteca de anotaciones Java que ayuda a reducir el código repetitivo      |
| Jackson Datatype Joda    |  Instancia de ObjectMapper para serializar respuestas y deserializar solicitudes  |
| Jackson Datatype Jsr310  |          Ofrece una forma sencilla e intuitiva de crear un ObjectMapper           |
| Jackson Databind         | Biblioteca de utilidades para ayudar con el desarrollo de la funcionalidad de E/S |
| Common io                |  Convierte JSON a objetos y admite una fácil conversión a Map desde datos JSON.   |





