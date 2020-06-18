Living As One NASA Mars Rover Programming Exercise

I really enjoyed this exercise. I did not have time to complete all aspects of the project such as image display in the web browser, doing performance analysis, code coverage analysis with SonarQube, or complete the Docker file. But I did get some of the unit tests started and I enabled/tested the <b>maven</b> <i>run</i>, <i>test</i> and <i>package</i> capabilities. So it's, by far, not code-complete.

The application dynamically builds the REST webservice URL from the list of dates provided and the API key. From this URL, the application calls the NASA webservice with a HEAD resource and pulls the ETag down and it checks if the image has been moved to a new location using the 301 resource code. Then it checks the local cache file to see if the image has been previously downloaded or if it has changed by comparing the Etag of the cache for the given image ID, if it exists. If it is not found, it downloads the file, placing it in the target directory and updates the cache file. The application follows one level of redirection at this point.

<br>

<b><u>There are a few steps to complete to run the application:</u></b>

<br>

1.) Once the project has been cloned, edit the application.properties file.

https://github.com/clotzer/lao-mars-rover/blob/master/lao-mars-rover/src/main/resources/application.properties

Set the <b><i>config.nasa_key</i></b> to your API key, set the <i><b>config.local_image_store_path</i></b> to your local path where you want the images stored. In addition to the image files, this directory will include the <i><b>date_list.json</i></b> file and the <i><b>image_list.dat</i></b> file.

<br>

2.) Create a file called <i><b>date_list.json</i></b> in the <i><b>config.local_image_store_path</i></b> folder. This file should be in the following format:
```
[
	"2015-6-3",
	"02/27/17",
	"April 31, 2018",
	"Jul-13-2016",
	"June 2, 2018"
]
```

The more dates added to the file, the longer it will take to run.

<br>

3.) To run the application from the command line using maven type:
```
mvn spring-boot:run
```
You will see a generous amount of log output and you should start seeing image files appear in your target directory. The application should also write an <b>image_list.dat</b> file in the target directory once it has completed the download sequence.

You should see messages such as this:
```
"Downloading file id <photo id>."
```

An example of the log output for the first run for a single date is shown here:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.1.RELEASE)

