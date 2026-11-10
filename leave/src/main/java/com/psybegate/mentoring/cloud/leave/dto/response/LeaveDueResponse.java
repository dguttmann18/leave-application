package com.psybegate.mentoring.cloud.leave.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LeaveDueResponse {

  private int days;

  private double hours;

  public void incrementDays() {
    days++;
  }
}
