package com.ct5221.auto_express.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {
    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser(){
        User user = new User(
                "Sean",
                "Benson",
                "seancbenson",
                "sean_c_benson@outlook.com",
                28,
                "6307318265"
                "yTQrs&^uOcI"
        );

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid user should have no violations");
    }

    @Test
    void testInvalidEmail(){
        User user = new User{
                "Sean",
                "Benson",
                "seancbenson",
                "invalid-email", // Invalid email
                28,
                "6307318265"
                "yTQrs&^uOcI"
        );
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty()xt().getMessage());, "Should have violations for invalid email");
        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
        }

        @Test
        void testInvalidPassword(){
            User user = new User(
                    "Sean",
                    "Benson",
                    "seancbenson",
                    "sean_c_benson@outlook.com",
                    "28",
                    "6307318265",
                    "weak" // Invalid
            );

            Set<ConstraintViolation<User>> violations = validator.validate(user);

            assertFalse(violations.isEmpty(), "Should have violations for invalid password");
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().contains("Password must be at least 8 characters long")), "Should have violation for password length");
            assertTrue(violations.stream()
                    .anyMatch(v -> v.getMessage().contains("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")), "Should have violation for password complexity");
        }

        @Test
        void testAgeValidation(){
            User user = new User(
                    "Sean",
                    "Benson",
                    "seancbenson",
                    "sean_c_benson@outlook.com",
                    16,
                    "6307318265",
                    "yTQrs&^uOcI"
            );
            Set<ConstraintViolation<User>> violations = validator.validate(user);

            assertFalse(violations.isEmpty(), "Should have violations for age < 18");
            assertEquals("Age must be at least 18", violations.iterator().next().getMessage());
        }

        @Test
        void testPhoneNumberLength(){
            User user = new User(
                    "Sean",
                    "Benson",
                    "seancbenson",
                    "sean_c_benson@outlook.com",
                    28,
                    "12345", // Invalid phone number
                    "yTQrs&^uOcI"
            );
            Set<ConstraintViolation<User>> violations = validator.validate(user);

            assertFalse(violations.isEmpty(), "Should have violations for invalid phone number length");
            assertEquals("Phone number must be 10 digits", violations.iterator().next().getMessage);
        }
}
