package com.psybegate.mentoring.cloud.leave.repository;

import com.psybegate.mentoring.cloud.leave.entity.LeaveApplication;
import com.psybegate.mentoring.cloud.leave.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = true")
  List<LeaveApplication> getAllLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = false")
  List<LeaveApplication> getAllDeactivatedLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l")
  List<LeaveApplication> getAllLeaveApplicationsInclusive();

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = true AND l.description LIKE %:value%")
  List<LeaveApplication> getAllLeaveApplicationsContaining(@Param("value") String value);

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = false AND l.description LIKE %:value%")
  List<LeaveApplication> getAllLeaveApplicationsDeactivatedContaining(@Param("value") String value);

  @Query("SELECT l FROM LeaveApplication l WHERE l.description LIKE %:value%")
  List<LeaveApplication> getAllLeaveDeactivatedApplicationsInclusiveContaining(@Param("value") String value);

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = true AND l.leaveType = :leaveType")
  List<LeaveApplication> getAllLeaveApplicationsOfType(@Param("leaveType") LeaveType leaveType);

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = false AND l.leaveType = :leaveType")
  List<LeaveApplication> getAllLeaveApplicationsDeactivatedOfType(@Param("leaveType") LeaveType leaveType);

  @Query("SELECT l FROM LeaveApplication l WHERE l.leaveType = :leaveType")
  List<LeaveApplication> getAllLeaveApplicationsInclusiveOfType(@Param("leaveType") LeaveType leaveType);

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = true AND l.firstDay >= :firstDate AND l.lastDay <= :lastDate")
  List<LeaveApplication> getAllLeaveApplicationsInRange(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = false AND l.firstDay >= :firstDate AND l.lastDay <= :lastDate")
  List<LeaveApplication> getAllLeaveApplicationsDeactivatedInRange(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

  @Query("SELECT l FROM LeaveApplication l WHERE l.firstDay >= :firstDate AND l.lastDay <= :lastDate")
  List<LeaveApplication> getAllLeaveApplicationsInclusiveInRange(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = true AND l.isPartial")
  List<LeaveApplication> getAllPartialLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = false AND l.isPartial")
  List<LeaveApplication> getAllDeactivatedPartialLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l WHERE l.isPartial")
  List<LeaveApplication> getAllInclusivePartialLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = true AND l.isPartial = false")
  List<LeaveApplication> getAllNonPartialLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l WHERE l.active = false AND l.isPartial = false")
  List<LeaveApplication> getAllDeactivatedNonPartialLeaveApplications();

  @Query("SELECT l FROM LeaveApplication l WHERE l.isPartial = false")
  List<LeaveApplication> getAllInclusiveNonPartialLeaveApplications();

  @Modifying
  @Query("UPDATE LeaveApplication l SET l.active = false WHERE l.id = :id")
  void deactivate(@Param("id") Long id);

  @Modifying
  @Query("UPDATE LeaveApplication l SET l.active = false WHERE l.id = :id")
  void activate(@Param("id") Long id);

}
