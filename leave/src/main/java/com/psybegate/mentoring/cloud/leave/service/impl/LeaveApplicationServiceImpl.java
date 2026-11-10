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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
  public void deactivate(Long id) {
    LeaveApplication leave = leaveApplicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Leave application " + id + " not found"));
    leave.setActive(false);
  }

  @Override
  public LeaveApplicationResponse get(Long id) {
    LeaveApplication entity = leaveApplicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("LeaveApplication " + id + " not found"));

    return mapper.toResponse(entity);
  }

  @Override
  public List<LeaveApplicationResponse> getContaining(String value) {
    return null;
  }

  @Override
  public List<LeaveApplicationResponse> getContainingWithDeleted(String value) {
    return null;
  }

  @Override
  public List<LeaveApplicationResponse> getAll(String orderBy, boolean isAscending) {
    List<LeaveApplication> list = isAscending
            ? leaveApplicationRepository.findByActiveTrue(Sort.by(Sort.Direction.ASC, orderBy))
            : leaveApplicationRepository.findByActiveTrue(Sort.by(Sort.Direction.DESC, orderBy));
    return mapper.toResponseList(list);
  }

  @Override
  public List<LeaveApplicationResponse> getAllDeleted() {
    return null;
  }

  @Override
  public List<LeaveApplicationResponse> getAllInclusive() {
    return null;
  }

  @Override
  public List<LeaveApplicationResponse> getAllInRange(LocalDate firstDay, LocalDate lastDay, String orderBy, boolean isAscending) {
    List<LeaveApplication> list = isAscending
            ? leaveApplicationRepository.findByActiveTrueAndFirstDayLessThanEqualAndLastDayGreaterThanEqual(lastDay, firstDay, Sort.by(Sort.Direction.ASC, orderBy))
            : leaveApplicationRepository.findByActiveTrueAndFirstDayLessThanEqualAndLastDayGreaterThanEqual(lastDay, firstDay,Sort.by(Sort.Direction.DESC, orderBy));
    return mapper.toResponseList(list);
  }
}
