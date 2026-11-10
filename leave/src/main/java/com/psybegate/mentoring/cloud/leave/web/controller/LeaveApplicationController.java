package com.psybegate.mentoring.cloud.leave.web.controller;

import com.psybegate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybegate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;
import com.psybegate.mentoring.cloud.leave.service.LeaveApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/leave_application/")
public class LeaveApplicationController {

  private final LeaveApplicationService leaveApplicationService;

  @PostMapping("/new")
  public void addNewLeaveApplication(@RequestBody LeaveApplicationRequest request) {
    leaveApplicationService.save(request);
  }

  @PatchMapping("/update/{id}")
  public void updateLaveApplication(@RequestBody LeaveApplicationRequest request, @PathVariable Long id) {
    leaveApplicationService.update(request, id);
  }

  @GetMapping("/{id}")
  public LeaveApplicationResponse getLeaveApplication(@PathVariable Long id) {
    return leaveApplicationService.get(id);
  }

  @GetMapping("{firstDay}/{lastDay}/{sortBy}/{isAscending}")
  public List<LeaveApplicationResponse> getAllLeaveApplicationInRange(
          @PathVariable LocalDate firstDay,
          @PathVariable LocalDate lastDay,
          @PathVariable String sortBy,
          @PathVariable boolean isAscending
  ) {
    return leaveApplicationService.getAllInRange(firstDay, lastDay, sortBy, isAscending);
  }

  @DeleteMapping("{id}")
  public void deleteLeaveApplication(@PathVariable Long id) {
    leaveApplicationService.deactivate(id);
  }

}
