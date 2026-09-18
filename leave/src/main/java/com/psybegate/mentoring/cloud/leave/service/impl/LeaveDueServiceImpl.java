package com.psybegate.mentoring.cloud.leave.service.impl;

import com.psybegate.mentoring.cloud.leave.repository.LeaveDueRepository;
import com.psybegate.mentoring.cloud.leave.service.LeaveDueService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional(readOnly = true)
public class LeaveDueServiceImpl implements LeaveDueService {

  private final LeaveDueRepository leaveDueRepository;

  @Override
  @Transactional
  public void addLeaveHours(double hours) {
    leaveDueRepository.addLeaveHours(hours);
  }

  @Override
  @Transactional
  public void subtractLeaveHours(double hours) {
    leaveDueRepository.subtractLeaveHours(hours);
  }

  @Override
  public double getLeaveHours() {
    return leaveDueRepository.getHours();
  }
}