2020-06-16 16:53:56.425  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Starting LaoMarsRoverApplication on MediaServer with PID 2516 (C:\Users\clotzer_2\Documents\workspace-sts\lao-mars-rover\target\classes started by clotzer in C:\Users\clotzer_2\Documents\workspace-sts\lao-mars-rover)
2020-06-16 16:53:56.429  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : No active profile set, falling back to default profiles: default
2020-06-16 16:53:58.724  INFO 2516 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-06-16 16:53:58.743  INFO 2516 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-06-16 16:53:58.743  INFO 2516 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.27]
2020-06-16 16:53:58.985  INFO 2516 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-06-16 16:53:58.986  INFO 2516 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2447 ms
2020-06-16 16:54:00.976  INFO 2516 --- [           main] c.c.marsrover.controller.FileHandler     : [2015-6-3]
2020-06-16 16:54:01.061  INFO 2516 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-06-16 16:54:01.403  INFO 2516 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 14 endpoint(s) beneath base path '/actuator'
2020-06-16 16:54:01.501  INFO 2516 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-06-16 16:54:01.508  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Started LaoMarsRoverApplication in 5.675 seconds (JVM running for 7.111)
2020-06-16 16:54:01.517  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Config [nasaLogin=c12345ll@gmail.com, nasaKey=UvSi0hhJWpM1e3vm18MQ6XCzGvwUFv44x3rtFi0f, nasaEarthDateBaseUri=https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos, earthDateFormat=yyyy-M-d, queryStringOne=earth_date, queryStringTwo=api_key, localImageStorePath=/images, localImageListName=image_list.dat, imageQueryDateFile=date_list.json]
2020-06-16 16:54:01.518  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : [2015-6-3]
2020-06-16 16:54:01.520  INFO 2516 --- [           main] com.clotzer.marsrover.controller.Config  : Wed Jun 03 00:00:00 CDT 2015
2020-06-16 16:54:01.521  INFO 2516 --- [           main] com.clotzer.marsrover.controller.Config  : https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&api_key=UvSi0hhJWpM1e3vm18MQ6XCzGvwUFv44x3rtFi0f
2020-06-16 16:54:02.407  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102685, sol=1004, camera=Camera [id=20, name=FHAZ, rover_id=5, full_name=Front Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:54:03.082  INFO 2516 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102685, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8801a51a235d3c8a2e2d072ef65fd07d, response_code=200, content_length=407617, last_downloaded_date=null]
2020-06-16 16:54:03.348  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Downloading file id 102685.
2020-06-16 16:54:03.348  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102686, sol=1004, camera=Camera [id=20, name=FHAZ, rover_id=5, full_name=Front Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:54:03.472  INFO 2516 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102686, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8167e7fcef76d7176281eb2e2bc0b1dd, response_code=200, content_length=436763, last_downloaded_date=null]
2020-06-16 16:54:03.655  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Downloading file id 102686.
2020-06-16 16:54:03.655  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102842, sol=1004, camera=Camera [id=21, name=RHAZ, rover_id=5, full_name=Rear Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:54:03.796  INFO 2516 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102842, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6f55e03754da910bc3fa0a72255963e2, response_code=200, content_length=383413, last_downloaded_date=null]
2020-06-16 16:54:03.859  INFO 2516 --- [on(1)-127.0.0.1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-06-16 16:54:03.859  INFO 2516 --- [on(1)-127.0.0.1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-06-16 16:54:03.874  INFO 2516 --- [on(1)-127.0.0.1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 14 ms
2020-06-16 16:54:03.995  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Downloading file id 102842.
2020-06-16 16:54:03.995  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102843, sol=1004, camera=Camera [id=21, name=RHAZ, rover_id=5, full_name=Rear Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:54:04.109  INFO 2516 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102843, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6db2517a32a6a6312cc97a01ebc56361, response_code=200, content_length=359642, last_downloaded_date=null]
2020-06-16 16:54:04.300  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Downloading file id 102843.
2020-06-16 16:54:04.302  INFO 2516 --- [           main] c.c.marsrover.controller.FileHandler     : Key = 102686, Value = ImageHeader [id=102686, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8167e7fcef76d7176281eb2e2bc0b1dd, response_code=200, content_length=436763, last_downloaded_date=2020-06-16]
2020-06-16 16:54:04.304  INFO 2516 --- [           main] c.c.marsrover.controller.FileHandler     : Key = 102685, Value = ImageHeader [id=102685, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8801a51a235d3c8a2e2d072ef65fd07d, response_code=200, content_length=407617, last_downloaded_date=2020-06-16]
2020-06-16 16:54:04.304  INFO 2516 --- [           main] c.c.marsrover.controller.FileHandler     : Key = 102842, Value = ImageHeader [id=102842, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6f55e03754da910bc3fa0a72255963e2, response_code=200, content_length=383413, last_downloaded_date=2020-06-16]
2020-06-16 16:54:04.305  INFO 2516 --- [           main] c.c.marsrover.controller.FileHandler     : Key = 102843, Value = ImageHeader [id=102843, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6db2517a32a6a6312cc97a01ebc56361, response_code=200, content_length=359642, last_downloaded_date=2020-06-16]
2020-06-16 16:54:04.305  INFO 2516 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Finished writing download file...
2020-06-16 16:54:04.313  INFO 2516 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
```

<br>

4.) Re-running the application with the same date file should result in log messages such as:

```
"File id <photo id> has already been downloaded!"
```

A log output running a second time is shown here:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.1.RELEASE)

2020-06-16 16:49:49.258  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Starting LaoMarsRoverApplication on MediaServer with PID 6104 (C:\Users\clotzer_2\Documents\workspace-sts\lao-mars-rover\target\classes started by clotzer in C:\Users\clotzer_2\Documents\workspace-sts\lao-mars-rover)
2020-06-16 16:49:49.266  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : No active profile set, falling back to default profiles: default
2020-06-16 16:49:52.379  INFO 6104 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2020-06-16 16:49:52.392  INFO 6104 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-06-16 16:49:52.392  INFO 6104 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.27]
2020-06-16 16:49:52.559  INFO 6104 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-06-16 16:49:52.559  INFO 6104 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2711 ms
2020-06-16 16:49:53.629  INFO 6104 --- [           main] c.c.marsrover.controller.FileHandler     : [2015-6-3]
ImageHeader [id=102686, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8167e7fcef76d7176281eb2e2bc0b1dd, response_code=200, content_length=436763, last_downloaded_date=2020-06-16]
ImageHeader [id=102685, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8801a51a235d3c8a2e2d072ef65fd07d, response_code=200, content_length=407617, last_downloaded_date=2020-06-16]
ImageHeader [id=102842, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6f55e03754da910bc3fa0a72255963e2, response_code=200, content_length=383413, last_downloaded_date=2020-06-16]
ImageHeader [id=102843, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6db2517a32a6a6312cc97a01ebc56361, response_code=200, content_length=359642, last_downloaded_date=2020-06-16]
2020-06-16 16:49:53.717  INFO 6104 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-06-16 16:49:54.095  INFO 6104 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 14 endpoint(s) beneath base path '/actuator'
2020-06-16 16:49:54.183  INFO 6104 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2020-06-16 16:49:54.188  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Started LaoMarsRoverApplication in 5.57 seconds (JVM running for 7.457)
2020-06-16 16:49:54.198  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Config [nasaLogin=c12345ll@gmail.com, nasaKey=UvSi0hhJWpM1e3vm18MQ6XCzGvwUFv44x3rtFi0f, nasaEarthDateBaseUri=https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos, earthDateFormat=yyyy-M-d, queryStringOne=earth_date, queryStringTwo=api_key, localImageStorePath=/images, localImageListName=image_list.dat, imageQueryDateFile=date_list.json]
2020-06-16 16:49:54.198  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : [2015-6-3]
2020-06-16 16:49:54.200  INFO 6104 --- [           main] com.clotzer.marsrover.controller.Config  : Wed Jun 03 00:00:00 CDT 2015
2020-06-16 16:49:54.201  INFO 6104 --- [           main] com.clotzer.marsrover.controller.Config  : https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&api_key=UvSi0hhJWpM1e3vm18MQ6XCzGvwUFv44x3rtFi0f
2020-06-16 16:49:55.457  INFO 6104 --- [on(1)-127.0.0.1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2020-06-16 16:49:55.458  INFO 6104 --- [on(1)-127.0.0.1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-06-16 16:49:55.468  INFO 6104 --- [on(1)-127.0.0.1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 10 ms
2020-06-16 16:49:55.489  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102685, sol=1004, camera=Camera [id=20, name=FHAZ, rover_id=5, full_name=Front Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:49:56.817  INFO 6104 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102685, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8801a51a235d3c8a2e2d072ef65fd07d, response_code=200, content_length=407617, last_downloaded_date=null]
2020-06-16 16:49:56.818  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : ImageHeader [id=102685, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FLB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8801a51a235d3c8a2e2d072ef65fd07d, response_code=200, content_length=407617, last_downloaded_date=2020-06-16]
2020-06-16 16:49:56.819  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Existing ETag: [8801a51a235d3c8a2e2d072ef65fd07d]
2020-06-16 16:49:56.820  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Download ETag: [8801a51a235d3c8a2e2d072ef65fd07d]
2020-06-16 16:49:56.820  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : File id 102685 has already been downloaded!
2020-06-16 16:49:56.820  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102686, sol=1004, camera=Camera [id=20, name=FHAZ, rover_id=5, full_name=Front Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:49:56.914  INFO 6104 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102686, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8167e7fcef76d7176281eb2e2bc0b1dd, response_code=200, content_length=436763, last_downloaded_date=null]
2020-06-16 16:49:56.914  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : ImageHeader [id=102686, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/fcam/FRB_486615455EDR_F0481570FHAZ00323M_.JPG, e_tag=8167e7fcef76d7176281eb2e2bc0b1dd, response_code=200, content_length=436763, last_downloaded_date=2020-06-16]
2020-06-16 16:49:56.915  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Existing ETag: [8167e7fcef76d7176281eb2e2bc0b1dd]
2020-06-16 16:49:56.916  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Download ETag: [8167e7fcef76d7176281eb2e2bc0b1dd]
2020-06-16 16:49:56.916  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : File id 102686 has already been downloaded!
2020-06-16 16:49:56.916  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102842, sol=1004, camera=Camera [id=21, name=RHAZ, rover_id=5, full_name=Rear Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:49:57.089  INFO 6104 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102842, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6f55e03754da910bc3fa0a72255963e2, response_code=200, content_length=383413, last_downloaded_date=null]
2020-06-16 16:49:57.089  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : ImageHeader [id=102842, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RLB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6f55e03754da910bc3fa0a72255963e2, response_code=200, content_length=383413, last_downloaded_date=2020-06-16]
2020-06-16 16:49:57.089  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Existing ETag: [6f55e03754da910bc3fa0a72255963e2]
2020-06-16 16:49:57.089  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Download ETag: [6f55e03754da910bc3fa0a72255963e2]
2020-06-16 16:49:57.089  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : File id 102842 has already been downloaded!
2020-06-16 16:49:57.089  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Photo [id=102843, sol=1004, camera=Camera [id=21, name=RHAZ, rover_id=5, full_name=Rear Hazard Avoidance Camera], img_src=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, earth_date=2015-06-03]
2020-06-16 16:49:57.357  INFO 6104 --- [           main] c.c.marsrover.controller.HttpHandler     : ImageHeader [id=102843, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6db2517a32a6a6312cc97a01ebc56361, response_code=200, content_length=359642, last_downloaded_date=null]
2020-06-16 16:49:57.358  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : ImageHeader [id=102843, url=http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01004/opgs/edr/rcam/RRB_486615482EDR_F0481570RHAZ00323M_.JPG, e_tag=6db2517a32a6a6312cc97a01ebc56361, response_code=200, content_length=359642, last_downloaded_date=2020-06-16]
2020-06-16 16:49:57.358  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Existing ETag: [6db2517a32a6a6312cc97a01ebc56361]
2020-06-16 16:49:57.358  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : Download ETag: [6db2517a32a6a6312cc97a01ebc56361]
2020-06-16 16:49:57.358  INFO 6104 --- [           main] c.c.m.ws.LaoMarsRoverApplication         : File id 102843 has already been downloaded!
2020-06-16 16:49:57.366  INFO 6104 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Shutting down ExecutorService 'applicationTaskExecutor'
```

