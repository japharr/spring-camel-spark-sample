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

### How it works
An Apache Camel route is configured to listen for files on a specified directory. On siting a new file, the Apache Camel route stores the file properties like filename, size to a Mongo database collection. The Apache Camel route then moves the file to another directory being listening by Apache Flume agent. The Apache Flume agent pick the file and transfer it to a specified HDFS directory. 

At interval, an Apache Camel scheduler queries the Mongo database collection for pending files, in case there are any, its read the HDFS directory using Apache Spark for the file content and persist it to another Mongo database collection.

### Installation
#### Apache Hadoop
Install and configure Hadoop using Homebrew (on Mac)
````
brew install hadoop
````

#### Apache Flume
Install and configure Flume using Homebrew (on Mac)
````
brew install flume
````

Start a flume-agent using the configuration file (agent.conf) in the flume directory
````
flume-ng agent -n agent -c $FLUME_CONF_DIR -f agent.conf -Dflume.root.logge=INFO
````