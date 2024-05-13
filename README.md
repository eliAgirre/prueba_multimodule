# Prueba multi módulo

## Arquitectura hexagonal

La arquitectura hexagonal tiene las siguientes capas:
- **infraestructure**: es la capa que se conecta con el exterior como la base de datos o los controladores.
- **application**: es la capa intermediaria que se conecta con la infraestructura.
- **domain**: es el núcleo, el core o la capa del negocio.

## Estructura y/o módulos

Este proyecto tiene la siguiente estructura:
- **client**: es la capa cliente por ejemplo para usar las tablas, payments o shopping externo (en este caso no es necesario).
- **authentication**: conecta todos los microservicios rest mediante un token, así es más seguro la comunicación (no es el caso, pero sería recomendable, se refuerza la seguridad).
- **database**: se usan los repositorios jpa para realizar consultas a la base de datos en memoria h2.
- **domain**: es la capa que se encarga de solucionar la lógica de negocio.
- **entity**: son todas las entidades de la lógica del dominio.
- **web**: es el starter, el servicio web y tiene la configuración de la base de datos h2 (application.properties).

En este proyecto se ha dispersado las capas que tiene teóricamente la arquitectura hexagonal de forma modularizada, así la lógica del negocio está desacoplada con el exterior. Es decir, si se realiza algún cambio en el exterior no tiene que afectar en la lógica del negocio.

En la **capa de infraestructura** estarían los módulos `database`, `authentication` y `client`.

En la **capa de aplicación** estaría el módulo `web`.

En la **capa del dominio** entran los módulos `domain` y `entity`.

Se ha usado mockito para realizar los tests unitarios desde un fichero json.

Los ficheros json están en el módulo `web` en la carpeta `resources` y después en el paquete `json`.

  ### Relaciones entre los módulos

  - **entity**: no tiene dependencia de ningún módulo, ya que son las entidades de la lógica del dominio.
  - **domain**:
    - Se relaciona con el módulo `entity`, ya que necesita las entidades de la lógica del dominio para el negocio.
    - Depende del módulo `database` para que se puedan insertar y obtener los datos de una base de datos en memoria, que es la ***capa de infraestructura*** de la arquitectura hexagonal.
  - **database**: tiene la relación con el módulo `entity` para manejar los datos.
  - **web**: tiene dependencia con el módulo `domain` para el manejo del negocio y dar respuestas a las peticiones.

