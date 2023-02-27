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
- **database**: se usa los repositorios jpa para realizar consultas a la base de datos en memoria h2.
- **domain**: es la capa que se encarga de solucionar la lógica de negocio.
- **entity**: son todas las entidades de la lógica del dominio.
- **web**: es el starter, el servicio web y tiene la configuración de la base de datos h2 (application.properties).

En este proyecto se ha dispersado las capas que tiene teóricamente la arquitectura hexagonal de forma modularizada, así la lógica del negocio está desacoplada con el exterior. Es decir, si se realiza algún cambio en el exterior no tiene que afectar en la lógica del negocio.

En la **capa de infraestructura** estarían los módulos `database`, `authentication` y `client`.

En la **capa de aplicación** estaría el módulo `web`.

En la **capa del dominio** entran los módulos `domain` y `entity`.

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

## Módulos
  ### Entity
  
  Son las entidades de la lógica del dominio como `Prices`, `Brands` y `Products`. Además, se ha añadido la entidad `Error` para cuando se generan fallos como las excepciones.
  
  La entidad `Prices` contiene las anotaciones `@Entity`, `@Id`, `@ManyToOne` y `@JoinColumn` para la persistencia de datos en JPA. La anotación `@Entity` sirve para que la aplicación sepa que es una entidad y que debe crear la tabla según las propiedades que tenga la clase. La anotación `@Id` sirve para definir el id de la clase. La anotación `@ManyToOne` es una relación de mucho a uno (N:1) para que cree en la tabla esa relación con una id de otra entidad mediante la anotación `@JoinColumn`:
  
  ```java
  @Entity
  public class Prices {
  
     @Id
    private int priceId;
    
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brands brand;
  
  }
  ```
  Aparte de las anotaciones de JPA la clase `Prices` tiene las anotaciones de Lombok reducir el código repetitivo como `@Getter`, `@ToString`, `@RequiredArgsConstructor` y `@AllArgsConstructor`.
  
  ### Database
  
  Se usa los repositorios JPA para realizar consultas a la base de datos en memoria h2. Se han creado los repositorios necesarios por cada entidad. En el caso del interfaz `PriceRepository` se extiende el repositorio JPA para utilizar la entidad `Prices`:
  
  ```java
  @Repository
  @Transactional
  public interface PricesRepository extends JpaRepository<Prices, Integer> {
  
     List<Prices> findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(LocalDateTime startDate, LocalDateTime endDate, Brands brand, Products product);
  }
  ```
  
  La anotación transaccional proporciona la aplicación la capacidad de controlar declarativamente de las transacciones en beans o entidades, así como clases definidas.
  
  Como se figura en el código se está realizando una consulta a la base de datos con una fecha de inicio, fecha de finalización, marca id y producto id. Esta consulta devuelve una lista de precios según los parámetros que vengan, siempre y cuando coincida que sea mayor o igual que la fecha de inicio, menor o igual que la fecha de finalización, y que coincidan tanto el id de marca como la del producto.
  
  ### Domain
  
  Se encarga de solucionar la lógica de negocio con los servicios necesarios por cada entidad. El módulo `domain` contiene los paquetes `services`, `exception` y `utils`.
  
  En el paquete `services` se han creado los servicios necesarios por cada entidad. La clase `BrandsServices` tiene una anotación `@Service` y `@Slf4j`. La primera es para que Spring Boot sepa que es un servicio y la segunda sirve para ver los registros de los logs.
  
  Esta clase tiene una inyección de dependencias en el constructor del repositorio y la clase `Brands`:
  
  ```java
  
  @Slf4j
  @Service
  public class BrandsServices {
  
    private Brands brand;

    private BrandsRepository brandsRepository;
  
      public BrandsServices(BrandsRepository brandsRepository, Brands brand){
        this.brandsRepository = brandsRepository;
        this.brand = brand;
    }
  }
  ```
  
  La clase `BrandsService` contiene la anotación `@PostConstuct` para inicializar la tarea de guardar la marca mediante el repositorio inyectado en el constructor:
  
  ```java
    @PostConstruct
    public Brands initBrand(){
        brand = generateBrand();
        return brandsRepository.save(brand);
    }
  ```
  

  
  ### Web


## Lista de dependencias

| Dependencia              |                                    Descripción                                            |
|--------------------------|:-----------------------------------------------------------------------------------------:|
| Spring Boot Starter Web  |                             Arranque de la aplicación                                     |
| Spring Boot Starter JPA  | Persiste datos en almacenes SQL con Java Persistence API mediante Spring Data e Hibernate |
| Spring Boot DevTools     |                   Herramienta de desarrollo como ver los logs.                            |
| Spring Boot Starter Test |                          Se realizan los test unitarios                                   |
| H2                       |                             Base de datos en memoria                                      |
| Lombok                   |      Biblioteca de anotaciones Java que ayuda a reducir el código repetitivo              |
| Jackson Datatype Joda    |  Instancia de ObjectMapper para serializar respuestas y deserializar solicitudes          |
| Jackson Datatype Jsr310  |          Ofrece una forma sencilla e intuitiva de crear un ObjectMapper                   |
| Jackson Databind         | Biblioteca de utilidades para ayudar con el desarrollo de la funcionalidad de E/S         |
| Common io                |  Convierte JSON a objetos y admite una fácil conversión a Map desde datos JSON.           |





