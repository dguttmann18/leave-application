package com.psybegate.mentoring.cloud.leave.dto.response;

import com.psybegate.mentoring.cloud.leave.entity.LeaveType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class LeaveApplicationResponse {

  private Long id;

  private LocalDateTime created;

  private LocalDateTime updated;

  private boolean active;

  private LocalDate firstDay;

  private LocalDate lastDay;

  private boolean isPartial;

  private double hours;

  private LeaveType leaveType;

  private String description;
}
