## Cakes Manager 1.0
This is a proposed replacement for the https://github.com/Waracle/cake-manager project.

It adds additional functionality like oAuth2 authentication, docker packaging and SwaggerUI
for the documentation and executing requests.

It requires Java 21 or later.

## Using maven wrapper on Windows
Replace the `./mvnw` with `mvnw.bat` (I'm not 100% sure about this as I don't have a Windows computer to test it)

## To run tests 
Execute command `./mvnw test`

## To run the web server
Execute command `./mvnw mn:run`

Once the server is started navigate to http://localhost:8080 in your browser and log in with your GitHub account to access Swagger UI

You can use SwaggerUI to execute REST requests by pressing the "Try it out" button.

Have in mind that API endpoints are secured and will return 401 HTTP Status Code unless you've logged in with GitHub.
This will also be case if you try to use Postman, curl or other programs without including the authorization header and its contents.

To stop the server press `Ctrl+C`.

## To package and run as Docker Image

Execute command `./mvnw package -Dpackaging=docker`

Once the image is created you can run it by executing `docker run -p 8080:8080 cakesmanager:latest`

Navigate to http://localhost:8080 in your browser.





