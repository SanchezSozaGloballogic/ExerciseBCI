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
  * cURL
  ```bash
    curl --location 'http://localhost:9091/sign-up' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "test de usuario",
    "email": "aloha@aloha.com",
    "password": "12wsFdefg",
    "phones":
    [
    {
    "number": 123,
    "cityCode": 2,
    "countryCode": "+56"
    }
    ]
    }'
     ```

  * Ejemplo de body
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

* Ejecutar endpoint en postman http://localhost:9091/login el cual es un metodo Get donde el parametro es el token generado en el endpoint anterior
  * Ejemplo cURL
    ```bash
    curl --location 'http://localhost:9091/login?token=eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSG9sYSBNdW5kbyIsImVtYWlsIjoiYWxvaGFAYWxvaGEuY29tIiwic3ViIjoiSG9sYSBNdW5kbyIsImp0aSI6ImQ4NTI1NjhiLTMzMTYtNDA5Yi04N2RmLWVjZTdmMWRiYTlhNyIsImlhdCI6MTY5NzYzNDk3MiwiZXhwIjoxNjk3NjM1MjcyfQ.dzW5UkruTKJedE7Xewsz2IsQ0XX0EgdOcwqDO27pcrs'
    ```

  * Ejemplo de consumo
  ```html
    http://localhost:9091/login?token=eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiSG9sYSBNdW5kbyIsImVtYWlsIjoiYWxvaGFAYWxvaGEuY29tIiwic3ViIjoiSG9sYSBNdW5kbyIsImp0aSI6ImQ4NTI1NjhiLTMzMTYtNDA5Yi04N2RmLWVjZTdmMWRiYTlhNyIsImlhdCI6MTY5NzYzNDk3MiwiZXhwIjoxNjk3NjM1MjcyfQ.dzW5UkruTKJedE7Xewsz2IsQ0XX0EgdOcwqDO27pcrs