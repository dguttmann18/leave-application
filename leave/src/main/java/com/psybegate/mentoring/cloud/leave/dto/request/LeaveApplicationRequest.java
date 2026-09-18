package com.psybegate.mentoring.cloud.leave.dto.request;

import com.psybegate.mentoring.cloud.leave.entity.LeaveType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveApplicationRequest {

  private LocalDate firstDay;

  private LocalDate lastDay;

  private boolean isPartial;

  private double hours;

  private LeaveType leaveType;

  private String description;

}
