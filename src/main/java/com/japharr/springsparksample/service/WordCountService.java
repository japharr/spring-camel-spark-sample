package com.japharr.springsparksample.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WordCountService {
  private final JavaSparkContext sc;

  public WordCountService(JavaSparkContext sc) {
    this.sc = sc;
  }

  public Map<String, Long> getCount(List<String> wordList) {
    log.info("WordCountService: getCount: {}", wordList.size());
    JavaRDD<String> words = sc.parallelize(wordList);
    return words.countByValue();
  }
}
