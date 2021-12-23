A Simple Spring-Camel-Spark Application
===
This simple Spring-Boot application demonstrates the use of Apache Spark to read a csv-file from HDFS and persist its content to a Mongodb Database.

It makes use of the following dependencies:

- Apache Camel 
- Apache Spark
- Hadoop File System (HDFS)
- Mongodb 
- Java 11 
- Spring-boot

### To compile
````
./mvnw clean package
````

### To execute
Provide the necessary configuration parameters in the application.properties

````
./mvnw spring-boot:run
````

### Using Apache Flume
Modify the flume agent configuration file in '/flume' and provide the spooling directory on your file-system that will match the 'routes.directory.flume' property in the 'application.properties', then start a flume agent using the configuration file.