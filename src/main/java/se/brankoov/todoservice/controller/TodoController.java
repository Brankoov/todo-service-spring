package se.brankoov.todoservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.brankoov.todoservice.service.TodoService;
import se.brankoov.todoservice.entity.Todo;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo, UriComponentsBuilder uri) {
        var saved = service.create(todo);
        var location = uri.path("/api/v1/todos/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(service.update(id, todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
