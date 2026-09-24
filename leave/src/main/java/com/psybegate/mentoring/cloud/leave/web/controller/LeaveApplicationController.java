package com.psybegate.mentoring.cloud.leave.web.controller;

import com.psybegate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybegate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;
import com.psybegate.mentoring.cloud.leave.service.LeaveApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/all")
  public List<LeaveApplicationResponse> getAllLeaveApplications(
          @RequestParam(required = false) String value) {
    return value == null
            ? leaveApplicationService.getAll()
            : leaveApplicationService.getContaining(value);
  }

}
