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