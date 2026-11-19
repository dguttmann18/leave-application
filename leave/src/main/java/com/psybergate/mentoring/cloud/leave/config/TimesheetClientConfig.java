package com.psybergate.mentoring.cloud.leave.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class TimesheetClientConfig {

  @Bean
  public RestClient timesheetRestClient(@Value("${timesheet.service.base-url}") String baseUrl) {
    return RestClient.builder()
            .baseUrl(baseUrl)
            .requestInterceptor((request, body, execution) -> {
              String requestId = MDC.get(RequestIdFilter.MDC_KEY);
              if (requestId != null) {
                request.getHeaders().set(RequestIdFilter.HEADER, requestId);
              }
              log.debug("-> {} {}", request.getMethod(), request.getURI());
              return execution.execute(request, body);
            })
            .build();
  }
}
