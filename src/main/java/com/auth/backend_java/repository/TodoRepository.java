package com.auth.backend_java.repository;

import com.auth.backend_java.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUsername(String username);  // Custom method to find Todos by username
}
