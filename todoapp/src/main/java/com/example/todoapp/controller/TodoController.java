package com.example.todoapp.controller;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.TodoRepository;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.service.TodoService;
import com.example.todoapp.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TodoController {

private final TodoRepository todoRepository;
private final UserRepository userRepository;
private final TodoService todoService;
private final UserService userService;


public TodoController(TodoRepository todoRepository,
                      UserRepository userRepository,
                      TodoService todoService,
                      UserService userService) {
    this.todoRepository = todoRepository;
    this.userRepository = userRepository;
    this.todoService = todoService;
    this.userService = userService;
}


    /**
     * Dashboard with optional category filtering and reminders
     */
@GetMapping("/dashboard")
public String dashboard(Model model,
                         @RequestParam(value = "category", required = false) String category,
                         Principal principal) {
    if (principal == null) {
        return "redirect:/login";
    }

    String username = principal.getName();
    User user = userRepository.findByUsername(username);

    List<Todo> todos = (category == null || category.equals("All")) ?
            todoRepository.findByUser(user) :
            todoRepository.findByUserAndCategory(user, category);

    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    LocalDate dayAfterTomorrow = today.plusDays(2);

    List<Todo> reminders24 = todos.stream()
            .filter(todo -> !todo.isCompleted() && todo.getDueDate() != null &&
                    (todo.getDueDate().isEqual(today) || todo.getDueDate().isEqual(tomorrow)))
            .collect(Collectors.toList());

    List<Todo> reminders48 = todos.stream()
            .filter(todo -> !todo.isCompleted() && todo.getDueDate() != null &&
                    (todo.getDueDate().isEqual(today) || todo.getDueDate().isEqual(tomorrow) || todo.getDueDate().isEqual(dayAfterTomorrow)))
            .collect(Collectors.toList());

    model.addAttribute("user", user);
    model.addAttribute("todos", todos);
    model.addAttribute("reminders24", reminders24);
    model.addAttribute("reminders48", reminders48);
    model.addAttribute("category", (category != null) ? category : "All");
    model.addAttribute("todo", new Todo()); // For modal form binding

    return "dashboard";
}

@GetMapping("/dashboard/completed")
public String completedTodos(Model model, Principal principal) {
    if (principal == null) {
        return "redirect:/login";
    }

    String username = principal.getName();
    User user = userRepository.findByUsername(username);

    List<Todo> completedTodos = todoRepository.findByUserAndCompletedTrue(user);

    model.addAttribute("user", user);
    model.addAttribute("todos", completedTodos);

    return "completed";
}


    /**
     * Return Todo as JSON by ID (API)
     */
   /**
 * Load the edit form with existing data for modal fragment (JSON for dynamic modal)
 */
@GetMapping("/dashboard/api/todo/{id}")
@ResponseBody
public Todo getTodoByIdApi(@PathVariable Long id) {
    return todoService.getTodoById(id);
}

    /**
     * Add a new todo
     */
    @PostMapping("/dashboard/add")
    public String addTodo(@ModelAttribute Todo todo, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        todo.setUser(user);
        todoRepository.save(todo);
        return "redirect:/dashboard";
    }

    /**
     * Delete a todo
     */
    @GetMapping("/dashboard/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/dashboard";
    }

    /**
     * Handle todo update
     */
@GetMapping("/dashboard/edit-form/{id}")
public String getEditForm(@PathVariable Long id, Model model) {
    Todo todo = todoService.getTodoById(id);
    model.addAttribute("todo", todo);
    return "fragments/edit-todo-form :: modalContent";
}



@PostMapping("/dashboard/edit/{id}")
public String editTodo(@PathVariable Long id, @ModelAttribute Todo updatedTodo) {
    todoService.updateTodo(id, updatedTodo);
    return "redirect:/dashboard";
}


    /**
     * Toggle complete status
     */
@PostMapping("/dashboard/complete/{id}")
@ResponseBody
public ResponseEntity<String> toggleComplete(@PathVariable Long id) {
    todoService.toggleComplete(id);
    return ResponseEntity.ok("Toggled");
}




    /**
     * Search todos for the logged-in user
     */
    @GetMapping("/dashboard/search")
    public String searchTodos(@RequestParam("keyword") String keyword,
                               Model model,
                               Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userRepository.findByUsername(username);

        List<Todo> todos = todoService.searchTodos(keyword, user);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        List<Todo> reminders24 = todos.stream()
                .filter(todo -> !todo.isCompleted() && todo.getDueDate() != null &&
                        (todo.getDueDate().isEqual(today) || todo.getDueDate().isEqual(tomorrow)))
                .collect(Collectors.toList());

        List<Todo> reminders48 = todos.stream()
                .filter(todo -> !todo.isCompleted() && todo.getDueDate() != null &&
                        (todo.getDueDate().isEqual(today) || todo.getDueDate().isEqual(tomorrow) || todo.getDueDate().isEqual(dayAfterTomorrow)))
                .collect(Collectors.toList());

        model.addAttribute("todos", todos);
        model.addAttribute("reminders24", reminders24);
        model.addAttribute("reminders48", reminders48);
        model.addAttribute("keyword", keyword);
        model.addAttribute("user", user);
        model.addAttribute("todo", new Todo());

        return "dashboard";
    }

    /**
     * Redirect root to dashboard
     */
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/dashboard";
    }


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

@GetMapping("/profile")
public String getProfile(Model model, Principal principal) {
    User user = userService.findByUsernameOrNull(principal.getName());
if (user == null) {
    return "redirect:/login?error=UserNotFound";
}

    model.addAttribute("user", user);
    return "profile";
}

@GetMapping("/dashboard/upcoming")
public String upcomingTodos(Model model, Principal principal) {
    if (principal == null) {
        return "redirect:/login";
    }

    String username = principal.getName();
    User user = userRepository.findByUsername(username);

    LocalDate now = LocalDate.now();
    LocalDate plusOneDay = now.plusDays(1);
    LocalDate plusTwoDays = now.plusDays(2);

    List<Todo> allTodos = todoRepository.findByUser(user);

    List<Todo> remindersIn24Hours = allTodos.stream()
            .filter(todo -> !todo.isCompleted() && todo.getDueDate() != null &&
                    (todo.getDueDate().isEqual(now) || todo.getDueDate().isEqual(plusOneDay)))
            .collect(Collectors.toList());

    List<Todo> remindersIn48Hours = allTodos.stream()
            .filter(todo -> !todo.isCompleted() && todo.getDueDate() != null &&
                    (todo.getDueDate().isEqual(now) ||
                     todo.getDueDate().isEqual(plusOneDay) ||
                     todo.getDueDate().isEqual(plusTwoDays)))
            .collect(Collectors.toList());

    model.addAttribute("user", user);
    model.addAttribute("remindersIn24Hours", remindersIn24Hours);
    model.addAttribute("remindersIn48Hours", remindersIn48Hours);

    return "upcoming";
}






}

