package com.japharr.springsparksample.route;

import com.japharr.springsparksample.entity.FileDesc;
import com.japharr.springsparksample.repository.FileDescRepository;
import com.japharr.springsparksample.service.FileDbSyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class FileDbSyncProcessor implements Processor {
  private final FileDescRepository fileDescRepository;
  private final FileDbSyncService fileDbSyncService;

  @Override
  public void process(Exchange exchange) throws Exception {
    log.info("FileDbSyncProcessor triggered");
    List<FileDesc> pendingFiles = fileDescRepository.findAllBySyncedOnIsNull();
    log.info("FileDbSyncProcessor: pending-files: {}", pendingFiles.size());
    if(!pendingFiles.isEmpty()) {
      pendingFiles.forEach(fileDbSyncService::sync);
    }
    log.info("FileDbSyncProcessor completed successfully.");
  }
}
