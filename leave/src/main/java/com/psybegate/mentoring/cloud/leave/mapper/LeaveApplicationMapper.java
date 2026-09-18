package com.psybegate.mentoring.cloud.leave.mapper;

import com.psybegate.mentoring.cloud.leave.dto.request.LeaveApplicationRequest;
import com.psybegate.mentoring.cloud.leave.dto.response.LeaveApplicationResponse;
import com.psybegate.mentoring.cloud.leave.entity.LeaveApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeaveApplicationMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  @Mapping(target = "active", ignore = true)
  LeaveApplication toEntity(LeaveApplicationRequest request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "updated", ignore = true)
  @Mapping(target = "active", ignore = true)
  void updateEntityFromRequest(LeaveApplicationRequest request, @MappingTarget LeaveApplication entity);

  LeaveApplicationResponse toResponse(LeaveApplication entity);

  List<LeaveApplicationResponse> toResponseList(List<LeaveApplication> entities);
}
