package se.brankoov.todoservice.TodoPackage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.getAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return service.create(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody Todo todo) {
        return service.update(id, todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
