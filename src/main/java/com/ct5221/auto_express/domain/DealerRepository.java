package com.ct5221.auto_express.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    Optional<Dealer> findByUsername(String username);
    Optional<Dealer> findByEmail(String email);
    Optional<Dealer> findByPhone(String phone);
    List<Dealer> findByUsernameContainingIgnoreCase(String username);
}
