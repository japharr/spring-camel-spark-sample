package com.japharr.springsparksample.service;

import com.japharr.springsparksample.entity.FileDesc;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDbSyncService {

  @Value("${hdfs.dir}")
  private String hdfsDir;

  private final JavaSparkContext sc;

  public void sync(FileDesc desc) {
    log.info("about to sync file: {}, file-size: {}, to db", desc.getFilename(), desc.getSize());

    String filePath = String.format("%s/%s", hdfsDir, desc.getFilename());

    JavaPairRDD<String, String> fileRdd = sc.wholeTextFiles(filePath);

    // Get the size of the file in the HDFS
    long fileSize = fileRdd.map(r -> getFileSize(r._1)).first();
    log.info("HDFS file-size: {}", fileSize);

    // Check if the file-size is equal to the file-size in the DB
    if(fileSize == desc.getSize()) {
      JavaRDD<String> fileContentRdd = sc.textFile(filePath);

      // Print the content of the file line by line
      fileContentRdd.collect().forEach(System.out::println);
    }
  }

  public long getFileSize(String filePath) throws IOException, FileNotFoundException {
    Configuration config = new Configuration();
    Path path = new Path(filePath);
    FileSystem hdfs = path.getFileSystem(config);
    ContentSummary cSummary = hdfs.getContentSummary(path);
    return cSummary.getLength();
  }
}
