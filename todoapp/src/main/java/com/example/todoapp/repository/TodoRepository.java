package com.example.todoapp.repository;
import java.time.LocalDate;
import java.util.List;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUserAndTitleContaining(User user, String keyword, Pageable pageable);
    List<Todo> findByTitleContainingIgnoreCaseAndUser(String title, User user);
    List<Todo> findByDueDateBetweenAndUser(LocalDate start, LocalDate end, User user);
    List<Todo> findByUserAndDueDateBetween(User user, LocalDate start, LocalDate end);
    List<Todo> findByUser(User user);
    List<Todo> findByUserAndCategory(User user, String category);
    List<Todo> findByUserAndCompletedTrue(User user);
}
