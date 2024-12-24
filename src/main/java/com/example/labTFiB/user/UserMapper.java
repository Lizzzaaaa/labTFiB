package com.example.labTFiB.user;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto toDto(User user){
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        return new UserDto(
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getContactNumber(),
                user.getPassword(),
                roleNames);
    }
}
