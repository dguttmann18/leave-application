package com.psybergate.mentoring.cloud.leave.service.impl;

import com.psybergate.mentoring.cloud.leave.client.TimeSheetClient;
import com.psybergate.mentoring.cloud.leave.client.dto.LeaveTimeSheetRequest;
import com.psybergate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybergate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;
import com.psybergate.mentoring.cloud.leave.entity.LeaveApplication;
import com.psybergate.mentoring.cloud.leave.mapper.LeaveApplicationMapper;
import com.psybergate.mentoring.cloud.leave.repository.LeaveApplicationRepository;
import com.psybergate.mentoring.cloud.leave.service.LeaveApplicationService;
import com.psybergate.mentoring.cloud.leave.service.LeaveDueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  private final LeaveApplicationRepository leaveApplicationRepository;

  private final LeaveApplicationMapper mapper;

  private final LeaveDueService leaveDueService;

  private final TimeSheetClient timeSheetClient;

  @Transactional
  @Override
  public void save(LeaveApplicationRequest leaveApplicationRequest) {
    LeaveApplication entity = mapper.toEntity(leaveApplicationRequest);
    LeaveApplication saved = leaveApplicationRepository.save(entity);

    double hours = entity.getHours();
    leaveDueService.subtractLeaveHours(hours);

    timeSheetClient.createLeaveEntries(toTimeSheetRequest(entity));
    log.info("Created leave application {} ({} to {}, {}h)", saved.getId(), saved.getFirstDay(), saved.getLastDay(), saved.getHours());
  }

  @Transactional
  @Override
  public void update(LeaveApplicationRequest leaveApplicationRequest, Long id) {
    LeaveApplication entity = leaveApplicationRepository.findByIdAndActiveTrue(id)
            .orElseThrow(() -> new EntityNotFoundException("LeaveApplication " + id + " not found"));
    double oldHours = entity.getHours();
    double newHours = leaveApplicationRequest.getHours();

    mapper.updateEntityFromRequest(leaveApplicationRequest, entity);
    leaveApplicationRepository.save(entity);

    double difference = newHours - oldHours;
    leaveDueService.subtractLeaveHours(difference);
    timeSheetClient.createLeaveEntries(toTimeSheetRequest(entity));
    log.info("Updated leave application {} ({} to {}): {}h -> {}h",
            id, entity.getFirstDay(), entity.getLastDay(), oldHours, newHours);
  }

  @Transactional
  public void deactivate(Long id) {
    LeaveApplication leave = leaveApplicationRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Leave application " + id + " not found"));
    if (!leave.isActive()) throw new IllegalStateException(
            "LeaveApplication " + id + " is already cancelled"
    );
    leave.setActive(false);
    leaveDueService.addLeaveHours(leave.getHours());
    timeSheetClient.deleteLeaveEntries(id);
    log.info("Cancelled leave application {}, refunded {}h", id, leave.getHours());
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
            : leaveApplicationRepository.findByActiveTrueAndFirstDayLessThanEqualAndLastDayGreaterThanEqual(lastDay, firstDay, Sort.by(Sort.Direction.DESC, orderBy));
    return mapper.toResponseList(list);
  }

  private LeaveTimeSheetRequest toTimeSheetRequest(LeaveApplication entity) {
    LeaveTimeSheetRequest request = new LeaveTimeSheetRequest();
    request.setLeaveApplicationId(entity.getId());
    request.setFirstDay(entity.getFirstDay());
    request.setLastDay(entity.getLastDay());
    request.setHours(entity.getHours());
    request.setDescription(buildDescription(entity));
    return request;
  }

  private String buildDescription(LeaveApplication entity) {
    String type = entity.getLeaveType() != null
            ? entity.getLeaveType().name()
            : "LEAVE";
    String description = entity.getDescription();
    return (description == null || description.isBlank())
            ? type + " leave"
            : type + " leave - " + description;
  }

}