## Módulos
  ### Entity
  
  Son las entidades de la lógica del dominio como `Prices`, `Brands` y `Products`. Además, se ha añadido la entidad `Error` para cuando se generan fallos como las excepciones.
  
  La entidad `Prices` contiene las anotaciones `@Entity`, `@Id`, `@ManyToOne` y `@JoinColumn` para la persistencia de datos en JPA. La anotación `@Entity` sirve para que la aplicación sepa que es una entidad y que debe crear la tabla según las propiedades que tenga la clase. La anotación `@Id` sirve para definir el identificador de la clase. La anotación `@ManyToOne` es una relación de mucho a uno (N:1) para que cree en la tabla esa relación con una `id` de otra entidad mediante la anotación `@JoinColumn`:
  
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
  
  Se usa los repositorios JPA para realizar consultas a la base de datos en memoria h2. Se han creado los repositorios necesarios por cada entidad. En el caso de la interfaz `PriceRepository` se extiende el repositorio JPA para utilizar la entidad `Prices`:
  
  ```java
  @Repository
  @Transactional
  public interface PricesRepository extends JpaRepository<Prices, Integer> {
  
     List<Prices> findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(LocalDateTime startDate, LocalDateTime endDate, Brands brand, Products product);
  }
  ```
  
  La anotación transaccional proporciona a la aplicación la capacidad de controlar declarativamente las transacciones en beans o entidades, así como las clases definidas.
  
  Como se figura en el código se está realizando una consulta a la base de datos con una fecha de inicio, fecha de finalización, marca id y producto id. Esta consulta devuelve una lista de precios según los parámetros que vengan, siempre y cuando coincida que sea mayor o igual que la fecha de inicio, menor o igual que la fecha de finalización, y que coincidan tanto el identificador de marca como la del producto.
  
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
  La clase `PricesService` tiene inyectado `PricesRepository`, `BrandsService`, `ProductsService`, `Brands` y `Products` en el constructor de la clase. Como en la clase anterior, se está usando la anotación `@PostConstruct` para que se guarden las marcas y los productos en sus tablas correspondientes, y la lista de los precios en la tabla de los precios:
  
  ```java
  
    @PostConstruct
    public void initPrices(){

        brand = brandsServices.initBrand();
        product = productsService.initProduct();

        pricesRepository.saveAll(Stream.of(new Prices(1, brand,
                                Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_1, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_1, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                1, product, 0, 35.50, Constants.CURR),

                                new Prices(2, brand,
                                        Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_2, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_2, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        2, product, 1, 25.45, Constants.CURR),

                                new Prices(3, brand,
                                        Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_3, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_3, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        3, product, 1, 30.50, Constants.CURR),

                                new Prices(4, brand,
                                        Utility.getLocalDateTimeFromString(Constants.START_DATE_STRING_4, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING_4, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS),
                                        4, product, 1, 38.95, Constants.CURR)
                                )
                                .collect(Collectors.toList()));
    }
  
  ```
  
  Mediante esta clase se podrá consultar los precios según la fecha de inicio, la fecha de finalización, la marca y el producto. Aquí hay un ejemplo sobre cómo se consulta la lista de los precios en la base de datos mediante el repositorio de JPA:
  
  ```java
   public List<Prices> getPricesBetweenDatesAndBrandAndProduct(String startDate, String endDate, int brandId, int productId) throws ServiceException{

        log.info(Constants.LOG_SERVICE, startDate, endDate, brandId, productId);

        if (Objects.isNull(startDate) || startDate.isEmpty()) {
            throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.START_DATE_IS_NOT_CORRRECT))
                    .withHttpStatus(HttpStatus.BAD_REQUEST).build();
        }
        if (Objects.isNull(endDate) || endDate.isEmpty()) {
            throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.END_DATE_IS_NOT_CORRRECT))
                    .withHttpStatus(HttpStatus.BAD_REQUEST).build();
        }

        if (Objects.isNull(brandId)) {
            throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.BRAND_ID_IS_NOT_CORRRECT))
                    .withHttpStatus(HttpStatus.BAD_REQUEST).build();
        }

        if (Objects.isNull(productId)) {
            throw new ServiceException.Builder(String.valueOf(ServiceErrorCatalog.PRODUCT_ID_IS_NOT_CORRRECT))
                    .withHttpStatus(HttpStatus.BAD_REQUEST).build();
        }

        LocalDateTime localStartDate = Utility.getLocalDateTimeFromString(startDate, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime localEndDate = Utility.getLocalDateTimeFromString(endDate, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        return pricesRepository.findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(localStartDate, localEndDate, brand, product);
    }
  ```
  
  ### Web
  
  Este módulo contiene el arranque de la aplicación, los controladores y la configuración de la base de datos desde el fichero `application.properties`. En el fichero `pom.xml` es donde figura cuál es la clase principal:
  ```xml
    <properties>
        <start-class>com.inditex.web.MainApplication</start-class>
    </properties>
  ```
  
  La configuración de la base de datos de H2 está dentro de la carpeta `resources` en el fichero de `application.properties`:
  
  ```properties
  spring.h2.console.enabled=true
  spring.datasource.url=jdbc:h2:mem:test
  spring.datasource.generate-unique-name=false
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=password
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

  # Resuelve el nombre de las columnas y de las tablas, no afecta a las relaciones
  spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
  DB_CLOSE_ON_EXIT=FALSE

  spring.jpa.defer-datasource-initialization = true
  # muestra los logs de sql
  spring.jpa.show-sql = true


  logging.level.web=DEBUG
  server.port=8082
  ```
  
  Dentro del módulo también está el controlador llamado `PricesController` con la anotación `@RestController`, `@RequestMapping`, `@Slf4j` y `@Validated`. Este controlador simplemente tiene inyectado el servicio de los precios en el constructor, y tiene un único endpoint para que se consulten los datos de los precios según los parámetros obtenidos de la petición:
  
  ```java
  @Slf4j
  @RestController
  @RequestMapping(Constants.BASE_MAPPING)
  @Validated
  public class PricesController {
  
    private PricesService pricesService;

    public PricesController(PricesService pricesService){
        this.pricesService = pricesService;
    }

    @GetMapping(Constants.PATH_PRICES_FILTER_BY_DATES_BRAND_PRODUCT)
    public ResponseEntity<List<Prices>> getPricesByDatesAndBrandAndProduct(
            @NotBlank @RequestParam(value = "startDate") String startDate,
            @NotBlank @RequestParam(value = "endDate") String endDate,
            @RequestParam(value = "brandId") int brandId,
            @RequestParam(value = "productId") int productId) throws ServiceException {

        log.info(Constants.LOG_CONTROLLER, startDate, endDate, brandId, productId);

        List<Prices> pricesList = null;

        try {
            pricesList = pricesService.getPricesBetweenDatesAndBrandAndProduct(startDate, endDate, brandId, productId);
        }
        catch (ServiceException e) {
            throw new ServiceException(e.getCode(), e.getHttpStatus(), e.getMessage(), e.getCause(), e.getParams());
        }

        return ResponseEntity.ok(pricesList);
    }
  }
  ```
  
  Según se figura en el código del controlador el endpoint puede lanzar una excepción de servicio o puede devolver una lista de precios, tanto vacía como rellena.
  
