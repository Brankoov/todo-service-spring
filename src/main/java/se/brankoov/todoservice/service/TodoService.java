package se.brankoov.todoservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.brankoov.todoservice.entity.Todo;
import se.brankoov.todoservice.repository.TodoRepository;

import java.time.Instant;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public Todo create(Todo todo) {
        // createdAt/updatedAt sätts i entiteten, men sätt updatedAt här för säkerhets skull
        todo.setUpdatedAt(Instant.now());
        return repository.save(todo);
    }

    public Todo update(String id, Todo newTodo) {
        return repository.findById(id)
                .map(t -> {
                    t.setTitle(newTodo.getTitle());
                    t.setDescription(newTodo.getDescription());
                    t.setCompleted(newTodo.isCompleted());
                    t.setUpdatedAt(Instant.now());
                    return repository.save(t);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        repository.deleteById(id);
    }
}
