# Prueba

## Arquitectura hexagonal

La arquitectura hexagonal tiene las siguientes capas:
- **infraestructure**: es la capa que se conecta con el exterior como la base de datos o los controladores.
- **application**: es la capa intermediaria que se conecta con la infraestructura.
- **domain**: es el núcleo, el core o la capa del negocio.

## Estructura

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