## Tests con Mockito

Se han creado los tests para las clases `PricesService` y `PricesController`. Se ha usado para realizar los tests `junit.jupiter` y `mockito`.

### Test en el servicio

En el caso de `PricesServiceTest` se ha creado un método llamado `setUp` para mockear las clases que se instancian en el servicio como `PricesRepository`, `BrandsService`, `ProductsService`, `Brands`, `Products` y `ServiceException`:

```java
    @BeforeEach
    public void setUp() {

        mockedPricesRepository = mock(PricesRepository.class);
        mockedBrandsServices = mock(BrandsServices.class);
        mockedProductsService = mock(ProductsService.class);
        mockedBrand = mock(Brands.class);
        mockedProduct = mock(Products.class);
        mockedPricesService = new PricesService(mockedPricesRepository, mockedBrandsServices, mockedProductsService, mockedBrand, mockedProduct);
        mockedServiceException = getServiceExceptionByErrorCodeAndStatus(ServiceErrorCatalog.START_DATE_IS_NOT_CORRRECT.name(),
                HttpStatus.INTERNAL_SERVER_ERROR, ServiceErrorCatalog.START_DATE_IS_NOT_CORRRECT.getMessage());

    }
```

Ejemplo de un caso unitario cuando la fecha de inicio, la fecha de finalización, la marca id y el producto id tengan un valor concreto:

```java

    @Test
    void test1_getPricesAt10ByDate14BrandId1AndProductId35435_should_call_pricesRepository() {
        System.out.println(Constants.LOG_SERVICE_TEST1);

        // Given
        LocalDateTime startDate = Utility.getLocalDateTimeFromString(Constants.TEST_1_START_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);
        LocalDateTime endDate = Utility.getLocalDateTimeFromString(Constants.END_DATE_STRING, Constants.FORMAT_DATE_TIME_YYYY_MM_DD_HH_MM_SS);

        // When
        mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.TEST_1_START_DATE_STRING, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        verify(mockedPricesRepository).findPricesByStartDateGreaterThanEqualAndEndDateLessThanEqualAndBrandAndProduct(startDate, endDate, mockedBrand, mockedProduct);
        System.out.println();
    }
```

Otro caso de uso es cuando la fecha de inicio sea vacío:

```java
    @Test
    void getPricesByStartDateEmptyAndEndDateAndBrandId1AndProductId35435_should_throw_ServiceException() {
        System.out.println(Constants.LOG_SERVICE_TEST_SERVICE_EXCEPTION);

        // Given
        mockedPricesService = mock(PricesService.class);
        doThrow(mockedServiceException).when(mockedPricesService).getPricesBetweenDatesAndBrandAndProduct(Constants.EMPTY, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID);

        // Then
        ServiceException serviceException = assertThrows(ServiceException.class,
                () -> mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(Constants.EMPTY, Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID));

        // Then
        assertNotNull(serviceException);
        assertNotNull(serviceException.getMessage());
        assertEquals(ServiceErrorCatalog.START_DATE_IS_NOT_CORRRECT.getMessage(), serviceException.getMessage());
        System.out.println();
    }
```

### Test en el controlador

En el caso del controlador se han utilizado los ficheros json cargados por cada caso que se inyecta con la anotación `@Autowired` y antes de cada test se mockean tanto el servicio como la excepción:

