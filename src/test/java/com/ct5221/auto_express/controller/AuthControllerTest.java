package com.ct5221.auto_express.controller;
import com.ct5221.auto_express.dto.LoginRequest;
import com.ct5221.auto_express.dto.LoginResponse;
import com.ct5221.auto_express.service.AuthService;
import com.ct5221.auto_express.service.DealerService;
import com.ct5221.auto_express.service.UserService;
import com.ct5221.auto_express.service.VehicleService;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class,
excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private VehicleService vehicleService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private DealerService dealerService;

    @Test
    @WithMockUser
    void testLoginEndpointSuccess() throws Exception {
        // Mock the service response
        LoginResponse mockResponse = new LoginResponse("test-jwt-token", "USER", 1L, "test@example.com");
        when(authService.authenticate(any(LoginRequest.class))).thenReturn(mockResponse);

        String requestBody = """
                {
                    "email": "test@example.com",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-jwt-token"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }
    @Test
    @WithMockUser
    void testLoginEndpointFailure() throws Exception {
        // Mock the service to throw exception
        when(authService.authenticate(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        String requestBody = """
                {
                    "email": "test@example.com",
                    "password": "wrongpassword"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
