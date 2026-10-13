package ru.Kodrik.security.service;

import ru.Kodrik.security.dto.UserDto;
import ru.Kodrik.security.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
