package com.example.labTFiB.user;

import java.util.List;

public record UserDto(
        String name,
        String surname,
        String email,
        String contactNumber,
        String password,
        List<String> roles
) {
}
