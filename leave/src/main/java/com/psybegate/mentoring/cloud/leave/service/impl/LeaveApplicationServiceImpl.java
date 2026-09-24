package com.psybegate.mentoring.cloud.leave.service.impl;

import com.psybegate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybegate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;
import com.psybegate.mentoring.cloud.leave.entity.LeaveApplication;
import com.psybegate.mentoring.cloud.leave.mapper.LeaveApplicationMapper;
import com.psybegate.mentoring.cloud.leave.repository.LeaveApplicationRepository;
import com.psybegate.mentoring.cloud.leave.service.LeaveApplicationService;
import com.psybegate.mentoring.cloud.leave.service.LeaveDueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@Service
@Transactional(readOnly = true)
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  private final LeaveApplicationRepository leaveApplicationRepository;

  private final LeaveApplicationMapper mapper;

  private final LeaveDueService leaveDueService;

  @Transactional
  @Override
  public void save(LeaveApplicationRequest leaveApplicationRequest) {
    LeaveApplication entity = mapper.toEntity(leaveApplicationRequest);
    leaveApplicationRepository.save(entity);

    double hours = entity.getHours();
    leaveDueService.subtractLeaveHours(hours);
  }

  @Transactional
  @Override
  public void update(LeaveApplicationRequest leaveApplicationRequest, Long id) {
    LeaveApplication entity = leaveApplicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("LeaveApplication " + id + " not found"));

    double oldHours = entity.getHours();
    double newHours = leaveApplicationRequest.getHours();

    mapper.updateEntityFromRequest(leaveApplicationRequest, entity);
    leaveApplicationRepository.save(entity);

    double difference = newHours - oldHours;
    leaveDueService.addLeaveHours(difference);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    LeaveApplicationResponse dto = get(id);
    double hours = dto.getHours();
    leaveApplicationRepository.deactivate(id);
    leaveDueService.addLeaveHours(hours);
  }

  @Override
  public LeaveApplicationResponse get(Long id) {
    LeaveApplication entity = leaveApplicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("LeaveApplication " + id + " not found"));

    return mapper.toResponse(entity);
  }

  @Override
  public List<LeaveApplicationResponse> getContaining(String value) {
    return mapper.toResponseList(leaveApplicationRepository.getAllLeaveApplicationsContaining(value));
  }

  @Override
  public List<LeaveApplicationResponse> getContainingWithDeleted(String value) {
    return mapper.toResponseList(leaveApplicationRepository.getAllLeaveApplicationsContaining(value));
  }

  @Override
  public List<LeaveApplicationResponse> getAll() {
    return mapper.toResponseList(leaveApplicationRepository.getAllLeaveApplications());
  }

  @Override
  public List<LeaveApplicationResponse> getAllDeleted() {
    return mapper.toResponseList(leaveApplicationRepository.getAllDeactivatedLeaveApplications());
  }

  @Override
  public List<LeaveApplicationResponse> getAllInclusive() {
    return mapper.toResponseList(leaveApplicationRepository.getAllLeaveApplicationsInclusive());
  }
}
