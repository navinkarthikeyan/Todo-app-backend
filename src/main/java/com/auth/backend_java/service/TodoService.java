package com.auth.backend_java.service;

import com.auth.backend_java.model.Todo;
import com.auth.backend_java.repository.TodoRepository;
import com.auth.backend_java.util.JwtUtil;  // Import JwtUtil
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class); // Logger instance
    private final TodoRepository todoRepository;
    private final JwtUtil jwtUtil;  // Inject JwtUtil

    // Constructor injection for TodoRepository and JwtUtil
    public TodoService(TodoRepository todoRepository, JwtUtil jwtUtil) {
        this.todoRepository = todoRepository;
        this.jwtUtil = jwtUtil;
    }

    public Todo createTodo(Todo todo) {
        // System.out.print("create todo callingggggg");
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo;
    }

    // Get all Todos for a user
    public List<Todo> getTodos(String username) {
        logger.info("Fetching Todos for user: {}", username); // Log the fetching action
        List<Todo> todos = todoRepository.findByUsername(username); // Fetch todos for the user
        logger.debug("Fetched Todos: {}", todos); 
        return todos;
    }

    public Todo updateTodo(Long id, String username, Todo updatedTodo) {
        logger.info("Updating Todo with ID: {}", id); 
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        logger.debug("Existing Todo: {}", existingTodo); 

        if (!existingTodo.getUsername().equals(username)) {
            logger.error("Unauthorized update attempt for Todo with ID: {}", id); // Log unauthorized attempt
            throw new RuntimeException("You are not authorized to update this Todo");
        }

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setDescription(updatedTodo.getDescription());
        existingTodo.setCompleted(updatedTodo.isCompleted());
        Todo updated = todoRepository.save(existingTodo);
        logger.info("Todo updated successfully: {}", updated); 
        return updated;
    }

    public void deleteTodo(Long id, String username) {
        logger.info("Deleting Todo with ID: {}", id); 
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        logger.debug("Existing Todo: {}", existingTodo); 

        if (!existingTodo.getUsername().equals(username)) {
            logger.error("Unauthorized delete attempt for Todo with ID: {}", id); // Log unauthorized attempt
            throw new RuntimeException("You are not authorized to delete this Todo");
        }

        todoRepository.delete(existingTodo);
        logger.info("Todo deleted successfully with ID: {}", id); // Log successful deletion
    }
    
}
