package com.ct5221.auto_express.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidVehicle() {
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red",
                2020,
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertTrue(violations.isEmpty(), "Valid vehicle should have no violations");
    }

    @Test
    void testInvalidVehicleMake(){
        Vehicle vehicle = new Vehicle(
                null, // Invalid make
                "Camry",
                "Red",
                2020,
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for null make");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Make cannot be null")), "Should have violation for null make");
    }

    @Test
    void testInvalidVehicleModel(){
        Vehicle vehicle = new Vehicle(
                "Toyota", // Invalid make
                null,
                "Red",
                2020,
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for null model");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Model cannot be null")),
                "Should have violation for null model");
    }

    @Test
    void testInvalidColorWithNumbers(){
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red123", // Invalid color with numbers
                2020,
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for invalid color");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Color must contain only letters")), "Should have violation for color pattern");
    }

    @Test
    void testInvalidColorWithSpecialChars() {
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red!", // Invalid color with special characters
                2020,
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for invalid color");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Color must contain only letters")), "Should have violation for color pattern");
    }

    @Test
    void testNullColor(){
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                null, // Invalid null color
                2020,
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for null color");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Color cannot be null")), "Should have violation for null color");
    }

    @Test
    void testInvalidYearTooEarly(){
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red",
                1899, // Invalid year too early
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for invalid year");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Year must be no earlier than 1900")), "Should have violation for year minimum");
    }

    @Test
    void testInvalidYearTooLate(){
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red",
                2027, // Invalid year too early
                15000.0,
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for invalid year");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Year must be no later than 2026")), "Should have violation for year maximum");
    }

    @Test
    void testInvalidNegativeMileage() {
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red",
                2020,
                -1000.0, // Invalid negative mileage
                20000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for negative mileage");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Mileage must be positive")),
                "Should have violation for mileage");
    }

    @Test
    void testInvalidNegativePrice() {
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Red",
                2020,
                15000.0,
                -5000.0 // Invalid negative price
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertFalse(violations.isEmpty(), "Should have violations for negative price");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Price must be negative")),
                "Should have violation for price");
    }

    @Test
    void testBoundaryYearMinimum() {
        Vehicle vehicle = new Vehicle(
                "Ford",
                "Model T",
                "Black",
                1900, // Boundary: minimum valid year
                0.0,
                5000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertTrue(violations.isEmpty(), "Year 1900 should be valid");
    }

    @Test
    void testBoundaryMileageZero() {
        Vehicle vehicle = new Vehicle(
                "Tesla",
                "Model 3",
                "White",
                2024,
                0.0, // Boundary: zero mileage (new vehicle)
                50000.0
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertTrue(violations.isEmpty(), "Zero mileage should be valid");
    }
    @Test
    void testBoundaryPriceZero() {
        Vehicle vehicle = new Vehicle(
                "Toyota",
                "Camry",
                "Blue",
                2020,
                15000.0,
                0.0 // Boundary: zero price (free vehicle)
        );
        Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
        assertTrue(violations.isEmpty(), "Zero price should be valid");
    }



}
