package com.simplebank.unit;

import com.simplebank.dto.UserRequest;
import com.simplebank.dto.UserResponse;
import com.simplebank.model.User;
import com.simplebank.repository.UserRepository;
import com.simplebank.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserRequest req = new UserRequest();
        req.username = "john_doe";
        req.password = "password123";
        req.fullName = "John Doe";
        req.email = "john@example.com";

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(req.username);
        savedUser.setFullName(req.fullName);
        savedUser.setEmail(req.email);
        savedUser.setCreatedAt(OffsetDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.createUser(req);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.username, "john_doe");
        Assert.assertEquals(response.fullName, "John Doe");
        Assert.assertEquals(response.email, "john@example.com");
    }

    @Test
    public void testLoginSuccess() {
        String rawPassword = "password123";
        String hashed = org.springframework.security.crypto.bcrypt.BCrypt.hashpw(rawPassword, org.springframework.security.crypto.bcrypt.BCrypt.gensalt());

        User user = new User();
        user.setUsername("jane_doe");
        user.setHashedPassword(hashed);

        when(userRepository.findByUsername("jane_doe")).thenReturn(Optional.of(user));

        Optional<User> result = userService.login(new com.simplebank.dto.LoginRequest() {{
            username = "jane_doe";
            password = rawPassword;
        }});

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(result.get().getUsername(), "jane_doe");
    }

    @Test
    public void testLoginFailure() {
        when(userRepository.findByUsername("invalid_user")).thenReturn(Optional.empty());

        Optional<User> result = userService.login(new com.simplebank.dto.LoginRequest() {{
            username = "invalid_user";
            password = "wrongpass";
        }});

        Assert.assertFalse(result.isPresent());
    }
}
