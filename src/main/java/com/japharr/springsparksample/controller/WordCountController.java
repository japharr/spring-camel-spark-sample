package com.japharr.springsparksample.controller;

import com.japharr.springsparksample.service.WordCountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class WordCountController {
  private final WordCountService wordCountService;

  @PostMapping("/wordcount")
  public Map<String, Long> count(@RequestParam(required = true) String words) {
    List<String> wordList = Arrays.asList(words.split("\\s"));
    return wordCountService.getCount(wordList);
  }
}