```java
  public class PricesControllerTest {

    @Autowired
    private JsonToObjectsCreator json;

    @Mock
    private PricesService mockedPricesService;

    private PricesController pricesController;

    @Mock
    private ServiceException mockedServiceException;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        pricesController = new PricesController(mockedPricesService);
        json = new JsonToObjectsCreator();
    }
  }
```

En uno de los casos unitarios, se obtiene la petición y la respuesta de los ficheros json. Se comprueba si la respuesta no viene nula, si el cuerpo de la respuesta no viene nula, si devuelve el tamaño correspondiente de la lista y si los datos por cada lista las devuelve como es debido:

```java
    public class PricesControllerTest {
        @Test
        void Test1_Given_StartDateAt10Date14AndEndDateAndBrandAndProduct_When_getPrices_Then_returns_PricesList() throws Exception {
            System.out.println(Constants.LOG_CONTROLLER_TEST1);
            // Given
            PriceRqTest rq1 = json.test1PriceRqAt10Date14();
            List<Prices> rsList1 = json.test1PriceRsAt10Date14();
    
            // When
            when(mockedPricesService.getPricesBetweenDatesAndBrandAndProduct(rq1.getStartDate(), rq1.getEndDate(),
                    rq1.getBrandId(), rq1.getProductId())).thenReturn(rsList1);
    
            ResponseEntity<List<Prices>> responseEntity = pricesController.getPricesByDatesAndBrandAndProduct(rq1.getStartDate(),
                    rq1.getEndDate(), rq1.getBrandId(), rq1.getProductId());
            responseEntity.getBody().stream()
                    .map(price -> price.toString())
                    .forEach(System.out::println);
            System.out.println();
    
            // Then
            assertNotNull(responseEntity);
            assertNotNull(responseEntity.getBody());
            assertEquals(rsList1.size(), responseEntity.getBody().size());
            assertEquals(rsList1.get(0), responseEntity.getBody().get(0));
            assertEquals(rsList1.get(1), responseEntity.getBody().get(1));
            assertEquals(rsList1.get(2), responseEntity.getBody().get(2));
            assertEquals(rsList1.get(3), responseEntity.getBody().get(3));
            System.out.println();
        }
    }
```

Otro caso unitario es cuando la fecha de iniciación es vacía y lanza la excepción `ServiceException`:

```java
    @Test
    void Given_StartDateEmptyAndEndDateAndBrandId1AndProductId35435_When_getPrice_Then_throws_ServiceException() throws ServiceException {
        System.out.println(Constants.LOG_CONTROLLER_TEST_SERVICE_EXCEPTION);
        // When
        doThrow(mockedServiceException).when(mockedPricesService).getPricesBetweenDatesAndBrandAndProduct(Constants.EMPTY, Constants.END_DATE_STRING,
                Constants.BRAND_ID, Constants.PRODUCT_ID);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> pricesController.getPricesByDatesAndBrandAndProduct(Constants.EMPTY,
                Constants.END_DATE_STRING, Constants.BRAND_ID, Constants.PRODUCT_ID));

        // Then
        assertNotNull(serviceException);
        System.out.println();
    }
```

## Lista de dependencias

| Dependencia              |                                    Descripción                                             |
|--------------------------|:------------------------------------------------------------------------------------------:|
| Spring Boot Starter Web  |                             Arranque de la aplicación                                      |
| Spring Boot Starter JPA  | Persiste datos en almacenes SQL con Java Persistence API mediante Spring Data e Hibernate. |
| Spring Boot DevTools     |                   Herramienta de desarrollo como ver los logs.                             |
| Spring Boot Starter Test |                          Se realizan los test unitarios.                                   |
| H2                       |                             Base de datos en memoria.                                      |
| Lombok                   |      Biblioteca de anotaciones Java que ayuda a reducir el código repetitivo.              |
| Jackson Datatype Joda    |  Instancia de ObjectMapper para serializar respuestas y deserializar solicitudes.          |
| Jackson Datatype Jsr310  |          Ofrece una forma sencilla e intuitiva de crear un ObjectMapper.                   |
| Jackson Databind         | Biblioteca de utilidades para ayudar con el desarrollo de la funcionalidad de E/S.         |
| Common io                |  Convierte JSON a objetos y admite una fácil conversión a Map desde datos JSON.            |





