package com.example.demo1.service;

import com.example.demo1.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean reg(UserDTO userDTO);
    String login(UserDTO userDTO);
}