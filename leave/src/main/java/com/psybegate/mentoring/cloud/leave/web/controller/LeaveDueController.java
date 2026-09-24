package com.psybegate.mentoring.cloud.leave.web.controller;

import com.psybegate.mentoring.cloud.leave.service.LeaveDueService;
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
  public double getLeaveDue() {
    return leaveDueService.getLeaveHours();
  }

}
