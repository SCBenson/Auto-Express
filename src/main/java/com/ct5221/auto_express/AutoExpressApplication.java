package com.ct5221.auto_express;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ct5221.auto_express.domain.*;
import com.ct5221.auto_express.controller.*;
import com.ct5221.auto_express.service.*;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoExpressApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoExpressApplication.class, args);
	}
	@Bean
	CommandLineRunner run(VehicleService vehicleService, UserService userService, DealerService dealerService){
		return args -> {
			System.out.println("=== Testing Vehicle Service with MariaDB ===\n");

			System.out.println("1. Creating vehicles...");
			Vehicle vehicle1 = new Vehicle("Jeep", "Wrangler", 2020, 14000.00);
			Vehicle vehicle2 = new Vehicle("Toyota", "Camry", 2019, 6000.00);

			vehicle1 = vehicleService.createVehicle(vehicle1);
			vehicle2 = vehicleService.createVehicle(vehicle2);
			System.out.println("Created vehicles with IDs: " + vehicle1.getId() + ", " + vehicle2.getId());

			vehicleService.getAllVehicles().forEach(v -> System.out.println("Vehicle: " + v.getMake() + " " + v.getModel() + ", Year: " + v.getYear() + ", Price: $" + v.getPrice()));

			vehicleService.updateVehicle(vehicle2.getId(), new Vehicle("Toyota", "Camry", 2018, 5500.00));
			System.out.println("Updated vehicle with ID: " + vehicle2.getId());

			System.out.println("--- Testing Dealer Service with MariaDB ---");
			Dealer dealer1 = new Dealer("username1", "username@gmail.com", "1234567890", "password123");
			Dealer dealer2 = new Dealer("username2", "username2@gmail.com", "0987654321", "password321");

			dealer1 = dealerService.createDealer(dealer1);
			dealer2 = dealerService.createDealer(dealer2);
			System.out.println("Created dealers with IDs: " + dealer1.getId() + ", " + dealer2.getId());

			dealerService.getAllDealers().forEach(d -> System.out.println("Dealer: " + d.getUsername() + ", Email: " + d.getEmail() + ", Phone: " + d.getPhone()));

			dealerService.updateDealer(dealer1.getId(), new Dealer("username3", "username3@gmail.com", "5554446666", "password456"));
			System.out.println("Updated dealer with ID: " + dealer1.getId());
			
			System.out.println("--- Testing User Service with MariaDB ---");
			User user1 = new User("username10", "username10@gmail.com", "8975478754", "password10");
			User user2 = new User("username11", "username11@gmail.com", "5486521497", "password11");

			user1 = userService.createUser(user1);
			user2 = userService.createUser(user2);

			System.out.println("Created users with IDs: " + user1.getId() + ", " + user2.getId());
			
			userService.getAllUsers().forEach(u -> System.out.println("User: " + u.getUsername() + ", Email: " + u.getEmail() + ", Phone: " + u.getPhone() + ", " + u.getPassword()));
			
			userService.updateUser(user1.getId(), new User("username12", "username12@gmail.com", "2563658472", "password12"));
			System.out.println("Updated user with ID: " + user1.getId());
			
			System.out.println("--- Cleanup ---");
			vehicleService.deleteVehicleById(vehicle2.getId());
			dealerService.deleteDealerById(dealer2.getId());
			userService.deleteUserById(user2.getId());
			System.out.println("Deleted test records");

		};
	}
}
