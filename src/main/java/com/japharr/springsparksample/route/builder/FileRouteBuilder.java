package com.japharr.springsparksample.route.builder;

import com.japharr.springsparksample.route.FileDescProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class FileRouteBuilder extends RouteBuilder {
  private final FileDescProcessor fileDescProcessor;

  @Override
  public void configure() throws Exception {
    errorHandler(deadLetterChannel("{{routes.directory.error}}").log(log));

    onException(Exception.class)
        .handled(true)
        .process(exchange -> {
          Exception e = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
          log.error(e.getMessage(), e);
        })
        .to("{{routes.directory.error}}");

    from("{{routes.directory.source}}")
        .process(fileDescProcessor)
        .to("{{routes.directory.flume}}");
  }
}
