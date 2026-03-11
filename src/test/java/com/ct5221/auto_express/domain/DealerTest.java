package com.ct5221.auto_express.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDealer() {
        Dealer dealer = new Dealer(
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
                25,
                "1234567890",
                "Password1@"
        );

        Set<ConstraintViolation<Dealer>> violations = validator.validate(dealer);

        assertTrue(violations.isEmpty(), "Valid dealer should have no violations");
    }

    @Test
    void testInvalidEmail() {
        Dealer dealer = new Dealer(
                "John",
                "Doe",
                "johndoe",
                "invalid-email",  // Invalid email
                25,
                "1234567890",
                "Password1@"
        );

        Set<ConstraintViolation<Dealer>> violations = validator.validate(dealer);

        assertFalse(violations.isEmpty(), "Should have violations for invalid email");
        assertEquals(1, violations.size());
        assertEquals("Email should be valid", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidPassword() {
        Dealer dealer = new Dealer(
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
                25,
                "1234567890",
                "weak"  // Doesn't meet password requirements
        );

        Set<ConstraintViolation<Dealer>> violations = validator.validate(dealer);

        assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().contains("Password must be at least 8 characters long")),
                "Should have violation for password length");
        assertTrue(violations.stream()
                        .anyMatch(v -> v.getMessage().contains("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")),
                "Should have violation for password complexity");
    }

    @Test
    void testAgeValidation() {
        Dealer dealer = new Dealer(
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
                16,  // Below minimum age
                "1234567890",
                "Password1@"
        );

        Set<ConstraintViolation<Dealer>> violations = validator.validate(dealer);

        assertFalse(violations.isEmpty(), "Should have violations for age < 18");
        assertEquals("Age must be at least 18", violations.iterator().next().getMessage());
    }

    @Test
    void testPhoneNumberLength() {
        Dealer dealer = new Dealer(
                "John",
                "Doe",
                "johndoe",
                "john@example.com",
                25,
                "12345",  // Invalid length
                "Password1@"
        );

        Set<ConstraintViolation<Dealer>> violations = validator.validate(dealer);

        assertFalse(violations.isEmpty(), "Should have violations for invalid phone length");
        assertEquals("Phone number must be 10 digits", violations.iterator().next().getMessage());
    }
}
