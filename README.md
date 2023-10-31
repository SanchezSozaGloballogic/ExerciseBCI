# ExerciseBCI

## Detalles Tecnicos 
* Creación de aplicacion con Java 8
* springboot 2.7.14 
* Gradle
* Spock 2.0
* Groovy 2.5.6
* Database H2

## Arranque de la aplicación

* Primero se debe ejecutar comando
  ```bash
   ./gradlew init 
  ```
* Se debe levantar el proyecto ejecutando el comando 
   ```bash 
     ./gradlew bootRun 
   ```
* Ejecutar endpoint en postman http://localhost:9091/sign-up el cual es un metodo POST
  * Ejemplo de Body
  ```JSON
    {
       "email": "aloha@aloha.cl",
       "password": "a2sFGfdfdf4",
       "phones": 
       [
        {
          "number": 123,
          "cityCode": 2,
          "countryCode": "+56"
        }
       ]
    }
```

