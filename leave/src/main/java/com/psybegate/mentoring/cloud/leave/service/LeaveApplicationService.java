package com.psybegate.mentoring.cloud.leave.service;

import com.psybegate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybegate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;

import java.util.List;

public interface LeaveApplicationService {

  void save(LeaveApplicationRequest leaveApplication);

  void update(LeaveApplicationRequest leaveApplicationRequest, Long id);

  void delete(Long id);

  LeaveApplicationResponse get(Long id);

  List<LeaveApplicationResponse> getContaining(String value);

  List<LeaveApplicationResponse> getContainingWithDeleted(String value);

  List<LeaveApplicationResponse> getAll();

  List<LeaveApplicationResponse> getAllDeleted();

  List<LeaveApplicationResponse> getAllInclusive();

}
