package com.example.mvctest.service;
//1
import com.example.mvctest.domain.User;
import com.example.mvctest.dto.UserSignupRequestDTO;
import com.example.mvctest.repository.userrepository.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    @Transactional
    public void signUp(UserSignupRequestDTO requestDto) {
        // Check for duplicate loginId
        if (userRepositoryPort.findByLoginId(requestDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("Login ID already exists: " + requestDto.getLoginId());
        }

        User user = new User();
        user.setLoginId(requestDto.getLoginId());
        // NOTE: Passwords should ALWAYS be encrypted before saving.
        // This is just for demonstration.
        user.setLoginPwd(requestDto.getLoginPwd());
        user.setUsersName(requestDto.getUsersName());
        user.setUsersDescription(requestDto.getUsersDescription());
        user.setUsersBirthday(requestDto.getUsersBirthday());
        user.setPoint(0L); // Initialize points to 0

        userRepositoryPort.save(user);
    }
}