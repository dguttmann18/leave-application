package com.psybergate.mentoring.cloud.leave.web.controller;

import com.psybergate.mentoring.cloud.leave.dto.response.LeaveDueResponse;
import com.psybergate.mentoring.cloud.leave.mapper.LeaveDueMapper;
import com.psybergate.mentoring.cloud.leave.service.LeaveDueService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leave_due")
public class LeaveDueController {

  private final LeaveDueService leaveDueService;

  @PatchMapping("/add_hours")
  public void addLeaveHours(@RequestParam double hours) {
    leaveDueService.addLeaveHours(hours);
  }

  @PatchMapping("/subtract_hours")
  public void subtractLeaveHours(@RequestParam double hours) {
    leaveDueService.subtractLeaveHours(hours);
  }

  @GetMapping
  public LeaveDueResponse getLeaveDue() {
    double hours = leaveDueService.getLeaveHours();
    LeaveDueResponse dto = LeaveDueMapper.hoursToDto(hours);
    return dto;
  }

}
