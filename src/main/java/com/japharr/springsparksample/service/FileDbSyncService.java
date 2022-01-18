package com.japharr.springsparksample.service;

import com.japharr.springsparksample.entity.FileDesc;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.WriteConfig;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDbSyncService {
  @Value("${hdfs.dir}")
  private String hdfsDir;

  private final JavaSparkContext sc;

  public boolean sync(FileDesc desc) {
    log.info("about to sync file: {}, file-size: {}, to db", desc.getFilename(), desc.getSize());

    String filePath = String.format("%s/%s", hdfsDir, desc.getFilename());

    JavaPairRDD<String, String> fileRdd = sc.wholeTextFiles(filePath);

    // Get the size of the file in the HDFS
    long fileSize = fileRdd.map(r -> getFileSize(r._1)).first();
    log.info("HDFS file-size: {}", fileSize);

    // Check if the file-size is equal to the file-size in the DB within -+5
    //if(Math.abs(fileSize - desc.getSize()) <= 5) {
      JavaRDD<String> fileContentRdd = sc.textFile(filePath);

      long count = fileContentRdd.count();
      System.out.println("count: " + count);

      // Split the csv file to field[index]-value and create a document
      JavaRDD<Document> documents = fileContentRdd.map(r -> {
        Document entity = new Document("_id", new ObjectId());
        String[] result = r.split(",");
        for(int i=0; i<result.length; i++) {
          entity.append(String.format("field%d", i+1), result[i]);
        }
        return entity;
      });

      Map<String, String> writeOverrides = new HashMap<>();
      writeOverrides.put("collection", "spark");
      writeOverrides.put("writeConcern.w", "majority");
      WriteConfig writeConfig = WriteConfig.create(sc).withOptions(writeOverrides);

      MongoSpark.save(documents, writeConfig);
      return true;
    //}
    //return false;
  }

  public static long getFileSize(String filePath) throws IOException, FileNotFoundException {
    Configuration config = new Configuration();
    Path path = new Path(filePath);
    FileSystem hdfs = path.getFileSystem(config);
    ContentSummary cSummary = hdfs.getContentSummary(path);
    return cSummary.getLength();
  }
}
