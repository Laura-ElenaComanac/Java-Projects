package com.license.ProjectSocialNetwork.converter;

import com.license.ProjectSocialNetwork.controller.dto.UserDTO;
import com.license.ProjectSocialNetwork.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<Long, User, UserDTO>{

    @Override
    public User convertDtoToModel(UserDTO dto) {
        User user = User.builder()
                .password(dto.getPassword())
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .build();
        user.setId(dto.getId());
        return user;

    }

    @Override
    public UserDTO convertModelToDto(User user) {
        UserDTO userdto = UserDTO.builder()
                .password(user.getPassword())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .build();
        userdto.setId(user.getId());
        return userdto;
    }
}
