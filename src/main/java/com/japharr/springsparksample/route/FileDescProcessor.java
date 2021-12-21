package com.japharr.springsparksample.route;

import com.japharr.springsparksample.entity.FileDesc;
import com.japharr.springsparksample.repository.FileDescRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@AllArgsConstructor
public class FileDescProcessor implements Processor {
  private final FileDescRepository fileDescRepository;

  @Override
  public void process(Exchange exchange) throws Exception {
    log.info("FileDescProcessor");
    String filename = exchange.getIn().getHeader(Exchange.FILE_NAME).toString();

    log.info("Processing file: {}", filename);
    File file = exchange.getIn().getBody(File.class);

    if(file != null && file.exists()) {
      log.info("file exist!");
      Path path = Paths.get(file.toURI());
      long bytes = Files.size(path);

      log.info("Saving file: {}, to db", filename);
      fileDescRepository.save(new FileDesc(filename, bytes));
    }
  }
}
