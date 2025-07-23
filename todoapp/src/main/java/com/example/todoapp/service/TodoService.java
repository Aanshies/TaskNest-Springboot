package com.example.todoapp.service;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodosByUser(User user) {
        return todoRepository.findByUser(user);
    }

    public List<Todo> getTodosByCategory(User user, String category) {
        return todoRepository.findByUserAndCategory(user, category);
    }

    public List<Todo> searchTodos(String keyword, User user) {
        return todoRepository.findByTitleContainingIgnoreCaseAndUser(keyword, user);
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

public Todo getTodoById(Long id) {
    return todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
}

public void toggleComplete(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
    todo.setCompleted(!todo.isCompleted());
    todoRepository.save(todo);
}

public void updateTodo(Long id, Todo updatedTodo) {
    Todo existing = getTodoById(id);
    existing.setTitle(updatedTodo.getTitle());
    existing.setDescription(updatedTodo.getDescription());
    existing.setDueDate(updatedTodo.getDueDate());
    existing.setPriority(updatedTodo.getPriority());
    existing.setCategory(updatedTodo.getCategory());
    todoRepository.save(existing);
}

    }

