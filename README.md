# Microservicio Telcel.
Proyecto desarrollado como prueba técnica.

## Requisitos.
- Contar con Java en su versión 23.
- Contar con MongoDB en su versión 7.0 o superior.
- Contar con Docker.

## Visualización de la documentación.
Usando la url: http://{host}:5000/swagger-ui.html puedes ingresar a la UI de **swagger**.

## Configuración de conexión a DB.
Para una correcta conexión a MongoDB se debe de color la URI y el nombre de la DB en los siguientes campos del archivo **application.properties**
```
spring.data.mongodb.uri
spring.data.mongodb.database
```

## Creación del archivo .jar
```
mvn clean install -U
```
De ser necesario crear el archivo .jar omitiendo las pruebas, se puede ejecutar el siguiente comando.
```
mvn clean install -U -Dmaven.test.skip=true
```
### Se puede verificar la cobertura del código usando SonarQube.
```
mvn clean verify sonar:sonar -Dsonar.projectKey=PROJECTKEY -Dsonar.host.url=HOST -Dsonar.login=KEY
```
## Creación de imágenes en Docker
```
docker build -t name-image:tag .
```

## Autor

- [Cesar Bautista](https://github.com/cesarBau)


## Anexo

Para la ejecución de pruebas se requiere tener instalado el servicio de Docker, ya que las pruebas de los repositorios usan Mongo test containers.
