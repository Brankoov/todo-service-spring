package se.brankoov.todoservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se.brankoov.todoservice.entity.Todo;
import se.brankoov.todoservice.repository.TodoRepository;
import se.brankoov.todoservice.statistics.TodoStats;

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
    
    public List<TodoStats> getStatsByTitle() {
        return repository.getCompletedStatsByTitle();
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
    public Todo getByTitle(String title) {
        return repository.findByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }


    public List<Todo> getAllByOwner(String owner) {
        return repository.findByOwner(owner);
    }

    public Todo createForOwner(Todo todo, String owner) {
        todo.setOwner(owner);
        todo.setUpdatedAt(Instant.now());
        return repository.save(todo);
    }

    public Todo updateForOwner(String id, Todo newTodo, String owner) {
        return repository.findById(id).map(t -> {
            if (!owner.equals(t.getOwner())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your todo");
            }
            t.setTitle(newTodo.getTitle());
            t.setDescription(newTodo.getDescription());
            t.setCompleted(newTodo.isCompleted());
            t.setUpdatedAt(Instant.now());
            return repository.save(t);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    public void deleteForOwner(String id, String owner) {
        var t = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        if (!owner.equals(t.getOwner())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your todo");
        }
        repository.deleteById(id);    

    }
}

