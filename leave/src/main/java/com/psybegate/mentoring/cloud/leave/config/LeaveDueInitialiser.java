package com.psybegate.mentoring.cloud.leave.config;

import com.psybegate.mentoring.cloud.leave.entity.LeaveDue;
import com.psybegate.mentoring.cloud.leave.repository.LeaveDueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LeaveDueInitialiser {

  private final LeaveDueRepository leaveDueRepository;

  @Bean
  ApplicationRunner initialiseLeaveDue() {
    return args -> {
      if (leaveDueRepository.count() == 0) {
        LeaveDue leaveDue = new LeaveDue();
        leaveDue.setHours(0.0);
        leaveDueRepository.save(leaveDue);
      }
    };
  }
}
