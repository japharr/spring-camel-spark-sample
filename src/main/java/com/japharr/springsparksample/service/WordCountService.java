package com.japharr.springsparksample.service;

import lombok.AllArgsConstructor;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class WordCountService {
  private final JavaSparkContext sc;

  public Map<String, Long> getCount(List<String> wordList) {
    JavaRDD<String> words = sc.parallelize(wordList);
    return words.countByValue();
  }
}
