package com.psybergate.mentoring.cloud.leave.repository;

import com.psybergate.mentoring.cloud.leave.entity.LeaveApplication;
import com.psybergate.mentoring.cloud.leave.entity.LeaveType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

  Optional<LeaveApplication> findByIdAndActiveTrue(Long id);

  List<LeaveApplication> findByActiveTrue(Sort sort);

  List<LeaveApplication> findByActiveFalse(Sort sort);

  List<LeaveApplication> findByActiveTrueAndFirstDayLessThanEqualAndLastDayGreaterThanEqual(LocalDate lastDate, LocalDate firstDate, Sort sort);

  List<LeaveApplication> findByActiveTrueAndLeaveType(LeaveType leaveType, Sort sort);

  List<LeaveApplication> findByActiveFalseAndLeaveType(LeaveType leaveType, Sort sort);

}
