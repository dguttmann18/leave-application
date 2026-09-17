package com.psybegate.mentoring.cloud.leave.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "leave_applications")
public class LeaveApplication extends BaseEntity {

  private LocalDate firstDay;

  private LocalDate lastDay;

  private boolean isPartial;

  private double hours;

  private String description;

}
