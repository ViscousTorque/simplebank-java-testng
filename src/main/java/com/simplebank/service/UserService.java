package com.simplebank.service;

import com.simplebank.dto.LoginRequest;
import com.simplebank.dto.UserRequest;
import com.simplebank.dto.UserResponse;
import com.simplebank.model.User;
import com.simplebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(UserRequest request) {
        String hashed = BCrypt.hashpw(request.password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(request.username);
        user.setHashedPassword(hashed);
        user.setFullName(request.fullName);
        user.setEmail(request.email);
        user.setCreatedAt(OffsetDateTime.now());

        user = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.id = user.getId();
        response.username = user.getUsername();
        response.fullName = user.getFullName();
        response.email = user.getEmail();
        response.createdAt = user.getCreatedAt();

        return response;
    }

    public Optional<User> login(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (BCrypt.checkpw(request.password, user.getHashedPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
