package com.ct5221.auto_express.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    List<User> findByUsernameContains(String keyword);
    List<User> findByUsernameContainingIgnoreCase(String searchTerm);
    @Query("SELECT u.purchasedVehicles FROM User u WHERE u.id = :userId")
    List<Vehicle> findPurchasedVehiclesByUserId(@Param("userId") Long userId);
}
