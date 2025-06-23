package com.simplebank.api;

import com.simplebank.dto.LoginRequest;
import com.simplebank.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.context.annotation.Import;
import com.simplebank.config.TestSecurityConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
public class UserControllerApiTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String getBaseUrl() {
        return "http://localhost:" + port + "/users";
    }

    @Test
    public void testUserRegistrationAndLogin() throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Create new user
        UserRequest user = new UserRequest();
        user.username = "apitestuser";
        user.password = "apitestpass";
        user.fullName = "API Test User";
        user.email = "apitest@example.com";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl(), request, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        // Try logging in with same user
        LoginRequest login = new LoginRequest();
        login.username = "apitestuser";
        login.password = "apitestpass";

        HttpEntity<String> loginRequest = new HttpEntity<>(objectMapper.writeValueAsString(login), headers);
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(getBaseUrl() + "/login", loginRequest, String.class);

        Assert.assertEquals(loginResponse.getStatusCode(), HttpStatus.OK);
    }
}
