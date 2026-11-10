package com.psybegate.mentoring.cloud.leave.repository;

import com.psybegate.mentoring.cloud.leave.entity.LeaveApplication;
import com.psybegate.mentoring.cloud.leave.entity.LeaveType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

  List<LeaveApplication> findByActiveTrue(Sort sort);

  List<LeaveApplication> findByActiveFalse(Sort sort);

  List<LeaveApplication> findByActiveTrueAndFirstDayLessThanEqualAndLastDayGreaterThanEqual(LocalDate lastDate, LocalDate firstDate, Sort sort);

  List<LeaveApplication> findByActiveTrueAndLeaveType(LeaveType leaveType, Sort sort);

  List<LeaveApplication> findByActiveFalseAndLeaveType(LeaveType leaveType, Sort sort);

}
