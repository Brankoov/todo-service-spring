package se.brankoov.todoservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.brankoov.todoservice.service.TodoService;
import se.brankoov.todoservice.entity.Todo;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.util.UriComponentsBuilder;
import se.brankoov.todoservice.statistics.TodoStats;



@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;
    public TodoController(TodoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(@AuthenticationPrincipal UserDetails me) {
        return ResponseEntity.ok(service.getAllByOwner(me.getUsername()));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@AuthenticationPrincipal UserDetails me,
                                           @Valid @RequestBody Todo todo,
                                           UriComponentsBuilder uri) {
        var saved = service.createForOwner(todo, me.getUsername());
        var location = uri.path("/todos/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@AuthenticationPrincipal UserDetails me,
                                           @PathVariable String id,
                                           @Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(service.updateForOwner(id, todo, me.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal UserDetails me,
                                           @PathVariable String id) {
        service.deleteForOwner(id, me.getUsername());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<Todo> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.getByTitle(title));
    }

    @GetMapping("/stats/by-title")
    public ResponseEntity<List<TodoStats>> getStatsByTitle() {
        var stats = service.getStatsByTitle();
        if (stats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stats);
    }


}
