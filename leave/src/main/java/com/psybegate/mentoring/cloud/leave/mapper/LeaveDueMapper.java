package com.psybegate.mentoring.cloud.leave.mapper;

import com.psybegate.mentoring.cloud.leave.dto.response.LeaveDueResponse;

public class LeaveDueMapper {

  public static LeaveDueResponse hoursToDto(double hours) {
    LeaveDueResponse dto = new LeaveDueResponse();
    while (hours > 8) {
      dto.incrementDays();
      hours -= 8;
    }
    dto.setHours(hours);
    return dto;
  }
}
