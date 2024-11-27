package com.beginwork.fashionserver.dto.mapper;


import com.beginwork.fashionserver.dto.request.UserRequest;
import com.beginwork.fashionserver.dto.response.UserResponse;
import com.beginwork.fashionserver.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);

    List<User> toUsers(List<UserRequest> userRequests);
    List<UserResponse> toUserResponses(List<User> users);


    void updateUser(@MappingTarget User user, UserRequest request);
}
