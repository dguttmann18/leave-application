package com.psybergate.mentoring.cloud.leave.client.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveTimeSheetRequest {

  private Long leaveApplicationId;

  private LocalDate firstDay;

  private LocalDate lastDay;

  private double hours;

  private String description;
}
