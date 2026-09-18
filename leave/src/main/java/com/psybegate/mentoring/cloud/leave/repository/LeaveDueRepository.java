package com.psybegate.mentoring.cloud.leave.repository;

import com.psybegate.mentoring.cloud.leave.entity.LeaveDue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LeaveDueRepository extends JpaRepository<LeaveDue, Long> {

  @Modifying
  @Query("UPDATE LeaveDue ld SET ld.hours = ld.hours + :hours")
  void addLeaveHours(@Param("hours") double hours);

  @Modifying
  @Query("UPDATE LeaveDue ld SET ld.hours = ld.hours - :hours")
  void subtractLeaveHours(@Param("hours") double hours);

  @Query("SELECT ld.hours FROM LeaveDue ld")
  double getHours();
}
