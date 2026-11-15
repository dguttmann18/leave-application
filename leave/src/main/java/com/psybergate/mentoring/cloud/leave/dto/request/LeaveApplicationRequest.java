package com.psybergate.mentoring.cloud.leave.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.psybergate.mentoring.cloud.leave.entity.LeaveType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveApplicationRequest {

  private LocalDate firstDay;

  private LocalDate lastDay;

  @JsonProperty("isPartial")
  private boolean isPartial;

  private double hours;

  private LeaveType leaveType;

  private String description;

}
