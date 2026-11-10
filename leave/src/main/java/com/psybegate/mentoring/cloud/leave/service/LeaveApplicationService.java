package com.psybegate.mentoring.cloud.leave.service;

import com.psybegate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybegate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;

import java.time.LocalDate;
import java.util.List;

public interface LeaveApplicationService {

  void save(LeaveApplicationRequest leaveApplication);

  void update(LeaveApplicationRequest leaveApplicationRequest, Long id);

  void deactivate(Long id);

  LeaveApplicationResponse get(Long id);

  List<LeaveApplicationResponse> getContaining(String value);

  List<LeaveApplicationResponse> getContainingWithDeleted(String value);

  List<LeaveApplicationResponse> getAll(String orderBy, boolean isAscending);

  List<LeaveApplicationResponse> getAllDeleted();

  List<LeaveApplicationResponse> getAllInclusive();

  List<LeaveApplicationResponse> getAllInRange(LocalDate firstDay, LocalDate lastDay, String orderBy, boolean isAscending);

}
