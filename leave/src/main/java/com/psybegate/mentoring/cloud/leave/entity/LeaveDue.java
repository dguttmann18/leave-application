package com.psybegate.mentoring.cloud.leave.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "leave_due")
public class LeaveDue extends BaseEntity {

  private double hours;

}
