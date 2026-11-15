package com.psybergate.mentoring.cloud.leave.client;

import com.psybergate.mentoring.cloud.leave.client.dto.LeaveTimeSheetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class TimeSheetClient {

  private final RestClient timesheetRestClient;

  public void createLeaveEntries(LeaveTimeSheetRequest req) {
    timesheetRestClient.post().uri("/api/time_sheet_entry/leave")
            .body(req).retrieve().toBodilessEntity();
  }

  public void deleteLeaveEntries(Long leaveApplicationId) {
    timesheetRestClient.delete().uri("/api/time_sheet_entry/leave/{id}", leaveApplicationId)
            .retrieve().toBodilessEntity();
  }

}