package com.japharr.springsparksample.config;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {
  @Value("${spark.app.name}")
  private String appName;
  @Value("${spark.master}")
  private String masterUri;
  @Value("${spring.data.mongodb.spark.uri}")
  private String mongodbUri;

  @Bean
  public SparkSession session() {
    return SparkSession.builder()
        .appName(appName)
        .master(masterUri)
        .config("spark.mongodb.input.uri", mongodbUri)
        .config("spark.mongodb.output.uri", mongodbUri)
        .getOrCreate();
  }

  @Bean
  public JavaSparkContext sparkContext() {
    return new JavaSparkContext(session().sparkContext());
  }
}
