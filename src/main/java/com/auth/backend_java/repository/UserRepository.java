package com.auth.backend_java.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.auth.backend_java.model.Userdata;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Userdata, Long> {
    Optional<Userdata> findByUsername(String username);
}
