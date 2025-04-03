package com.banking.IgorVannelSibemou_corebanking.service;


import com.banking.IgorVannelSibemou_corebanking.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    List<UserDto> getAllUsers();
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    void changeUserStatus(Long id, boolean active);
    void addRoleToUser(Long id, String role);
    void removeRoleFromUser(Long id, String role);
}