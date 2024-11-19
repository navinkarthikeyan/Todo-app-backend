package com.auth.backend_java.controller;

import com.auth.backend_java.model.Todo;
import com.auth.backend_java.service.TodoService;
import com.auth.backend_java.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final JwtUtil jwtUtil;

    // Constructor injection
    public TodoController(TodoService todoService, JwtUtil jwtUtil) {
        this.todoService = todoService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping
    public ResponseEntity<Todo> createTodo(
            @RequestBody Todo todo, 
            @RequestHeader("Authorization") String token) {

        System.out.print("Create Todo Called");
        
        // Extract username from the JWT token
        String username = jwtUtil.extractUsername(token.replace("Bearer", ""));

        System.out.print(username);
        todo.setUsername(username); 
        
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(201).body(createdTodo);
    }

    // Get all Todos for a user
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer", ""));
        List<Todo> todos = todoService.getTodos(username);
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id, 
            @RequestBody Todo updatedTodo, 
            @RequestHeader("Authorization") String token) {
        
        String username = jwtUtil.extractUsername(token.replace("Bearer", ""));
        Todo todo = todoService.updateTodo(id, username, updatedTodo);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id, 
            @RequestHeader("Authorization") String token) {
        
        String username = jwtUtil.extractUsername(token.replace("Bearer", ""));
        todoService.deleteTodo(id, username);
        return ResponseEntity.noContent().build();
    }
}
