KrampHubWebApp:
The purpose of this service is to search for albums and books for a user provided search term.
Albums are queried using iTunes whereas books are queried using Google Books api.

Technologies used:
1. Maven 3.5.0
	Maven is a powerful project management tool that is used for project builds as well as dependency management.
2. Eclipse Neon
	IDE for project development.
3. JDK 1.7
4. Spring 4.1.1
	Spring provides clear division between controllers, JavaBeans and views. Its is flexible with use of interfaces and configurations.
5. Tomcat 7
	It is an open source web server and servlet container. It provides a java http web server environment for java code to run.
6. Logback 1.0.13
	Logging system that is easy to use. It provides flexibility to switch between itself and log4J or slf4J.

Project layout:
.
|____KrampHubWebApp
| |____src
| | |____main
| | | |____java
| | | |  |____com
| | | |     |____kramphub
| | | |        |____component
| | | |        |____controller
| | | |        |____entity
| | | |        |____response
| | | |____resources
| | | |____webapp
| | | | |____index.jsp
| | | | |____WEB-INF
| | | | | |____mvc-dispatcher-servlet.xml
| | | | | |____web.xml
| |____pom.xml

Usage:
1. To compile and package the project into WAR file:
   mvn package
2. To start the Tomcat server and deploy the generated WAR file to default port 8080.
   mvn tomcat7:run

Demo:
1.With input limit parameter
http://localhost:8080/KrampHubWebApp/getData?search=michael&limit=2

Response:
KrampHub assignment

Search Result : {"status":200,"data":{"albums":[{"artist":["Michael Jackson"],"title":"The Essential Michael Jackson","kind":"Album"},{"artist":["Michael Jackson"],"title":"Michael","kind":"Album"}],"books":[{"artist":["Michael W. Apple"],"title":"Ideology and Curriculum","kind":"Book"},{"artist":["Michael Heim"],"title":"Virtual Realism","kind":"Book"}]}}

2. Without input limit parameter (Default 5)
http://localhost:8080/KrampHubWebApp/getData?search=michael

Response:
KrampHub assignment

Search Result : {"status":200,"data":{"albums":[{"artist":["Michael Jackson"],"title":"The Essential Michael Jackson","kind":"Album"},{"artist":["Michael Jackson"],"title":"Michael","kind":"Album"},{"artist":["Various Artists"],"title":"Michael (Music from the Motion Picture)","kind":"Album"},{"artist":["Michael Bublé"],"title":"It's Time","kind":"Album"},{"artist":["Tyga"],"title":"Careless World - Rise of the Last King","kind":"Album"}],"books":[{"artist":["Michael W. Apple"],"title":"Ideology and Curriculum","kind":"Book"},{"artist":["Michael Heim"],"title":"Virtual Realism","kind":"Book"},{"artist":["Michael Rosen"],"title":"Dignity","kind":"Book"},{"artist":["Michael J. Sandel"],"title":"Liberalism and the Limits of Justice","kind":"Book"},{"artist":["Michael W. Doyle"],"title":"Empires","kind":"Book"}]}}


3. Without any input parameter
http://localhost:8080/KrampHubWebApp/getData

Response:
HTTP Status 400

4. Result not found
http://localhost:8080/KrampHubWebApp/getData?search=kramphub&limit=2

Response:
KrampHub assignment

Search Result : {"status":404,"data":null}

5. Health
http://localhost:8080/KrampHubWebApp/health

Response:
Project server is running. The time on the server is: July 3, 2017 10:39:45 PM CDT