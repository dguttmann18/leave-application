package com.psybegate.mentoring.cloud.leave.service;

public interface LeaveDueService {

  void addLeaveHours(double hours);

  void subtractLeaveHours(double hours);

  double getLeaveHours();

}
